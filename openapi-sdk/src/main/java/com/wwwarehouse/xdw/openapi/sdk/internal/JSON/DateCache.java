 package com.wwwarehouse.xdw.openapi.sdk.internal.JSON;

 import java.text.DateFormatSymbols;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Locale;
 import java.util.TimeZone;

 class DateCache
 {
   public static String DEFAULT_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
   private static long __hitWindow = 3600L;
   private String _formatString;
   private String _tzFormatString;
   private SimpleDateFormat _tzFormat;
   private String _minFormatString;
   private SimpleDateFormat _minFormat;
   private String _secFormatString;
   private String _secFormatString0;
   private String _secFormatString1;
   private long _lastMinutes = -1L;
   private long _lastSeconds = -1L;
   private int _lastMs = -1;
   private String _lastResult = null;
   private Locale _locale = null;
   private DateFormatSymbols _dfs = null;

   public DateCache()
   {
     this(DEFAULT_FORMAT);
     getFormat().setTimeZone(TimeZone.getDefault());
   }

   public DateCache(String format)
   {
     this._formatString = format;
     setTimeZone(TimeZone.getDefault());
   }

   public DateCache(String format, Locale l)
   {
     this._formatString = format;
     this._locale = l;
     setTimeZone(TimeZone.getDefault());
   }

   public DateCache(String format, DateFormatSymbols s)
   {
     this._formatString = format;
     this._dfs = s;
     setTimeZone(TimeZone.getDefault());
   }

   public synchronized void setTimeZone(TimeZone tz)
   {
     setTzFormatString(tz);
     if (this._locale != null)
     {
       this._tzFormat = new SimpleDateFormat(this._tzFormatString, this._locale);
       this._minFormat = new SimpleDateFormat(this._minFormatString, this._locale);
     }
     else if (this._dfs != null)
     {
       this._tzFormat = new SimpleDateFormat(this._tzFormatString, this._dfs);
       this._minFormat = new SimpleDateFormat(this._minFormatString, this._dfs);
     }
     else
     {
       this._tzFormat = new SimpleDateFormat(this._tzFormatString);
       this._minFormat = new SimpleDateFormat(this._minFormatString);
     }
     this._tzFormat.setTimeZone(tz);
     this._minFormat.setTimeZone(tz);
     this._lastSeconds = -1L;
     this._lastMinutes = -1L;
   }

   public TimeZone getTimeZone()
   {
     return this._tzFormat.getTimeZone();
   }

   public void setTimeZoneID(String timeZoneId)
   {
     setTimeZone(TimeZone.getTimeZone(timeZoneId));
   }

   private synchronized void setTzFormatString(TimeZone tz)
   {
     int zIndex = this._formatString.indexOf("ZZZ");
     if (zIndex >= 0)
     {
       String ss1 = this._formatString.substring(0, zIndex);
       String ss2 = this._formatString.substring(zIndex + 3);
       int tzOffset = tz.getRawOffset();
       StringBuilder sb = new StringBuilder(this._formatString.length() + 10);
       sb.append(ss1);
       sb.append("'");
       if (tzOffset >= 0)
       {
         sb.append('+');
       }
       else
       {
         tzOffset = -tzOffset;
         sb.append('-');
       }
       int raw = tzOffset / 60000;
       int hr = raw / 60;
       int min = raw % 60;
       if (hr < 10) {
         sb.append('0');
       }
       sb.append(hr);
       if (min < 10) {
         sb.append('0');
       }
       sb.append(min);
       sb.append('\'');
       sb.append(ss2);
       this._tzFormatString = sb.toString();
     }
     else
     {
       this._tzFormatString = this._formatString;
     }
     setMinFormatString();
   }

   private void setMinFormatString()
   {
     int i = this._tzFormatString.indexOf("ss.SSS");
     int l = 6;
     if (i >= 0) {
       throw new IllegalStateException("ms not supported");
     }
     i = this._tzFormatString.indexOf("ss");
     l = 2;

     String ss1 = this._tzFormatString.substring(0, i);
     String ss2 = this._tzFormatString.substring(i + l);
     this._minFormatString = (ss1 + "'ss'" + ss2);
   }

   public synchronized String format(Date inDate)
   {
     return format(inDate.getTime());
   }

   public synchronized String format(long inDate)
   {
     long seconds = inDate / 1000L;
     if ((seconds < this._lastSeconds) || ((this._lastSeconds > 0L) && (seconds > this._lastSeconds + __hitWindow)))
     {
       Date d = new Date(inDate);
       return this._tzFormat.format(d);
     }
     if (this._lastSeconds == seconds) {
       return this._lastResult;
     }
     Date d = new Date(inDate);

     long minutes = seconds / 60L;
     if (this._lastMinutes != minutes)
     {
       this._lastMinutes = minutes;
       this._secFormatString = this._minFormat.format(d);
       int i = this._secFormatString.indexOf("ss");
       int l = 2;
       this._secFormatString0 = this._secFormatString.substring(0, i);
       this._secFormatString1 = this._secFormatString.substring(i + l);
     }
     this._lastSeconds = seconds;
     StringBuilder sb = new StringBuilder(this._secFormatString.length());
     sb.append(this._secFormatString0);
     int s = (int)(seconds % 60L);
     if (s < 10) {
       sb.append('0');
     }
     sb.append(s);
     sb.append(this._secFormatString1);
     this._lastResult = sb.toString();
     return this._lastResult;
   }

   public void format(long inDate, StringBuilder buffer)
   {
     buffer.append(format(inDate));
   }

   public SimpleDateFormat getFormat()
   {
     return this._minFormat;
   }

   public String getFormatString()
   {
     return this._formatString;
   }

   public String now()
   {
     long now = System.currentTimeMillis();
     this._lastMs = ((int)(now % 1000L));
     return format(now);
   }

   public int lastMs()
   {
     return this._lastMs;
   }
 }
