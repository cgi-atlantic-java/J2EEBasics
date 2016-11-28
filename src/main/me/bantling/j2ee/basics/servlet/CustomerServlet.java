package me.bantling.j2ee.basics.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final Logger log = LoggerFactory.getLogger(CustomerServlet.class);

  @Override
  protected void doGet(
    final HttpServletRequest request,
    final HttpServletResponse response
  ) throws ServletException, IOException {
    log.debug("> doGet");
    request.getRequestDispatcher("/WEB-INF/jsp/customer.jsp").forward(request, response);
    log.debug("< doGet");
  }
}
