
package horseracer.data.exceptions;

public class LoadException extends Exception {
  public LoadException() {
    super("Error occurred while loading!");
  }

  public LoadException(String msg) {
    super(msg);
  }
}
