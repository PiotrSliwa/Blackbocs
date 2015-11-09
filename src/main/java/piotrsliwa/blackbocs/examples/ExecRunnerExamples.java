package piotrsliwa.blackbocs.examples;

import piotrsliwa.blackbocs.execution.ExecRunner;
import piotrsliwa.blackbocs.execution.CustomCommandExec;
import piotrsliwa.blackbocs.execution.ConsoleOutputProvider;
import piotrsliwa.blackbocs.execution.ConsoleOutputCollector;
import piotrsliwa.blackbocs.execution.Exec;

public class ExecRunnerExamples {

    private void displayResults(ConsoleOutputCollector collector) {
        System.out.println("Exec output: " + collector.getStandardOutput().toString());
        System.out.println("Exec errors: " + collector.getErrorOutput().toString());
    }

    public void executeEchoCommand() throws Exception {
        Exec exec = new CustomCommandExec("echo Hello World!");
        ConsoleOutputCollector collector = new ConsoleOutputCollector((ConsoleOutputProvider) exec);
        ExecRunner execRunner = new ExecRunner(exec, null, null, collector);
        execRunner.run();
        Thread.sleep(500);
        displayResults(collector);
        execRunner.kill();
    }

    public void executeJavaCommandWithUnrecognizedOption() throws Exception {
        Exec exec = new CustomCommandExec("java --dummynator");
        ConsoleOutputCollector collector = new ConsoleOutputCollector((ConsoleOutputProvider) exec);
        ExecRunner execRunner = new ExecRunner(exec, null, null, collector);
        execRunner.run();
        Thread.sleep(500);
        displayResults(collector);
        execRunner.kill();
    }

    public void executePingCommand() throws Exception {
        Exec exec = new CustomCommandExec("ping github.com");
        ConsoleOutputCollector collector = new ConsoleOutputCollector((ConsoleOutputProvider) exec);
        ExecRunner execRunner = new ExecRunner(exec, null, null, collector);
        execRunner.run();
        Thread.sleep(1000);
        displayResults(collector);
        execRunner.kill();
    }

    public static void main(String[] args) throws Exception {
        ExecRunnerExamples execRunnerExamples = new ExecRunnerExamples();
        System.out.println("\n*** Example: executeEchoCommand");
        execRunnerExamples.executeEchoCommand();
        System.out.println("\n*** Example: executeJavaCommandWithUnrecognizedOption");
        execRunnerExamples.executeJavaCommandWithUnrecognizedOption();
        System.out.println("\n*** Example: executePingCommand");
        execRunnerExamples.executePingCommand();
    }

}
