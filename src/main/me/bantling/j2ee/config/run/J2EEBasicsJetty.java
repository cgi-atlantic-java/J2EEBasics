package me.bantling.j2ee.config.run;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.Configuration.ClassList;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

import me.bantling.j2ee.config.logback.JettyAccessLog;

public class J2EEBasicsJetty {
  public static final int PORT = 8080;
  
  public static Server startJetty(
  ) throws Exception {
    // Create the server on port 8080
    final Server server = new Server(PORT);
    
    // Enable parsing of jndi-related parts of web.xml and jetty-env.xml
    ClassList classlist = ClassList.setServerDefault(server);
    classlist.addAfter(
      FragmentConfiguration.class.getName(),
      EnvConfiguration.class.getName(),
      PlusConfiguration.class.getName()
    );
    
    // Use a jetty-env.xml to specify a custom Properties object as a JNDI entry
//    final EnvConfiguration envConfiguration = new EnvConfiguration();
//    envConfiguration.setJettyEnvXml(Paths.get("WebContent/jetty/WEB-INF/jetty-env.xml").toUri().toURL());
    
    // The ROOT context
    final WebAppContext webappContext = new WebAppContext();
    webappContext.setResourceBase("WebContent/application");
    webappContext.setConfigurations(new Configuration[] {
      new AnnotationConfiguration(),
//      envConfiguration,
      new WebInfConfiguration(),
      new WebXmlConfiguration()
    });
    
    // In Jetty, the ROOT can be set to "/" without any warnings
    webappContext.setContextPath("/");
    
    final URL classes = J2EEBasicsJetty.class.getProtectionDomain().getCodeSource().getLocation();
    webappContext.getMetaData().setWebInfClassesDirs(
      Arrays.asList(Resource.newResource(classes))
    );
        
    
    /*
     * Configure access logging the new way in Jetty 9.3 using Slf4jRequestLog rather than
     * logback-access. Use a custom Slf4jRequestLog class that can log all headers if requested
     * (except for User-Agent and Cookie).
     */
    final JettyAccessLog accessLog = new JettyAccessLog();
    accessLog.setLoggerName("container.access");
    accessLog.setLogDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS X");
    accessLog.setLogLocale(Locale.ENGLISH);
    accessLog.setLogTimeZone("Z");
    accessLog.setExtended(true);
    accessLog.setLogCookies(true);
    accessLog.setLogHeaders(true);
    server.setRequestLog(accessLog);
    
    final HandlerCollection handlers = new HandlerCollection();
    handlers.addHandler(webappContext);
    
    server.setHandler(handlers);
    
    // Start the server
    server.start();
    
    return server;
  }
  
  public static void main(
    final String[] args
  ) throws Exception {
    // Start Jetty and wait for it to die
    startJetty().join();
  }
}
