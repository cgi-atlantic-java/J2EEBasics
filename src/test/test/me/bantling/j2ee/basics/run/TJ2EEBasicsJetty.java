package test.me.bantling.j2ee.basics.run;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.bantling.j2ee.basics.run.J2EEBasicsJetty;
import me.bantling.j2ee.basics.servlet.HelloWorldServlet;

@SuppressWarnings("static-method")
public class TJ2EEBasicsJetty {
  private static final Logger log = LoggerFactory.getLogger(TJ2EEBasicsJetty.class);
  
  @Test
  public void jetty(
  ) throws Exception {
    final Server jetty = J2EEBasicsJetty.startJetty();
    
    // Get the port number, context path, and servlet path
    final int port = ((NetworkConnector)(jetty.getConnectors()[0])).getPort();
    final String contextPath = ((WebAppContext)(jetty.getHandler())).getContextPath();
    final String servletPath = HelloWorldServlet.class.getAnnotation(WebServlet.class).urlPatterns()[0];
    
    final URL url = new URL("http://localhost:" + port + contextPath + servletPath);
    log.info("Accessing Jetty Hello, World app at: {}", url);
    final HttpURLConnection conn = (HttpURLConnection)(url.openConnection());
    
    final StringBuilder sb = new StringBuilder();
    try (
      final BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    ) {
      final String eol = String.format("%n");
      
      for (String line; (line = r.readLine()) != null;) {
        if (line.length() > 0) {
          sb.append(eol).append(line);
        }
      }
    }
    
    /*
     * With Jetty, we can log this message before or after closing the connection, either way the
     * access log entry is displayed before the page dump.
     */
    log.info("Generated page:{}", sb);
    log.info("Done accessing Jetty URL");
    
    // Need to explicitly stop Jetty, otherwise the JVM will continue to run
    jetty.stop();
  }
}
