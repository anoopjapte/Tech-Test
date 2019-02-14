package business_logic;

import business_logic.working_days.ArabWorkingDays;
import business_logic.working_days.DefaultWorkingDays;
import business_logic.working_days.IWorkingDays;
import model.instruction.Instruction;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;

/**
 * Settlement Date Calculator
 */
public class InstructionSettlementDateCalculator {

    /**
     * Function to calculate settlement date for every given instruction
     * @param instructions the instructions of which the settlement date will be calculated
     */
    public static void calculateSettlementDates(Set<Instruction> instructions) {
        instructions.forEach(InstructionSettlementDateCalculator::calculateSettlementDate);
    }

    /**
     * Calculate the settlement Date
     * @param instruction - the instruction of which the settlement date will be calculated
     */
    public static void calculateSettlementDate(Instruction instruction) {
        // Select proper logic based on the Currency
        final IWorkingDays workingDaysMechanism = getWorkingDaysLogic(instruction.getCurrency());

        // find the correct settlement date
        final LocalDate newSettlementDate =
                workingDaysMechanism.findFirstWorkingDate(instruction.getSettlementDate());

        if (newSettlementDate != null) {
            // set the correct settlement date
            instruction.setSettlementDate(newSettlementDate);
        }
    }

    /**
     * Settlement Day logic based on Currency
     * @param currency the currency to choose
     * @return the proper working days strategy
     */
    private static IWorkingDays getWorkingDaysLogic(Currency currency) {
        if ((currency.getCurrencyCode().equals("AED")) ||
            (currency.getCurrencyCode().equals("SAR")))
        {
            return ArabWorkingDays.getInstance();
        }
        return DefaultWorkingDays.getInstance();
    }
}