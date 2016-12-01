package me.bantling.j2ee.basics.form;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface ObjectCreator<T> {
  public T create(
    HttpServletRequest request
  );
}
