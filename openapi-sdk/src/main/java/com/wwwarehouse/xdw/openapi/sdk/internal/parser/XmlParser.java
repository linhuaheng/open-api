 package com.wwwarehouse.xdw.openapi.sdk.internal.parser;

 import com.wwwarehouse.xdw.openapi.sdk.WhException;
 import com.wwwarehouse.xdw.openapi.sdk.response.AbstractResponse;

 public class XmlParser
   implements Parser
 {
   public <T extends AbstractResponse> T parse(String formatString, Class<T> responseClass)
     throws WhException
   {
     return null;
   }
 }
