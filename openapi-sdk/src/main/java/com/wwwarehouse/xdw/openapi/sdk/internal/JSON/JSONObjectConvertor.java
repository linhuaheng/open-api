 package com.wwwarehouse.xdw.openapi.sdk.internal.JSON;

 import java.lang.reflect.Method;
 import java.lang.reflect.Modifier;
 import java.util.Arrays;
 import java.util.HashSet;
 import java.util.Map;
 import java.util.Set;

 public class JSONObjectConvertor
   implements JSON.Convertor
 {
   private boolean _fromJSON;
   private Set _excluded = null;

   public JSONObjectConvertor()
   {
     this._fromJSON = false;
   }

   public JSONObjectConvertor(boolean fromJSON)
   {
     this._fromJSON = fromJSON;
   }

   public JSONObjectConvertor(boolean fromJSON, String[] excluded)
   {
     this._fromJSON = fromJSON;
     if (excluded != null) {
       this._excluded = new HashSet(Arrays.asList(excluded));
     }
   }

   public Object fromJSON(Map map)
   {
     if (this._fromJSON) {
       throw new UnsupportedOperationException();
     }
     return map;
   }

   public void toJSON(Object obj, JSON.Output out)
   {
     try
     {
       Class c = obj.getClass();
       if (this._fromJSON) {
         out.addClass(obj.getClass());
       }
       Method[] methods = obj.getClass().getMethods();
       for (int i = 0; i < methods.length; i++)
       {
         Method m = methods[i];
         if ((!Modifier.isStatic(m.getModifiers())) && (m.getParameterTypes().length == 0) && (m.getReturnType() != null) && (m.getDeclaringClass() != Object.class))
         {
           String name = m.getName();
           if (name.startsWith("is"))
           {
             name = name.substring(2, 3).toLowerCase() + name.substring(3);
           }
           else
           {
             if (!name.startsWith("get")) {
               continue;
             }
             name = name.substring(3, 4).toLowerCase() + name.substring(4);
           }
           if (includeField(name, obj, m)) {
             out.add(name, m.invoke(obj, (Object[])null));
           }
         }
       }
     }
     catch (Throwable e)
     {
       throw new IllegalArgumentException(e);
     }
   }

   protected boolean includeField(String name, Object o, Method m)
   {
     return (this._excluded == null) || (!this._excluded.contains(name));
   }
 }
