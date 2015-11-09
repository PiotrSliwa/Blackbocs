package piotrsliwa.blackbocs;

import piotrsliwa.blackbocs.execution.Exec;

public class EmptyEnvironment {

    public EnvironmentWithSutConfigurable withSut(Exec sut) {
        return new EnvironmentWithSutConfigurable(sut);
    }

}
