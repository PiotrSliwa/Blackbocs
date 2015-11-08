package piotrsliwa.blackbocs;

public class ActionFailedException extends Exception {
    
    public ActionFailedException(Class<?> clazz) {
        super("Action " + clazz.getCanonicalName() + " failed!");
    }
    
}
