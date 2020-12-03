---
sort: 2
---

# Java Web 文件下载Servlet

本次实验使用在浏览器端查看学生相片的例子, 讲解文件下载Servlet的实现.

查看学生相片的例子实现代码包括两部分, :

1. 后端Servlet代码. 通过解析请求路径的参数(学号), 将文件数据(相片)发送给前端.
1. 前端页面. 使用`<img>`标签引用服务器端的相片下载地址.

与文件上传例子的讲解顺序相反, 这个例子, 先讲解服务器端文件下载的代码实现.

## 文件下载Servlet

阅读`FileDownloadServlet.java`的代码. 这个代码先解析请求路径的 `num`(学号)参数, 然后根据 `{学号}.jpg`的格式到`C:/`目录下查找文件, 如果文件存在, 则将文件内容发送给浏览器端.

```java
package com.demo;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/FileDownloadServlet")
public class FileDownloadServlet extends HttpServlet{
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String num = request.getParameter("num");
        
        InputStream is = new FileInputStream("c:/"+num+".jpg");

        response.setContentType("image/jpeg");
        response.setContentLength(is.available());

        OutputStream os = response.getOutputStream();
        byte[] buf = new byte[8192];
        int length;
        while((length=is.read(buf))!=-1){
            os.write(buf,0,length);
        }
        os.close();
    }
}
```

讲解关键代码:

```java
@WebServlet("/FileDownloadServlet")
```
这行代码使用注解的方式声明Servet映射的路径.

```java
InputStream is = new FileInputStream("c:/"+num+".jpg");
```

这行代码实现了相片文件读取流的获取.

```java
response.setContentType("image/jpeg");
response.setContentLength(is.available());
```

这两行代码分别在HTTP的头部信息中声明文件的类型和文件的大小. 浏览器根据文件的类型决定如果处理(显示)文件, 根据文件的大小显示下载的进度.

```tip
`response.setContentLength()`方法的重要性在于, 浏览器可以根据这个值显示文件下载的进度, 让文件下载更加人性化.
```

```java
OutputStream os = response.getOutputStream();
byte[] buf = new byte[8192];
int length;
while((length=is.read(buf))!=-1){
    os.write(buf,0,length);
}
os.close();
```
这段代码实现将文件(相片)数据用流的方式写给浏览器端.

## 前端页面

如下为`file_download.jsp`文件的代码. 这个文件*不能*放在`WEB-INF`目录下面, 可以放在`WEB-INF`平级的目录下面.

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Download</title>
</head>
<body>
<label>001</label>
<img src="FileDownloadServlet?num=001" width="100pt"></img><br>
<label>002</label>
<img src="FileDownloadServlet?num=002" width="100pt"></img>
</body>
</html>
```