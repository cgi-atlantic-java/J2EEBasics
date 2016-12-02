package me.bantling.j2ee.basics.form;

import javax.servlet.http.HttpServletRequest;

/**
 * A helper class for creating objects from {@link HttpServletRequest} parameters.
 * Parameters are always trimmed, to eliminate leading and/or trailing whitespace.
 */
public class RequestParametersHelper {
  
  // ==== Instance fields
  
  private final HttpServletRequest request;
  
  // ==== Cons
  
  public RequestParametersHelper(
    final HttpServletRequest request
  ) {
    this.request = request;
  }
  
  // ==== Impl
  
  public int getInt(
    final String name
  ) {
    final String str = request.getParameter(name).trim();
    return str != null ? Integer.parseInt(str) : 0;
  }
  
  public int getInt(
    final String name,
    final int defaultValue
  ) {
    final String str = request.getParameter(name).trim();
    return str != null ? Integer.parseInt(str) : defaultValue;
  }
  
  public String getString(
    final String name
  ) {
    return request.getParameter(name).trim();
  }
  
  public String getNullOrNonEmptyString(
    final String name
  ) {
    final String str = request.getParameter(name).trim();
    return "".equals(str) ? null : str;
  }
  
  public <E extends Enum<E>> E getEnum(
    final String name,
    final Class<E> enumType
  ) {
    E value = null;
    try {
      value = Enum.valueOf(enumType, request.getParameter(name).trim());
    } catch (@SuppressWarnings("unused") final Exception e) {
      //
    }
    
    return value;
  }
}
