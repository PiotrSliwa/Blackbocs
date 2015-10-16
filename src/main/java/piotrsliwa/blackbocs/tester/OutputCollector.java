package piotrsliwa.blackbocs.tester;

public interface OutputCollector extends Runnable {
    
    public void clear();
    public void finish();

    @Override
    public void run();
}
