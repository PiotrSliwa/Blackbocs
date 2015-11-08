package piotrsliwa.blackbocs.execution;

public class ActionFailedException extends Exception {
    
    public ActionFailedException(Class<?> clazz) {
        super("Action " + clazz.getCanonicalName() + " failed!");
    }
    
}
