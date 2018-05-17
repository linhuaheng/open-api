 package com.wwwarehouse.xdw.openapi.sdk.internal.JSON;

 import java.util.Map;

 public class JSONPojoConvertorFactory
   implements JSON.Convertor
 {
   private final JSON _json;
   private final boolean _fromJson;

   public JSONPojoConvertorFactory(JSON json)
   {
     if (json == null) {
       throw new IllegalArgumentException();
     }
     this._json = json;
     this._fromJson = true;
   }

   public JSONPojoConvertorFactory(JSON json, boolean fromJSON)
   {
     if (json == null) {
       throw new IllegalArgumentException();
     }
     this._json = json;
     this._fromJson = fromJSON;
   }

   public void toJSON(Object obj, JSON.Output out)
   {
     String clsName = obj.getClass().getName();
     JSON.Convertor convertor = this._json.getConvertorFor(clsName);
     if (convertor == null) {
       try
       {
         Class cls = Loader.loadClass(JSON.class, clsName);
         convertor = new JSONPojoConvertor(cls, this._fromJson);
         this._json.addConvertorFor(clsName, convertor);
       }
       catch (ClassNotFoundException e)
       {
         JSON.LOG.warn(e);
       }
     }
     if (convertor != null) {
       convertor.toJSON(obj, out);
     }
   }

   public Object fromJSON(Map object)
   {
     Map map = object;
     String clsName = (String)map.get("class");
     if (clsName != null)
     {
       JSON.Convertor convertor = this._json.getConvertorFor(clsName);
       if (convertor == null) {
         try
         {
           Class cls = Loader.loadClass(JSON.class, clsName);
           convertor = new JSONPojoConvertor(cls, this._fromJson);
           this._json.addConvertorFor(clsName, convertor);
         }
         catch (ClassNotFoundException e)
         {
           JSON.LOG.warn(e);
         }
       }
       if (convertor != null) {
         return convertor.fromJSON(object);
       }
     }
     return map;
   }
 }
