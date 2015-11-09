package piotrsliwa.blackbocs.execution;

public interface OutputCollector extends Runnable {

    public void clear();
    public void finish();

    @Override
    public void run();
}
