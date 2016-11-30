package me.bantling.j2ee.basics.exception;

/**
 * A value did not pass a validation
 */
public class ValidationException extends RuntimeException {
  private static final long serialVersionUID = 8372968669780152720L;

  public ValidationException(
    final String message
  ) {
    super(message);
  }
}
