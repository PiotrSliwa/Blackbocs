package piotrsliwa.blackbocs.tester;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class SutOutputInfiniteUpdater<OutputType> extends Thread {
    
    private final Sut sut;
    private int queryInterval = 100;
    private final List<OutputType> outputChunks = new ArrayList<>();

    public SutOutputInfiniteUpdater(Sut sut) {
        this.sut = sut;
    }
    
    public List<OutputType> getOutput() {
        return outputChunks;
    }
    
    public void setQueryInterval(int milliseconds) {
        queryInterval = milliseconds;
    }
    
    public int getQueryInterval() {
        return queryInterval;
    }

    @Override
    public void run() {
        OutputType chunk;
        while (!sut.isFinished()) {
            try {
                chunk = getOutputChunk();
                if (chunk != null)
                    outputChunks.add(chunk);
            } catch (Exception ex) {
                Logger.getLogger(SutOutputInfiniteUpdater.class.getName()).log(Level.SEVERE, "Blackbocs Error!", ex);
            }
            try {
                Thread.sleep(getQueryInterval());
            } catch (InterruptedException ex) {
                return;
            }
        }
    }
    
    protected abstract OutputType getOutputChunk();
}
