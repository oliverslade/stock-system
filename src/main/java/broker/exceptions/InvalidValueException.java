package broker.exceptions;

public class InvalidValueException extends BusinessException {

  private static final long serialVersionUID = 1381320130022416598L;

  public InvalidValueException(final String message) {
    super(message);
  }
}
