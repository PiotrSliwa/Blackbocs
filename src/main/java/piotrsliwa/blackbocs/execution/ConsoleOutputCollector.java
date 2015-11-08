package piotrsliwa.blackbocs.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ConsoleOutputCollector implements OutputCollector {
    
    private static final int DEFAULT_QUERY_DELAY_MS = 100;
    
    private final Thread standardOutputCollectingThread;
    private final Thread errorOutputCollectingThread;
    
    private Delayer queryDelayer = new Delayer(DEFAULT_QUERY_DELAY_MS);
    private List<String> standardOutput = new ArrayList<>();
    private List<String> errorOutput = new ArrayList<>();
    
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
                    queryDelayer.sleep();
                } catch (Exception ex) {
                    return;
                }
            }
        }
    }

    public ConsoleOutputCollector(ConsoleOutputProvider outputProvider) {
        this.standardOutputCollectingThread = new Thread(new Collector(standardOutput, () -> {
            return outputProvider.readLineFromStandardOutput();
        }));
        this.errorOutputCollectingThread = new Thread(new Collector(errorOutput, () -> {
            return outputProvider.readLineFromErrorOutput();
        }));
    }

    public void setQueryDelayer(Delayer queryDelayer) {
        this.queryDelayer = queryDelayer;
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
