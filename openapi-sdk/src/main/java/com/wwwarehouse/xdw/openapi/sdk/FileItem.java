 package com.wwwarehouse.xdw.openapi.sdk;

 import com.wwwarehouse.xdw.openapi.sdk.internal.util.WhUtils;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStream;

 public class FileItem
 {
   private String fileName;
   private String mimeType;
   private byte[] content;
   private File file;

   public FileItem(File file)
   {
     this.file = file;
   }

   public FileItem(String filePath)
   {
     this(new File(filePath));
   }

   public FileItem(String fileName, byte[] content)
   {
     this.fileName = fileName;
     this.content = content;
   }

   public FileItem(String fileName, byte[] content, String mimeType)
   {
     this(fileName, content);
     this.mimeType = mimeType;
   }

   public String getFileName()
   {
     if ((this.fileName == null) && (this.file != null) && (this.file.exists())) {
       this.fileName = this.file.getName();
     }
     return this.fileName;
   }

   public String getMimeType()
     throws IOException
   {
     if (this.mimeType == null) {
       this.mimeType = WhUtils.getMimeType(getContent());
     }
     return this.mimeType;
   }

   public byte[] getContent()
     throws IOException
   {
     if ((this.content == null) && (this.file != null) && (this.file.exists()))
     {
       InputStream in = null;
       ByteArrayOutputStream out = null;
       try
       {
         in = new FileInputStream(this.file);
         out = new ByteArrayOutputStream();
         int ch;
         while ((ch = in.read()) != -1) {
           out.write(ch);
         }
         this.content = out.toByteArray();
       }
       finally
       {
         if (out != null) {
           out.close();
         }
         if (in != null) {
           in.close();
         }
       }
     }
     return this.content;
   }
 }
