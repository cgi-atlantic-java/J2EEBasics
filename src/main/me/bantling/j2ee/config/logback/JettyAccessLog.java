package me.bantling.j2ee.config.logback;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.Cookie;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.util.DateCache;

@SuppressWarnings("deprecation")
public class JettyAccessLog extends Slf4jRequestLog {
  private static ThreadLocal<StringBuilder> buffers = new ThreadLocal<StringBuilder>(
  ) { 
    @Override 
    protected StringBuilder initialValue(
    ) { 
      return new StringBuilder(256); 
    } 
  }; 

  private transient DateCache logDateCache;
  private transient PathMap<String> ignorePathMap;
  private boolean logHeaders;
  
  public boolean getLogHeaders() {
    return logHeaders;
  }

  public void setLogHeaders(boolean logHeaders) {
    this.logHeaders = logHeaders;
  }

  private static void append(
    final StringBuilder buf,
    final String s
  ) { 
    if ((s == null) || (s.length() == 0)) { 
      buf.append('-');
    } else { 
      buf.append(s); 
    } 
  }
  
  @Override
  protected synchronized void doStart(
  ) throws Exception {
    final String logDateFormat = getLogDateFormat();
    
    if (logDateFormat != null) { 
      logDateCache = new DateCache(logDateFormat, getLogLocale(), getLogTimeZone()); 
    } 

    final String[] _ignorePaths = getIgnorePaths();
    if (_ignorePaths != null && _ignorePaths.length > 0) {
      ignorePathMap = new PathMap<>(); 
      for (int i = 0; i < _ignorePaths.length; i++) { 
        ignorePathMap.put(_ignorePaths[i], _ignorePaths[i]);
      }
    } else {
      ignorePathMap = null;
    }
    
    super.doStart();
  }
  
  @Override
  public void log(
    final Request request,
    final Response response
  ) {
    try { 
      if ((ignorePathMap != null) && (ignorePathMap.getMatch(request.getRequestURI()) != null)) { 
        return; 
      }
 
      if (!isEnabled()) { 
        return;
      }
 
      final StringBuilder buf = buffers.get(); 
      buf.setLength(0); 
 
      if (getLogServer()) { 
        append(buf,request.getServerName()); 
        buf.append(' '); 
      } 
  
      String addr = null; 
      if (getPreferProxiedForAddress()) { 
        addr = request.getHeader(HttpHeader.X_FORWARDED_FOR.toString()); 
      } 
 
      if (addr == null) {
        addr = request.getRemoteAddr();
      }
 
      buf.append(addr); 
      buf.append(" - "); 
      final Authentication authentication = request.getAuthentication(); 
      append(
        buf,
        authentication instanceof Authentication.User ?
        ((Authentication.User)authentication).getUserIdentity().getUserPrincipal().getName() :
        null
      ); 
 
      buf.append(" ["); 
      if (logDateCache != null) { 
        buf.append(logDateCache.format(request.getTimeStamp()));
      } else { 
        buf.append(request.getTimeStamp()); 
      }
 
      buf.append("] \""); 
      append(buf,request.getMethod()); 
      buf.append(' '); 
      append(buf,request.getHttpURI().toString()); 
      buf.append(' '); 
      append(buf,request.getProtocol()); 
      buf.append("\" "); 
 
      final int status = response.getCommittedMetaData().getStatus(); 
      if (status >= 0) { 
        buf.append((char)('0' + ((status / 100) % 10))); 
        buf.append((char)('0' + ((status / 10) % 10))); 
        buf.append((char)('0' + (status % 10))); 
      } else { 
        buf.append(status);
      }
 
      final long written = response.getHttpChannel().getBytesWritten(); 
      if (written >= 0) { 
        buf.append(' ');
        
        if (written > 99999) {
          buf.append(written);
        } else { 
          if (written > 9999) { 
            buf.append((char)('0' + ((written / 10000) % 10)));
          }
          
          if (written > 999) { 
            buf.append((char)('0' + ((written / 1000) % 10)));
          }
          
          if (written > 99) { 
            buf.append((char)('0' + ((written / 100) % 10)));
          }
          
          if (written > 9) { 
            buf.append((char)('0' + ((written / 10) % 10)));
          }
          
          buf.append((char)('0' + (written) % 10)); 
       } 
       
       buf.append(' '); 
     } else { 
       buf.append(" - "); 
     }
     
     if (isExtended()) { 
       logExtended(buf, request, response); 
     }
 
     if (getLogCookies()) {
       Cookie[] cookies = request.getCookies(); 
       if ((cookies == null) || (cookies.length == 0)) { 
         buf.append(" -");
       } else { 
         buf.append(" \""); 
         for (int i = 0; i < cookies.length; i++) { 
           if (i != 0) { 
             buf.append(';');
           }
           buf.append(cookies[i].getName()); 
           buf.append('='); 
           buf.append(cookies[i].getValue()); 
         } 
         
         buf.append('\"'); 
       } 
     }
     
     if (logHeaders) {
       // Log all headers except Cookie and User-Agent
       buf.append(" \"");
       boolean firstName = true;
       for (final Enumeration<String> names = request.getHeaderNames(); names.hasMoreElements();) {
         final String headerName = names.nextElement();
         
         if ((! "Cookie".equals(headerName)) && (! "User-Agent".equals(headerName))) {
           if (! firstName) {
             buf.append(", ");
           }
           firstName = false;
           
           buf.append(headerName);
           buf.append(" = {");
           
           boolean firstValue = true;
           for (final Enumeration<String> values = request.getHeaders(headerName); values.hasMoreElements();) {
             if (! firstValue) {
               buf.append(" | ");
             }
             firstValue = false;
             
             buf.append(values.nextElement());
           }
           
           buf.append("}");
         }
       }
       buf.append('"');
     }
 
     if (getLogLatency()) {
       long now = System.currentTimeMillis();
       buf.append(' '); 
       buf.append(now - request.getTimeStamp()); 
     } 
 
     final String log = buf.toString(); 
       write(log); 
     } catch (IOException e) { 
       LOG.warn(e);
     }
  }
}
