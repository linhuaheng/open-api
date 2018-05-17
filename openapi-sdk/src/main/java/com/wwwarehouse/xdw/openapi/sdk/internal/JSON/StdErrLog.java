 package com.wwwarehouse.xdw.openapi.sdk.internal.JSON;

 import java.io.PrintStream;
 import java.security.AccessControlException;
 import java.util.Properties;

 class StdErrLog
   extends AbstractLogger
 {
   private static final String EOL = System.getProperty("line.separator");
   private static DateCache _dateCache;
   private static final Properties __props = new Properties();
   private static final boolean __source = Boolean.parseBoolean(Log.__props.getProperty("org.eclipse.jetty.util.log.SOURCE", Log.__props.getProperty("org.eclipse.jetty.util.log.stderr.SOURCE", "false")));
   private static final boolean __long = Boolean.parseBoolean(Log.__props.getProperty("org.eclipse.jetty.util.log.stderr.LONG", "false"));
   public static final int LEVEL_ALL = 0;
   public static final int LEVEL_DEBUG = 1;
   public static final int LEVEL_INFO = 2;
   public static final int LEVEL_WARN = 3;

   static
   {
     __props.putAll(Log.__props);
     String[] deprecatedProperties = { "DEBUG", "org.eclipse.jetty.util.log.DEBUG", "org.eclipse.jetty.util.log.stderr.DEBUG" };
     for (String deprecatedProp : deprecatedProperties) {
       if (System.getProperty(deprecatedProp) != null) {
         System.err.printf("System Property [%s] has been deprecated! (Use org.eclipse.jetty.LEVEL=DEBUG instead)%n", new Object[] { deprecatedProp });
       }
     }
     try
     {
       _dateCache = new DateCache("yyyy-MM-dd HH:mm:ss");
     }
     catch (Exception x)
     {
       x.printStackTrace(System.err);
     }
   }

   private int _level = 2;
   private int _configuredLevel;
   private PrintStream _stderr = null;
   private boolean _source = __source;
   private boolean _printLongNames = __long;
   private final String _name;
   private final String _abbrevname;
   private boolean _hideStacks = false;

   public StdErrLog()
   {
     this(null);
   }

   public StdErrLog(String name)
   {
     this(name, __props);
   }

   public StdErrLog(String name, Properties props)
   {
     if ((props != null) && (props != __props)) {
       __props.putAll(props);
     }
     this._name = (name == null ? "" : name);
     this._abbrevname = condensePackageString(this._name);
     this._level = getLoggingLevel(props, this._name);
     this._configuredLevel = this._level;
     try
     {
       this._source = Boolean.parseBoolean(props.getProperty(this._name + ".SOURCE", Boolean.toString(this._source)));
     }
     catch (AccessControlException ace)
     {
       this._source = __source;
     }
   }

   public static int getLoggingLevel(Properties props, String name)
   {
     String nameSegment = name;
     while ((nameSegment != null) && (nameSegment.length() > 0))
     {
       String levelStr = props.getProperty(nameSegment + ".LEVEL");

       int level = getLevelId(nameSegment + ".LEVEL", levelStr);
       if (level != -1) {
         return level;
       }
       int idx = nameSegment.lastIndexOf('.');
       if (idx >= 0) {
         nameSegment = nameSegment.substring(0, idx);
       } else {
         nameSegment = null;
       }
     }
     return getLevelId("log.LEVEL", props.getProperty("log.LEVEL", "INFO"));
   }

   protected static int getLevelId(String levelSegment, String levelName)
   {
     if (levelName == null) {
       return -1;
     }
     String levelStr = levelName.trim();
     if ("ALL".equalsIgnoreCase(levelStr)) {
       return 0;
     }
     if ("DEBUG".equalsIgnoreCase(levelStr)) {
       return 1;
     }
     if ("INFO".equalsIgnoreCase(levelStr)) {
       return 2;
     }
     if ("WARN".equalsIgnoreCase(levelStr)) {
       return 3;
     }
     System.err.println("Unknown StdErrLog level [" + levelSegment + "]=[" + levelStr + "], expecting only [ALL, DEBUG, INFO, WARN] as values.");
     return -1;
   }

   protected static String condensePackageString(String classname)
   {
     String[] parts = classname.split("\\.");
     StringBuilder dense = new StringBuilder();
     for (int i = 0; i < parts.length - 1; i++) {
       dense.append(parts[i].charAt(0));
     }
     if (dense.length() > 0) {
       dense.append('.');
     }
     dense.append(parts[(parts.length - 1)]);
     return dense.toString();
   }

   public String getName()
   {
     return this._name;
   }

   public void setPrintLongNames(boolean printLongNames)
   {
     this._printLongNames = printLongNames;
   }

   public boolean isPrintLongNames()
   {
     return this._printLongNames;
   }

   public boolean isHideStacks()
   {
     return this._hideStacks;
   }

   public void setHideStacks(boolean hideStacks)
   {
     this._hideStacks = hideStacks;
   }

   public boolean isSource()
   {
     return this._source;
   }

   public void setSource(boolean source)
   {
     this._source = source;
   }

   public void warn(String msg, Object... args)
   {
     if (this._level <= 3)
     {
       StringBuilder buffer = new StringBuilder(64);
       format(buffer, ":WARN:", msg, args);
       (this._stderr == null ? System.err : this._stderr).println(buffer);
     }
   }

   public void warn(Throwable thrown)
   {
     warn("", thrown);
   }

   public void warn(String msg, Throwable thrown)
   {
     if (this._level <= 3)
     {
       StringBuilder buffer = new StringBuilder(64);
       format(buffer, ":WARN:", msg, thrown);
       (this._stderr == null ? System.err : this._stderr).println(buffer);
     }
   }

   public void info(String msg, Object... args)
   {
     if (this._level <= 2)
     {
       StringBuilder buffer = new StringBuilder(64);
       format(buffer, ":INFO:", msg, args);
       (this._stderr == null ? System.err : this._stderr).println(buffer);
     }
   }

   public void info(Throwable thrown)
   {
     info("", thrown);
   }

   public void info(String msg, Throwable thrown)
   {
     if (this._level <= 2)
     {
       StringBuilder buffer = new StringBuilder(64);
       format(buffer, ":INFO:", msg, thrown);
       (this._stderr == null ? System.err : this._stderr).println(buffer);
     }
   }

   public boolean isDebugEnabled()
   {
     return this._level <= 1;
   }

   public void setDebugEnabled(boolean enabled)
   {
     if (enabled)
     {
       this._level = 1;
       for (Logger log : Log.getLoggers().values()) {
         if ((log instanceof StdErrLog)) {
           ((StdErrLog)log).setLevel(1);
         }
       }
     }
     else
     {
       this._level = this._configuredLevel;
       for (Logger log : Log.getLoggers().values()) {
         if ((log instanceof StdErrLog)) {
           ((StdErrLog)log).setLevel(((StdErrLog)log)._configuredLevel);
         }
       }
     }
   }

   public int getLevel()
   {
     return this._level;
   }

   public void setLevel(int level)
   {
     this._level = level;
   }

   public void setStdErrStream(PrintStream stream)
   {
     this._stderr = (stream == System.err ? null : stream);
   }

   public void debug(String msg, Object... args)
   {
     if (this._level <= 1)
     {
       StringBuilder buffer = new StringBuilder(64);
       format(buffer, ":DBUG:", msg, args);
       (this._stderr == null ? System.err : this._stderr).println(buffer);
     }
   }

   public void debug(Throwable thrown)
   {
     debug("", thrown);
   }

   public void debug(String msg, Throwable thrown)
   {
     if (this._level <= 1)
     {
       StringBuilder buffer = new StringBuilder(64);
       format(buffer, ":DBUG:", msg, thrown);
       (this._stderr == null ? System.err : this._stderr).println(buffer);
     }
   }

   private void format(StringBuilder buffer, String level, String msg, Object... args)
   {
     String d = _dateCache.now();
     int ms = _dateCache.lastMs();
     tag(buffer, d, ms, level);
     format(buffer, msg, args);
   }

   private void format(StringBuilder buffer, String level, String msg, Throwable thrown)
   {
     format(buffer, level, msg, new Object[0]);
     if (isHideStacks()) {
       format(buffer, String.valueOf(thrown), new Object[0]);
     } else {
       format(buffer, thrown);
     }
   }

   private void tag(StringBuilder buffer, String d, int ms, String tag)
   {
     buffer.setLength(0);
     buffer.append(d);
     if (ms > 99) {
       buffer.append('.');
     } else if (ms > 9) {
       buffer.append(".0");
     } else {
       buffer.append(".00");
     }
     buffer.append(ms).append(tag);
     if (this._printLongNames) {
       buffer.append(this._name);
     } else {
       buffer.append(this._abbrevname);
     }
     buffer.append(':');
     if (this._source)
     {
       Throwable source = new Throwable();
       StackTraceElement[] frames = source.getStackTrace();
       for (int i = 0; i < frames.length; i++)
       {
         StackTraceElement frame = frames[i];
         String clazz = frame.getClassName();
         if ((!clazz.equals(StdErrLog.class.getName())) && (!clazz.equals(Log.class.getName())))
         {
           if ((!this._printLongNames) && (clazz.startsWith("org.eclipse.jetty."))) {
             buffer.append(condensePackageString(clazz));
           } else {
             buffer.append(clazz);
           }
           buffer.append('#').append(frame.getMethodName());
           if (frame.getFileName() != null) {
             buffer.append('(').append(frame.getFileName()).append(':').append(frame.getLineNumber()).append(')');
           }
           buffer.append(':');
           break;
         }
       }
     }
   }

   private void format(StringBuilder builder, String msg, Object... args)
   {
     if (msg == null)
     {
       msg = "";
       for (int i = 0; i < args.length; i++) {
         msg = msg + "{} ";
       }
     }
     String braces = "{}";
     int start = 0;
     for (Object arg : args)
     {
       int bracesIndex = msg.indexOf(braces, start);
       if (bracesIndex < 0)
       {
         escape(builder, msg.substring(start));
         builder.append(" ");
         builder.append(arg);
         start = msg.length();
       }
       else
       {
         escape(builder, msg.substring(start, bracesIndex));
         builder.append(String.valueOf(arg));
         start = bracesIndex + braces.length();
       }
     }
     escape(builder, msg.substring(start));
   }

   private void escape(StringBuilder builder, String string)
   {
     for (int i = 0; i < string.length(); i++)
     {
       char c = string.charAt(i);
       if (Character.isISOControl(c))
       {
         if (c == '\n') {
           builder.append('|');
         } else if (c == '\r') {
           builder.append('<');
         } else {
           builder.append('?');
         }
       }
       else {
         builder.append(c);
       }
     }
   }

   private void format(StringBuilder buffer, Throwable thrown)
   {
     if (thrown == null)
     {
       buffer.append("null");
     }
     else
     {
       buffer.append(EOL);
       format(buffer, thrown.toString(), new Object[0]);
       StackTraceElement[] elements = thrown.getStackTrace();
       for (int i = 0; (elements != null) && (i < elements.length); i++)
       {
         buffer.append(EOL).append("\tat ");
         format(buffer, elements[i].toString(), new Object[0]);
       }
       Throwable cause = thrown.getCause();
       if ((cause != null) && (cause != thrown))
       {
         buffer.append(EOL).append("Caused by: ");
         format(buffer, cause);
       }
     }
   }

   protected Logger newLogger(String fullname)
   {
     StdErrLog logger = new StdErrLog(fullname);

     logger.setPrintLongNames(this._printLongNames);

     logger.setSource(this._source);
     logger._stderr = this._stderr;
     return logger;
   }

   public String toString()
   {
     StringBuilder s = new StringBuilder();
     s.append("StdErrLog:");
     s.append(this._name);
     s.append(":LEVEL=");
     switch (this._level)
     {
     case 0:
       s.append("ALL");
       break;
     case 1:
       s.append("DEBUG");
       break;
     case 2:
       s.append("INFO");
       break;
     case 3:
       s.append("WARN");
       break;
     default:
       s.append("?");
     }
     return s.toString();
   }

   public static void setProperties(Properties props)
   {
     __props.clear();
     __props.putAll(props);
   }

   public void ignore(Throwable ignored)
   {
     if (this._level <= 0)
     {
       StringBuilder buffer = new StringBuilder(64);
       format(buffer, ":IGNORED:", "", ignored);
       (this._stderr == null ? System.err : this._stderr).println(buffer);
     }
   }
 }
