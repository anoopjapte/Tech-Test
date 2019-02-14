package utils;

import model.instruction.Instruction;
import model.instruction.InstructionDetails;
import model.instruction.TradeAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

public class InputInstructionsGenerator {
    public static Set<Instruction> getInputInstructions() {
        return new HashSet<>(Arrays.asList(

            new Instruction(
                "foo",
                TradeAction.BUY,
                LocalDate.of(2016, 1, 1),
                LocalDate.of(2016, 1, 2),
                new InstructionDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(0.50),
                        200,
                        BigDecimal.valueOf(100.25))),

            new Instruction(
                "bar",
                TradeAction.SELL,
                LocalDate.of(2016, 1, 5),
                LocalDate.of(2016, 1, 7),
                new InstructionDetails(
                        Currency.getInstance("AED"),
                        BigDecimal.valueOf(0.22),
                        450,
                        BigDecimal.valueOf(150.5))),

            new Instruction(
                "coo",
                TradeAction.BUY,
                LocalDate.of(2016, 1, 1),
                LocalDate.of(2016, 1, 2),
                new InstructionDetails(
                        Currency.getInstance("INR"),
                        BigDecimal.valueOf(0.014),
                        300,
                        BigDecimal.valueOf(175))),

            new Instruction(
                "car",
                TradeAction.SELL,
                LocalDate.of(2016, 1, 5),
                LocalDate.of(2016, 1, 7),
                new InstructionDetails(
                        Currency.getInstance("CAD"),
                        BigDecimal.valueOf(0.76),
                        150,
                        BigDecimal.valueOf(200))),

            new Instruction(
                "too",
                TradeAction.BUY,
                LocalDate.of(2016, 1, 15),
                LocalDate.of(2016, 1, 16),
                new InstructionDetails(
                        Currency.getInstance("CNY"),
                        BigDecimal.valueOf(0.15),
                        350,
                        BigDecimal.valueOf(220))),

            new Instruction(
                "tar",
                TradeAction.SELL,
                LocalDate.of(2016, 1, 17),
                LocalDate.of(2016, 1, 19),
                new InstructionDetails(
                        Currency.getInstance("AUD"),
                        BigDecimal.valueOf(0.71),
                        250,
                        BigDecimal.valueOf(125))),

            new Instruction(
                "koo",
                TradeAction.BUY,
                LocalDate.of(2016, 1, 15),
                LocalDate.of(2016, 1, 16),
                new InstructionDetails(
                        Currency.getInstance("EUR"),
                        BigDecimal.valueOf(1.13),
                    220,
                        BigDecimal.valueOf(250))),

            new Instruction(
                "kar",
                TradeAction.SELL,
                LocalDate.of(2016, 1, 17),
                LocalDate.of(2016, 1, 19),
                    new InstructionDetails(
                            Currency.getInstance("GBP"),
                            BigDecimal.valueOf(1.29),
                        270,
                            BigDecimal.valueOf(300))),
           
            new Instruction(
                    "doo",
                    TradeAction.BUY,
                    LocalDate.of(2016, 1, 7),
                    LocalDate.of(2016, 1, 9),
                        new InstructionDetails(
                                Currency.getInstance("SAR"),
                                BigDecimal.valueOf(0.21),
                            240,
                                BigDecimal.valueOf(275))), 
            
            new Instruction(
                    "dar",
                    TradeAction.SELL,
                    LocalDate.of(2016, 1, 8),
                    LocalDate.of(2016, 1, 9),
                        new InstructionDetails(
                                Currency.getInstance("NZD"),
                                BigDecimal.valueOf(0.53),
                            120,
                                BigDecimal.valueOf(320)))
        ));
    }
}