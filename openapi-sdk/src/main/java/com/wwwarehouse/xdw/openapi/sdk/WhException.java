 package com.wwwarehouse.xdw.openapi.sdk;

 public class WhException
   extends Exception
 {
   private static final long serialVersionUID = -7035498848577048685L;
   private String errCode;
   private String errMsg;

   public WhException() {}

   public WhException(String message, Throwable cause)
   {
     super(message, cause);
   }

   public WhException(String message)
   {
     super(message);
   }

   public WhException(Throwable cause)
   {
     super(cause);
   }

   public WhException(String errCode, String errMsg)
   {
     super(errCode + ": " + errMsg);
     this.errCode = errCode;
     this.errMsg = errMsg;
   }

   public String getErrCode()
   {
     return this.errCode;
   }

   public String getErrMsg()
   {
     return this.errMsg;
   }
 }
