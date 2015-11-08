package piotrsliwa.blackbocs.execution;

import piotrsliwa.blackbocs.execution.ActionFailedException;

public interface Action {
    public void run() throws ActionFailedException;
}
