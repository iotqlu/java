---
sort: 1
---

# Java Web 文件上传Servlet

使用Java Servelt实现文件上传的代码实现分为两部分:

1. 前端实现: 使用html或jsp实现文件上传的form表单.

1. 后端实现: 使用servlet接收前端发送的请求, 从表单参数中提取出文件数据, 然后保存到服务器端的存储中.

## 文件上传HTML页面

阅读以下`file_upload.jsp`文件的代码, 此段代码实现了一个上传文件的form表单, 通过`POST`请求的方式, 传递`num`(学号)和`photo`(图片)两个参数给服务端的`FileUploadServlet`路径地址.

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Photo Upload</title>
</head>
<body>
<form action="FileUploadServlet" method="post" enctype="multipart/form-data">
学号：<input type="text" name="num" size="15"/><br>
上传相片：<input type="file" name="photo">
<input type="submit" value="上传图片"/>
</form>
</body>
</html>
```

关键代码讲解.

```html
<form action="FileUploadServlet" method="post" enctype="multipart/form-data">
```
这段代码声明了form表单的请求地址是`FileUploadServlet`, 为了实现文件的上传功能, form表单中必需要添加`enctype="multipart/form-data"`声明.

```html
上传相片：<input type="file" name="photo">
```
上传文件对应的类型是`type="file"`.

```tip
`file_upload.jsp`文件不能放在`WEB-INF`目录下面, 因为`WEB-INF`目录下面的文件不能通过URI直接访问. 建议放在 `WEB-INF`目录平级的目录下面, 这样就不需要修改form表单的`action`属性值.
```

## 文件上传Servlet

首先阅读`FileUploadServlet.java`文件的代码, 这段代码实现了接收前端通过POST请求`/FileUploadServlet` 路径的参数和文件(图片)数据, 并按学号命名文件名(`{学号}.jpg`)后保存到`C:/`目录下.

```java
package com.demo;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/FileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String num = request.getParameter("num");
        Part photo = request.getPart("photo");

        String filename = num+".jpg";
        String rootpath = "c:/";

        InputStream is = photo.getInputStream();
        OutputStream os = new FileOutputStream(rootpath+filename);

        byte[] buf = new byte[8192];
        int length;
        while((length=is.read(buf))!=-1){
            os.write(buf, 0, length);
        }
        os.flush();
        os.close();

        response.getWriter().println("file upload success!");
    }
}

```

下面讲解一下关键代码.

```java
@WebServlet("/FileUploadServlet")
```
这行代码声明Servlet映射的路径, 这里的路径对应前端页面form表单的请求(`action`)地址.

```java
@MultipartConfig
```
让Servet实现文件上传的功能, 必需在声明Servlet时添加`@MultipartConfig`注解标识, 这里对应前端页面form表单声明时添加的`enctype="multipart/form-data"`属性.

```java
String num = request.getParameter("num");
Part photo = request.getPart("photo");
```
以上代码分别示例了从`request`对象中参数取值和二进制文件取值的代码, 从前端发送的数据`photo`是二进制的图片数据,需要使用`HttpServletRequest`的`getPart()`方法获取文件流的引用, 然后通过文件读写流操作, 将图片数据按学号命名保存到存储中, 在此例子中, 文件保存到了 `C:/`目录下面.

```java
byte[] buf = new byte[8192];
int length;
while((length=is.read(buf))!=-1){
    os.write(buf, 0, length);
}
```
这段代码实现了将文件保存到存储中, 代码实现的逻辑是每次读取只申请`8k`的内存空间. 

```tip
每次申请内存空间的大小, 由JVM的内存容量决定, 申请太大的内存空间会增加JVM的运行负担, 申请小的内存空间会减少对内存的压力, 但是, 申请过于小的内存空间也是没有意义的, 比如: 小于512字节, 因为会浪费操作系统的IO操作, 而IO是最慢的一种操作. 硬盘存储的扇区大小默认标准是512字节, 操作系统进行硬盘的读操作时, 每次会连续读取多个扇区的数据, 即512字节的倍数. 
```

 



