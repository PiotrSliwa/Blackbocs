package piotrsliwa.blackbocs.execution;

public class Delayer {

    private int delay;

    public Delayer(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void sleep() throws InterruptedException {
        Thread.sleep(delay);
    }

}
