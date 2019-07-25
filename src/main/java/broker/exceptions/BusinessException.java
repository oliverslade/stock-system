package broker.exceptions;

public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = 6152704507769043665L;

  public BusinessException(final String message) {
    super(message);
  }
}
