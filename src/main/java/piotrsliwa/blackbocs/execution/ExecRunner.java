package piotrsliwa.blackbocs.execution;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecRunner {

    private static final int DEFAULT_QUERY_DELAY_MS = 100;

    private final Exec exec;
    private final StateReseter stateReseter;
    private final ReadinessCondition readinessChecker;
    private final OutputCollector outputCollector;

    private Delayer queryDelayer = new Delayer(DEFAULT_QUERY_DELAY_MS);

    public ExecRunner(
            Exec exec,
            StateReseter execReseter,
            ReadinessCondition readinessChecker,
            OutputCollector outputCollector) {
        this.exec = exec;
        this.stateReseter = execReseter;
        this.readinessChecker = readinessChecker;
        this.outputCollector = outputCollector;
    }

    public void setQueryDelayer(Delayer queryDelayer) {
        this.queryDelayer = queryDelayer;
    }

    public void run() throws ExecFinishedButNotReadyException, ExecutionException, ExecNotProvidedException {
        if (exec == null)
            throw new ExecNotProvidedException();
        exec.run();
        if (outputCollector != null)
            outputCollector.run();
        while (!isExecReady()) {
            if (exec.errorOccured())
                throw new ExecutionException();
            if (exec.isFinished())
                throw new ExecFinishedButNotReadyException();
            try {
                queryDelayer.sleep();
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecRunner.class.getName()).log(Level.SEVERE, "Blackbocs Warning!", ex);
                return;
            }
        }
    }

    public boolean isExecReady() {
        if (readinessChecker != null)
            return readinessChecker.isReady();
        return true;
    }

    public void reset() throws UnableToResetException {
        if (stateReseter != null && !stateReseter.reset())
            throw new UnableToResetException();
        if (outputCollector != null)
            outputCollector.clear();
    }

    public void kill() {
        if (exec != null)
            exec.finish();
        if (outputCollector != null)
            outputCollector.finish();
    }

}
