package me.bantling.j2ee.basics.validation;

import java.util.Objects;

public final class Validation {
  
  // ==== Member fields
  
  private final String id;
  private final String message;
  
  // ==== Cons
  
  public Validation(
    final String name,
    final String message
  ) {
    this.id = name;
    this.message = message;
  }
  
  // ==== Accessors
  
  public String getId(
  ) {
    return id;
  }
  
  public String getMessage(
  ) {
    return message;
  }
  
  // ==== Object
  
  @Override
  public int hashCode(
  ) {
    return (Objects.hashCode(id)) * 31 + Objects.hashCode(message);
  }
  
  @Override
  public boolean equals(
    final Object o
  ) {
    boolean equals = o == this;
    
    if ((! equals) && (o instanceof Validation)) {
      final Validation obj = (Validation)(o);
      
      equals = Objects.equals(id, obj.id) && Objects.equals(message, obj.message);
    }
    
    return equals;
  }
  
  @Override
  public String toString(
  ) {
    return new StringBuilder().
      append(Validation.class.getSimpleName()).
      append("[name=").append(id).
      append(", message=").append(message).
      toString();
  }
}
