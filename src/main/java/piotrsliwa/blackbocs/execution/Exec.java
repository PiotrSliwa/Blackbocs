package piotrsliwa.blackbocs.execution;

public interface Exec extends Runnable {

    public boolean errorOccured();
    public boolean isRunning();
    public boolean isFinished();
    public void finish();

    @Override
    public void run();
}
