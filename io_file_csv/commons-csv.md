---
sort: 3
---

# 使用 Commons-CSV 库解析 CSV

## 创建 Maven 工程

### 初始化 Maven 工程

使用以下命令初始化工程`commons-csv-demo`, `groupId`是工程代码的包名, `archetypeArtifactId`是工程模板.

```
mvn archetype:generate -DgroupId=com.iotqlu.java -DartifactId=commons-csv-demo -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```

### 添加 Commons-CSV 库依赖

在`pom.xml`文件的`<dependencies>`标签下添加`commons-csv`包依赖声明.

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-csv</artifactId>
    <version>1.8</version>
</dependency>
```

### 工程编译运行

使用以下命令编译工程

```
mvn compile
```

编译完成后, 使用以下命令运行工程, 运行成功会打印出 `Hello World!`.

```
mvn exec:java -Dexec.mainClass="com.iotqlu.java.App"
```

## 文件解析

### 解析 Excel CSV 文件

使用 commons-csv 解析 excel csv 文件的示例代码如下:

```java
Reader in = new FileReader("path/to/file.csv");
Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
for (CSVRecord record : records) {
    String lastName = record.get(1);
    String firstName = record.get(2);
}
```

`CSVFormat.EXCEL`是一种预设的规则:

- 分隔符(',')
- 引用符('"')
- 换行符("\r\n")
- 忽略空行(false)
- 允许列名缺失(true)
- 允许重复标题(true)

文件[biostats_excel.csv](../assets/data/biostats_excel.csv)是从 Excel 中导出的一个 CSV 格式示例文件.

实现`biostats_excel.csv`文件读取的代码[App.java]("../assets/project/commons-csv-demo/src/main/java/com/iotqlu/java/App.java")如下所示:

```java
package com.iotqlu.java;

import java.io.*;
import org.apache.commons.csv.*;

public class App {

    public void parseExcelCSV(String filePath) {
        try {
            Reader in = new FileReader(filePath);
            // 设置字段名, 可以跟文件中真实列名不同;
            // 如果不预设列名, 则只能按 index 访问;
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader("name", "sex", "age", "height", "weight")
                    .parse(in);
            // 如果使用csv文件第一行为字段名, 使用withFirstRecordAsHeader()方法.

            for (CSVRecord record : records) {
                String name = record.get("name");
                String sex = record.get(2); // 1-based
                String age = record.get("age");
                String height = record.get("height");
                String weight = record.get("weight");

                System.out.println(name);
                System.out.println(sex);
                System.out.println(age);
                System.out.println(height);
                System.out.println(weight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.parseExcelCSV(args[0]);
    }
}

```

使用以下命令编译并运行, 注意 csv 文件的存放路径, 默认为工程目录下.

```shell
mvn compile && mvn exec:java -Dexec.mainClass="com.iotqlu.java.App" -Dexec.args="biostats_excel.csv"
```
