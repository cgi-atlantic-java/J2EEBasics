package me.bantling.j2ee.basics.util;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

public interface RequestUtils {
  public static String requestParameters(
    final HttpServletRequest request
  ) {
    final TreeMap<String, String[]> sortedParameters = new TreeMap<>(request.getParameterMap());
    
    final StringBuilder sb = new StringBuilder();
    sb.append('{');
    
    boolean firstEntry = true;
    for (final Map.Entry<String, String[]> e : sortedParameters.entrySet()) {
      sb.
        append(firstEntry ? "" : ", ").
        append(e.getKey()).
        append('=');
      firstEntry = false;
      
      final String[] array = e.getValue();
      if (array.length == 0) {
        sb.append("[]");
      } else if (array.length == 1) {
        final String value = array[0];
        if (value == null) {
          sb.append(value);
        } else {
          sb.
            append('"').
            append(array[0]).
            append('"');
        }
      } else {
        boolean firstValue = true;
        sb.append('[');
        
        for (final String value : array) {
          if (value == null) {
            sb.append(value);
          } else {
            sb.
              append(firstValue ? "" : ", ").
              append('"').
              append(value).
              append('"');
          }
        }
        
        sb.append(']');
      } 
    }

    sb.append('}');
    
    return sb.toString();
  }
}
