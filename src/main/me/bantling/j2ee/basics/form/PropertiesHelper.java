package me.bantling.j2ee.basics.form;

import javax.servlet.http.HttpServletRequest;

public class PropertiesHelper {
  private final HttpServletRequest request;
  
  public PropertiesHelper(
    final HttpServletRequest request
  ) {
    this.request = request;
  }
  
  public int getInt(
    final String name
  ) {
    final String str = request.getParameter(name);
    return str != null ? Integer.parseInt(str) : 0;
  }
  
  public int getInt(
    final String name,
    final int defaultValue
  ) {
    final String str = request.getParameter(name);
    return str != null ? Integer.parseInt(str) : defaultValue;
  }
  
  public String getString(
    final String name
  ) {
    return request.getParameter(name);
  }
  
  public String getNullOrNonEmptyString(
    final String name
  ) {
    final String str = request.getParameter(name);
    return "".equals(str) ? null : str;
  }
  
  public <E extends Enum<E>> E getEnum(
    final String name,
    final Class<E> enumType
  ) {
    E value = null;
    try {
      value = Enum.valueOf(enumType, request.getParameter(name));
    } catch (@SuppressWarnings("unused") final Exception e) {
      //
    }
    
    return value;
  }
}
