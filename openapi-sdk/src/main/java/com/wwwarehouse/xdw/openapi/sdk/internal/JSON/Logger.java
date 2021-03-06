package com.wwwarehouse.xdw.openapi.sdk.internal.JSON;

abstract interface Logger
{
  public abstract String getName();
  
  public abstract void warn(String paramString, Object... paramVarArgs);
  
  public abstract void warn(Throwable paramThrowable);
  
  public abstract void warn(String paramString, Throwable paramThrowable);
  
  public abstract void info(String paramString, Object... paramVarArgs);
  
  public abstract void info(Throwable paramThrowable);
  
  public abstract void info(String paramString, Throwable paramThrowable);
  
  public abstract boolean isDebugEnabled();
  
  public abstract void setDebugEnabled(boolean paramBoolean);
  
  public abstract void debug(String paramString, Object... paramVarArgs);
  
  public abstract void debug(Throwable paramThrowable);
  
  public abstract void debug(String paramString, Throwable paramThrowable);
  
  public abstract Logger getLogger(String paramString);
  
  public abstract void ignore(Throwable paramThrowable);
}
