package piotrsliwa.blackbocs.examples;

import piotrsliwa.blackbocs.tester.*;

public class SutRunnerExamples {
    
    private void displayResults(ConsoleOutputCollector collector) {
        System.out.println("SUT output: " + collector.getStandardOutput().toString());
        System.out.println("SUT errors: " + collector.getErrorOutput().toString());
    }
    
    public void executeEchoCommand() throws Exception {
        Sut sut = new CustomCommandSut("echo Hello World!");
        ConsoleOutputCollector collector = new ConsoleOutputCollector((ConsoleOutputProvider) sut);
        SutRunner sutRunner = new SutRunner(sut, null, null, collector);
        sutRunner.run();
        Thread.sleep(500);
        displayResults(collector);
        sutRunner.kill();
    }
    
    public void executeJavaCommandWithUnrecognizedOption() throws Exception {
        Sut sut = new CustomCommandSut("java --dummynator");
        ConsoleOutputCollector collector = new ConsoleOutputCollector((ConsoleOutputProvider) sut);
        SutRunner sutRunner = new SutRunner(sut, null, null, collector);
        sutRunner.run();
        Thread.sleep(500);
        displayResults(collector);
        sutRunner.kill();
    }
    
    public void executePingCommand() throws Exception {
        Sut sut = new CustomCommandSut("ping github.com", null);
        ConsoleOutputCollector collector = new ConsoleOutputCollector((ConsoleOutputProvider) sut);
        SutRunner sutRunner = new SutRunner(sut, null, null, collector);
        sutRunner.run();
        Thread.sleep(1000);
        displayResults(collector);
        sutRunner.kill();
    }
    
    public static void main(String[] args) throws Exception {
        SutRunnerExamples sutRunnerExamples = new SutRunnerExamples();
        System.out.println("\n*** Example: executeEchoCommand");
        sutRunnerExamples.executeEchoCommand();
        System.out.println("\n*** Example: executeJavaCommandWithUnrecognizedOption");
        sutRunnerExamples.executeJavaCommandWithUnrecognizedOption();
        System.out.println("\n*** Example: executePingCommand");
        sutRunnerExamples.executePingCommand();
    }
    
}
