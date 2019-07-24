package broker.exceptions;

public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = -5398829504118023324L;

  public BusinessException(final String message) {
    super(message);
  }
}
