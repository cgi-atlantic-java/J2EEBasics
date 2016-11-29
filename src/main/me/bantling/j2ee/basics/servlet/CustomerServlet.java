package me.bantling.j2ee.basics.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/customer", loadOnStartup = 1)
public class CustomerServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final Logger log = LoggerFactory.getLogger(CustomerServlet.class);
  
  @Override
  public void init(
  ) throws ServletException {
    log.info("initialized");
    try {
      final InitialContext ctx = new InitialContext();
      final Properties props = (Properties)(ctx.lookup("config/Properties"));
      System.out.println(props);
    } catch (final Exception e ) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doGet(
    final HttpServletRequest request,
    final HttpServletResponse response
  ) throws ServletException, IOException {
    log.info("> doGet");
    request.getRequestDispatcher("/WEB-INF/jsp/customer.jsp").forward(request, response);
    log.info("< doGet");
  }
  
  @Override
  public void destroy(
  ) {
    log.info("destroy");
  }
}
