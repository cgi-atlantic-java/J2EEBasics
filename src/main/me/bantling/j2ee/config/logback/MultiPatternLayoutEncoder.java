package me.bantling.j2ee.config.logback;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;

public class MultiPatternLayoutEncoder extends MultiLayoutWrappingEncoderBase {
  private List<String> patterns = new LinkedList<>();

  public List<String> getPatterns(
  ) {
    return patterns;
  }

  public void setPatterns(
    final List<String> patternByName
  ) {
    this.patterns = patternByName;
  }
  
  public void addPattern(
    final String pattern
  ) {
    patterns.add(pattern);
  }
  
  @Override
  public void start(
  ) {
    final LinkedHashMap<String, Layout<ILoggingEvent>> layoutByName = new LinkedHashMap<>();
    for (final String namedPattern : patterns) {
      final String[] parts = namedPattern.split("\\|");
      final String name = parts.length >= 2 ? parts[0] : Logger.ROOT_LOGGER_NAME;
      final String pattern_ = parts.length >= 2 ? parts[1] : parts[0];
      
      final PatternLayout patternLayout = new PatternLayout();
      patternLayout.setContext(context);
      patternLayout.setPattern(pattern_);
      patternLayout.setOutputPatternAsHeader(false);
      patternLayout.start();
      
      layoutByName.put(name, patternLayout);
    }
    
    setLayoutByName(layoutByName);
    super.start();
  }
}
