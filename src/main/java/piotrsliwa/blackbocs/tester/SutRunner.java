package piotrsliwa.blackbocs.tester;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SutRunner {
    
    private final Sut sut;
    private final SutReseter sutReseter;
    private final ReadinessChecker readinessChecker;
    private final OutputCollector outputCollector;
    
    private int queryInterval = 100;

    public SutRunner(Sut sut, SutReseter sutReseter, ReadinessChecker readinessChecker, OutputCollector outputCollector) {
        this.sut = sut;
        this.sutReseter = sutReseter;
        this.readinessChecker = readinessChecker;
        this.outputCollector = outputCollector;
    }
    
    public void setQueryInterval(int milliseconds) {
        queryInterval = milliseconds;
    }
    
    public int getQueryInterval() {
        return queryInterval;
    }
    
    public void run() throws SutFinishedButItsNotReadyException, SutExecutionException, SutNotProvidedException {
        if (sut == null)
            throw new SutNotProvidedException();
        sut.run();
        if (outputCollector != null)
            outputCollector.run();
        while (!isSutReady()) {
            if (sut.errorOccured())
                throw new SutExecutionException();
            if (sut.isFinished())
                throw new SutFinishedButItsNotReadyException();
            try {
                Thread.sleep(getQueryInterval());
            } catch (InterruptedException ex) {
                Logger.getLogger(SutRunner.class.getName()).log(Level.SEVERE, "Blackbocs Warning!", ex);
                return;
            }
        }
    }
    
    public boolean isSutReady() {
        if (readinessChecker != null)
            return readinessChecker.isReady();
        return true;
    }
    
    public void reset() throws UnableToResetException {
        if (sutReseter != null && !sutReseter.reset())
            throw new UnableToResetException();
        if (outputCollector != null)
            outputCollector.clear();
    }
    
    public void kill() {
        if (sut != null)
            sut.finish();
        if (outputCollector != null)
            outputCollector.finish();
    }
    
}
