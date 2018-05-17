 package com.wwwarehouse.xdw.openapi.sdk.internal.JSON;

 import java.lang.reflect.Method;
 import java.util.Map;

 public class JSONEnumConvertor
   implements JSON.Convertor
 {
   private static final Logger LOG = Log.getLogger(JSONEnumConvertor.class);
   private boolean _fromJSON;
   private Method _valueOf;

   public JSONEnumConvertor()
   {
     this(false);
   }

   public JSONEnumConvertor(boolean fromJSON)
   {
     try
     {
       Class e = Loader.loadClass(getClass(), "java.lang.Enum");
       this._valueOf = e.getMethod("valueOf", new Class[] { Class.class, String.class });
     }
     catch (Exception e)
     {
       throw new RuntimeException("!Enums", e);
     }
     this._fromJSON = fromJSON;
   }

   public Object fromJSON(Map map)
   {
     if (!this._fromJSON) {
       throw new UnsupportedOperationException();
     }
     try
     {
       Class c = Loader.loadClass(getClass(), (String)map.get("class"));
       return this._valueOf.invoke(null, new Object[] { c, map.get("value") });
     }
     catch (Exception e)
     {
       LOG.warn(e);
     }
     return null;
   }

   public void toJSON(Object obj, JSON.Output out)
   {
     if (this._fromJSON)
     {
       out.addClass(obj.getClass());
       out.add("value", obj.toString());
     }
     else
     {
       out.add(obj.toString());
     }
   }
 }
