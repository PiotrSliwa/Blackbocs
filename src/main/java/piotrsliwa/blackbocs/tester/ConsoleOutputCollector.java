package piotrsliwa.blackbocs.tester;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ConsoleOutputCollector implements OutputCollector {
    
    private final ConsoleOutputProvider outputProvider;
    private final Thread standardOutputCollectingThread;
    private final Thread errorOutputCollectingThread;
    
    private List<String> standardOutput = new ArrayList<>();
    private List<String> errorOutput = new ArrayList<>();
    private int queryInterval = 100;
    
    private class Collector implements Runnable {
        
        private final List<String> targetList;
        private final Callable<String> reader;

        public Collector(List<String> targetList, Callable<String> reader) {
            this.targetList = targetList;
            this.reader = reader;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String line = reader.call();
                    if (line != null && !line.isEmpty())
                        targetList.add(line);
                    Thread.sleep(queryInterval);
                } catch (Exception ex) {
                    return;
                }
            }
        }
    }

    public ConsoleOutputCollector(ConsoleOutputProvider outputProvider) {
        this.outputProvider = outputProvider;
        this.standardOutputCollectingThread = new Thread(new Collector(standardOutput, () -> {
            return outputProvider.readLineFromStandardOutput();
        }));
        this.errorOutputCollectingThread = new Thread(new Collector(errorOutput, () -> {
            return outputProvider.readLineFromErrorOutput();
        }));
    }
    
    public void setQueryInterval(int milliseconds) {
        queryInterval = milliseconds;
    }
    
    public int getQueryInterval() {
        return queryInterval;
    }

    public List<String> getStandardOutput() {
        return standardOutput;
    }

    public List<String> getErrorOutput() {
        return errorOutput;
    }

    @Override
    public void clear() {
        standardOutput.clear();
        errorOutput.clear();
    }

    @Override
    public void finish() {
        standardOutputCollectingThread.interrupt();
        errorOutputCollectingThread.interrupt();
    }

    @Override
    public void run() {
        standardOutputCollectingThread.start();
        errorOutputCollectingThread.start();
    }
    
}
