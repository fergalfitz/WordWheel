package ie.corktrainingcentre.wordwheel;

/**
 * Created by ferg on 12/06/15.
 *
 * This class was created to make it easier to relay information back to the caller about the results
 * of checking a word against scrambled letters to find out if it is a valid guess at the wordWheel
 */
public class Result {

    private boolean valid;
    private String message;

    public Result() {
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
