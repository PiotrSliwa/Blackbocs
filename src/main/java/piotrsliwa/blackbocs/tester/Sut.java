package piotrsliwa.blackbocs.tester;

public interface Sut extends Runnable {

    public boolean errorOccured();
    public boolean isRunning();
    public boolean isFinished();
    public void finish();
    
    @Override
    public void run();
}
