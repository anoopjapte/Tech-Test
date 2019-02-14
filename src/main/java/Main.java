import business_logic.reports.ReportGenerator;
import model.instruction.Instruction;
import utils.InputInstructionsGenerator;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        final Set<Instruction> instructions = InputInstructionsGenerator.getInputInstructions();
        final ReportGenerator reportGenerator = new ReportGenerator();

        System.out.println(reportGenerator.generateInstructionsReport(instructions));
    }
}
