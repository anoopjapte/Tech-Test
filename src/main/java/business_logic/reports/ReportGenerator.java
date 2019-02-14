package business_logic.reports;

import business_logic.InstructionSettlementDateCalculator;
import business_logic.InstructionSettlementStatsCalculator;
import business_logic.ranking.Rank;
import model.instruction.Instruction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class ReportGenerator {
    private StringBuilder stringBuilder = new StringBuilder();

    public String generateInstructionsReport(Set<Instruction> instructions) {
        // first calculate the correct settlement dates
        InstructionSettlementDateCalculator.calculateSettlementDates(instructions);

        // Build the report string
        return generateReportTrailer (instructions,
        		generateDailyOutgoingRanking(instructions,
               generateDailyIncomingRanking(instructions,
               generateDailyIncomingAmount(instructions,
               generateDailyOutgoingAmount(instructions, 
               generateReportHeader(instructions, stringBuilder))))))
            .toString();
    }
  
    private static StringBuilder generateReportHeader(Set<Instruction> instructions, StringBuilder stringBuilder) {

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("      JPMC Test - Trade Report            \n")
                .append("----------------------------------------\n");

        return stringBuilder;
    }
    
    private static StringBuilder generateReportTrailer(Set<Instruction> instructions, StringBuilder stringBuilder) {

        stringBuilder
                .append("\n************ End of Report *************\n");

        return stringBuilder;
    }
     
    private static StringBuilder generateDailyOutgoingAmount(Set<Instruction> instructions, StringBuilder stringBuilder) {
        final Map<LocalDate, BigDecimal> dailyOutgoingAmount =
                InstructionSettlementStatsCalculator.calculateDailyOutgoingAmount(instructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Outgoing Daily Amount          \n")
                .append("----------------------------------------\n")
                .append("      Date       |    Trade Amount      \n")
                .append("----------------------------------------\n");

        for (LocalDate date : dailyOutgoingAmount.keySet()) {
            stringBuilder
                .append(date).append("       |      ").append(dailyOutgoingAmount.get(date)).append("\n");
        }

        return stringBuilder;
    }

    private static StringBuilder generateDailyIncomingAmount(Set<Instruction> instructions, StringBuilder stringBuilder) {
        final Map<LocalDate, BigDecimal> dailyOutgoingAmount =
                InstructionSettlementStatsCalculator.calculateDailyIncomingAmount(instructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Incoming Daily Amount          \n")
                .append("----------------------------------------\n")
                .append("      Date       |    Trade Amount      \n")
                .append("----------------------------------------\n");

        for (LocalDate date : dailyOutgoingAmount.keySet()) {
            stringBuilder
                    .append(date).append("       |      ").append(dailyOutgoingAmount.get(date)).append("\n");
        }

        return stringBuilder;
    }

    private static StringBuilder generateDailyOutgoingRanking(Set<Instruction> instructions, StringBuilder stringBuilder) {
        final Map<LocalDate, LinkedList<Rank>> dailyOutgoingRanking =
                InstructionSettlementStatsCalculator.calculateDailyOutgoingRanking(instructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Outgoing Daily Ranking          \n")
                .append("----------------------------------------\n")
                .append("     Date    |     Rank   |   Entity     \n")
                .append("----------------------------------------\n");

        for (LocalDate date : dailyOutgoingRanking.keySet()) {
            for (Rank rank : dailyOutgoingRanking.get(date)) {
                stringBuilder
                    .append(date).append("   |      ")
                    .append(rank.getRank()).append("     |    ")
                    .append(rank.getEntity()).append("\n");
            }
        }

        return stringBuilder;
    }

    private static StringBuilder generateDailyIncomingRanking(Set<Instruction> instructions, StringBuilder stringBuilder) {
        final Map<LocalDate, LinkedList<Rank>> dailyIncomingRanking =
                InstructionSettlementStatsCalculator.calculateDailyIncomingRanking(instructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Incoming Daily Ranking          \n")
                .append("----------------------------------------\n")
                .append("     Date    |     Rank   |   Entity     \n")
                .append("----------------------------------------\n");

        for (LocalDate date : dailyIncomingRanking.keySet()) {
            for (Rank rank : dailyIncomingRanking.get(date)) {
                stringBuilder
                        .append(date).append("   |      ")
                        .append(rank.getRank()).append("     |    ")
                        .append(rank.getEntity()).append("\n");
            }
        }

        return stringBuilder;
    }
}
