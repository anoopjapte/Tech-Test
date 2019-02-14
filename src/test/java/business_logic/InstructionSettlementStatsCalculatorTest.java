package business_logic;

import business_logic.ranking.Rank;
import model.instruction.Instruction;
import model.instruction.InstructionDetails;
import model.instruction.TradeAction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class InstructionSettlementStatsCalculatorTest {

    private static final LocalDate MONDAY    = LocalDate.of(2019, 1, 14);
    private static final LocalDate TUESDAY   = LocalDate.of(2019, 1, 15);
    private static final LocalDate WEDNESDAY = LocalDate.of(2019, 1, 16);
    private static final LocalDate SATURDAY  = LocalDate.of(2019, 1, 12);
    private static final LocalDate SUNDAY    = LocalDate.of(2019, 1, 13);

    private static Set<Instruction> getInputInstructions() {
        final Set<Instruction> instructions = new HashSet<>();

        // ===========================================================================
        // All these should be under the same settlement date
        // ===========================================================================
        instructions.add(new Instruction(
                "AAA",
                TradeAction.BUY,
                LocalDate.of(2019, 1, 12),
                MONDAY,
                new InstructionDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        100,
                        BigDecimal.valueOf(1))));

        instructions.add(new Instruction(
                "BBB",
                TradeAction.BUY,
                LocalDate.of(2019, 1, 12),
                MONDAY,
                new InstructionDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        200,
                        BigDecimal.valueOf(1))));

        instructions.add(new Instruction(
                "CCC",
                TradeAction.BUY,
                LocalDate.of(2019, 1, 12),
                SATURDAY,
                new InstructionDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        300,
                        BigDecimal.valueOf(1))));

        instructions.add(new Instruction(
                "DDD",
                TradeAction.SELL,
                LocalDate.of(2019, 1, 12),
                SUNDAY,
                new InstructionDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        200,
                        BigDecimal.valueOf(1))));

        // ===========================================================================
        // All these should be under the same settlement date
        // ===========================================================================
        instructions.add(new Instruction(
                "EEE",
                TradeAction.BUY,
                LocalDate.of(2019, 1, 12),
                TUESDAY,
                new InstructionDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        400,
                        BigDecimal.valueOf(1))));

        instructions.add(new Instruction(
                "FFF",
                TradeAction.SELL,
                LocalDate.of(2019, 1, 12),
                TUESDAY,
                new InstructionDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        1000,
                        BigDecimal.valueOf(1))));

        // ===========================================================================
        // All these should be under the same settlement date
        // ===========================================================================
        instructions.add(new Instruction(
                "GGG",
                TradeAction.BUY,
                LocalDate.of(2019, 1, 12),
                WEDNESDAY,
                new InstructionDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        7000,
                        BigDecimal.valueOf(1))));

        InstructionSettlementDateCalculator.calculateSettlementDates(instructions);

        return instructions;
    }

    @Test
    public void testDailyIncomingAmount() throws Exception {
        final Map<LocalDate, BigDecimal> dailyIncomingAmount =
                InstructionSettlementStatsCalculator.calculateDailyIncomingAmount(getInputInstructions());

        assertEquals(2, dailyIncomingAmount.size());
        assertTrue(Objects.equals(dailyIncomingAmount.get(MONDAY), BigDecimal.valueOf(200.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        assertTrue(Objects.equals(dailyIncomingAmount.get(TUESDAY), BigDecimal.valueOf(1000.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
    }

    @Test
    public void testDailyOutgoingAmount() throws Exception {
        final Map<LocalDate, BigDecimal> dailyOutgoingAmount =
                InstructionSettlementStatsCalculator.calculateDailyOutgoingAmount(getInputInstructions());

        assertEquals(3, dailyOutgoingAmount.size());
        assertTrue(Objects.equals(dailyOutgoingAmount.get(MONDAY), BigDecimal.valueOf(600.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        assertTrue(Objects.equals(dailyOutgoingAmount.get(TUESDAY), BigDecimal.valueOf(400.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
    }

    @Test
    public void testDailyIncomingRanking() throws Exception {
        final Map<LocalDate, LinkedList<Rank>> dailyIncomingRanking =
                InstructionSettlementStatsCalculator.calculateDailyIncomingRanking(getInputInstructions());

        assertEquals(2, dailyIncomingRanking.size());

        assertEquals(1, dailyIncomingRanking.get(MONDAY).size());
        assertEquals(1, dailyIncomingRanking.get(TUESDAY).size());

        assertTrue(dailyIncomingRanking.get(MONDAY).contains(new Rank(1, "DDD", MONDAY)));
        assertTrue(dailyIncomingRanking.get(TUESDAY).contains(new Rank(1, "FFF", TUESDAY)));

    }

    @Test
    public void testDailyOutgoingRanking() throws Exception {
        final Map<LocalDate, LinkedList<Rank>> dailyOutgoingRanking =
                InstructionSettlementStatsCalculator.calculateDailyOutgoingRanking(getInputInstructions());

        assertEquals(3, dailyOutgoingRanking.size());

        assertEquals(3, dailyOutgoingRanking.get(MONDAY).size());
        assertEquals(1, dailyOutgoingRanking.get(TUESDAY).size());
        assertEquals(1, dailyOutgoingRanking.get(WEDNESDAY).size());

        assertTrue(dailyOutgoingRanking.get(MONDAY).contains(new Rank(1, "CCC", MONDAY)));
        assertTrue(dailyOutgoingRanking.get(MONDAY).contains(new Rank(2, "BBB", MONDAY)));
        assertTrue(dailyOutgoingRanking.get(MONDAY).contains(new Rank(3, "AAA", MONDAY)));

        assertTrue(dailyOutgoingRanking.get(TUESDAY).contains(new Rank(1, "EEE", TUESDAY)));

        assertTrue(dailyOutgoingRanking.get(WEDNESDAY).contains(new Rank(1, "GGG", WEDNESDAY)));
    }
}