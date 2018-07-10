package ancor2gui.model.workers;

import javafx.concurrent.Task;

/**
 * Interface for calling a callback after thread execution.
 * Suitable for extracting results and displaying them in the gui
 */
public interface Callback {

    /**
     * Method to be called on thread exit
     * @param task Runnable calling this
     */
    void callback(Task<Void> task);
}
