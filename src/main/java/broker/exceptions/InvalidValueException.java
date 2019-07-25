package broker.exceptions;

public class InvalidValueException extends BusinessException {

  private static final long serialVersionUID = -4901423716905483129L;

  public InvalidValueException(final String message) {
    super(message);
  }
}
