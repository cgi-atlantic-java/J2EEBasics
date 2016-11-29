package me.bantling.j2ee.basics.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ServletContextLogger implements ServletContextListener {

  private static final Logger log = LoggerFactory.getLogger(ServletContextLogger.class);
  
  @Override
  public void contextInitialized(
    final ServletContextEvent evt
  ) {
    final StringBuilder sb = new StringBuilder();
    sb.
      append("Initialized context ").
      append(evt.getServletContext().getContextPath()).
      append(" with following servlets:");
    
    for (final ServletRegistration servletReg : evt.getServletContext().getServletRegistrations().values()) {
      sb.
        append(String.format("%n")).
        append(servletReg.getName()).
        append(" => ").
        append(servletReg.getMappings());
    }
    
    log.info(sb.toString());
  }

  @Override
  public void contextDestroyed(
    final ServletContextEvent evt
  ) {
    log.info("Destroyed context " + evt.getServletContext().getContextPath());
  }
}
