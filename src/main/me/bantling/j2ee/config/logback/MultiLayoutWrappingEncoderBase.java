package me.bantling.j2ee.config.logback;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.encoder.EncoderBase;

public class MultiLayoutWrappingEncoderBase extends EncoderBase<ILoggingEvent> {
  private LinkedHashMap<String, Layout<ILoggingEvent>> layoutByName;
  private Layout<ILoggingEvent> firstLayout;
  private Charset charset;
  private boolean immediateFlush = true;
  
  private static void appendIfNotNull(
    final StringBuilder sb,
    final String s
  ) {
    if (s != null) {
      sb.append(s);
    }
  }
  
  private byte[] convertToBytes(
    final String s
  ) {
    if (charset == null) {
      return s.getBytes();
    }
    
    try {
      return s.getBytes(charset.name());
    } catch (@SuppressWarnings("unused") final UnsupportedEncodingException e) {
      throw new IllegalStateException("An existing charset cannot possibly be unsupported.");
    }
  }
  
  void writeHeader(
  ) throws IOException {
    if (firstLayout != null && (outputStream != null)) {
      StringBuilder sb = new StringBuilder();
      appendIfNotNull(sb, firstLayout.getFileHeader());
      appendIfNotNull(sb, firstLayout.getPresentationHeader());
      if (sb.length() > 0) {
        sb.append(CoreConstants.LINE_SEPARATOR);
        // If at least one of file header or presentation header were not
        // null, then append a line separator.
        // This should be useful in most cases and should not hurt.
        outputStream.write(convertToBytes(sb.toString()));
        outputStream.flush();
      }
    }
  }
  
  void writeFooter(
  ) throws IOException {
    if (firstLayout != null && outputStream != null) {
      StringBuilder sb = new StringBuilder();
      appendIfNotNull(sb, firstLayout.getPresentationFooter());
      appendIfNotNull(sb, firstLayout.getFileFooter());
      if (sb.length() > 0) {
        outputStream.write(convertToBytes(sb.toString()));
        outputStream.flush();
      }
    }
  }
  
  @Override
  public void init(
    final OutputStream os
  ) throws IOException {
    super.init(os);
    firstLayout =
      (layoutByName == null) || layoutByName.isEmpty() ?
      null :
      layoutByName.values().iterator().next();
    writeHeader();
  }

  @Override
  public void doEncode(
    final ILoggingEvent event
  ) throws IOException {
    String txt = layoutByName.getOrDefault(event.getLoggerName(), firstLayout).doLayout(event);
    outputStream.write(convertToBytes(txt));
    if (immediateFlush) {
      outputStream.flush();
    }
  }

  @Override
  public void close(
  ) throws IOException {
    writeFooter();
  }
  
  @Override
  public void start(
  ) {
    started = true;
  }

  @Override
  public void stop(
  ) {
    started = false;
    if (outputStream != null) {
      try {
        outputStream.flush();
      } catch (@SuppressWarnings("unused") IOException e) {
        //
      }
    }
  }

  public LinkedHashMap<String, Layout<ILoggingEvent>> getLayoutByName(
  ) {
    return layoutByName;
  }

  public void setLayoutByName(
    final LinkedHashMap<String, Layout<ILoggingEvent>> layouts
  ) {
    this.layoutByName = layouts;
  }

  public Charset getCharset(
  ) {
    return charset;
  }

  public void setCharset(
    final Charset charset
  ) {
    this.charset = charset;
  }

  public boolean isImmediateFlush(
  ) {
    return immediateFlush;
  }

  public void setImmediateFlush(
    final boolean immediateFlush
  ) {
    this.immediateFlush = immediateFlush;
  }
}
