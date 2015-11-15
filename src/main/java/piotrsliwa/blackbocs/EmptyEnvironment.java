package piotrsliwa.blackbocs;

import piotrsliwa.blackbocs.execution.Exec;

public class EmptyEnvironment {

    public EnvironmentWithSut withSut(Exec sut) {
        return new EnvironmentWithSut(sut);
    }

}
