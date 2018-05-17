 package com.wwwarehouse.xdw.openapi.sdk.internal.util;

 import java.io.IOException;
 import org.codehaus.jackson.map.ObjectMapper;

 public final class JsonUtil
 {
   private static final ObjectMapper mapper = new ObjectMapper();

   private JsonUtil()
   {
     throw new UnsupportedOperationException();
   }

   public static String toJson(Object obj)
     throws IOException
   {
     return mapper.writeValueAsString(obj);
   }
 }