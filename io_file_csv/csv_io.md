---
sort: 2
---

# CSV 文件操作

## Step 0: CSV 文件数据读取 {#step-0}

CSV 文件的读取操作实现可以按文本文件的操作实现，先实现把文本文件的全部内容打印出来。

### CSV 文件示例 {#csv-file-demo}

以下是一个简单的 CSV 文件示例([下载](../assets/data/biostats.csv))，CSV 格式规则：

- 分隔符：逗号(,)
- 引用符：引号(")
- 换行符：回车(\r\n)

```csv
"Name",     "Sex", "Age", "Height (in)", "Weight (lbs)"
"Alex",       "M",   41,       74,      170
"Bert",       "M",   42,       68,      166
"Carl",       "M",   32,       70,      155
"Dave",       "M",   39,       72,      167
"Elly",       "F",   30,       66,      124
"Fran",       "F",   33,       66,      115
"Gwen",       "F",   26,       64,      121
"Hank",       "M",   30,       71,      158
"Ivan",       "M",   53,       72,      175
"Jake",       "M",   32,       69,      143
"Kate",       "F",   47,       69,      139
"Luke",       "M",   34,       72,      163
"Myra",       "F",   23,       62,       98
"Neil",       "M",   36,       75,      160
"Omar",       "M",   38,       70,      145
"Page",       "F",   31,       67,      135
"Quin",       "M",   29,       71,      176
"Ruth",       "F",   28,       65,      131
```

### 文本文件读取

以下是实现文本文件打印的示例代码 [TextFileReadDemo.java](../assets/code/TextFileReadDemo.java)。

```java
import java.io.*;

class TextFileReadDemo {

    public void print(String filePath) {
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            // 基于 换行符：回车(\r\n) 的规则实现CSV文件中逐条记录的读取
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        TextFileReadDemo demo = new TextFileReadDemo();
        demo.print(args[0]);
    }
}
```

使用以下命令编译并运行，运行时需要提供一个参数：文本文件的路径，以读取 [`biostats.csv`](../assets/data/biostats.csv) 文件为例：

```shell
javac TextFileReadDemo.java
java TextFileReadDemo biostats.csv
```

运行成功后，运行结果会打印`biostats.csv`文件的内容。

## Step 1: CSV 文件读取 {#step-1}

在 Step 0 中，实现了 CSV 文件文本内容的读取。为了实现 CSV 文件的解析，需要按 CSV 文件格式规则读取数据值，基本的格式规则是：

- 分隔符：逗号(,)
- 引用符：引号(")
- 换行符：回车(\r\n)

目前，暂不考虑在数据值中出现关键字符(如: 在引用符`""`内出现`,`或`\r\n`或`"`)的情况。

下面的解析代码[CSVFileReadDemo.java](../assets/code/CSVFileReadDemo.java)实现了以上三条规则。

```java
import java.io.*;

public class CSVFileReadDemo {

    public void print(String filePath) {
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            // 基于 换行符：回车(\r\n) 的规则实现CSV文件中逐条记录的解析
            while ((str = br.readLine()) != null) {
                // 基于 分隔符：逗号(,) 的规则实现从CSV记录中解析出字段
                String fileds[] = str.split(",");
                for (String s : fileds) {
                    // 基于 引用符：引号(") 的规则解析出字段的内容
                    s = s.replace("\"", "");
                    System.out.print(s);
                    System.out.print("\t");
                }
                System.out.println();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        CSVFileReadDemo demo = new CSVFileReadDemo();
        demo.print(args[0]);
    }
}

```

## Step 2: CSV 文件解析 {#step-2}

以下代码 [CSVFileParseDemo.java](../assets/code/CSVFileParseDemo.java)实现了[`biostats.csv`](../assets/data/biostats.csv)文件的解析，并将数据保存到`Person`对象中。

```java
import java.io.*;
import java.util.*;

public class CSVFileParseDemo {

    public List<Person> parse(String filePath) {
        File file = new File(filePath);
        List<Person> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            System.out.println(br.readLine()); // 打印CSV文件数据字段标签
            while ((str = br.readLine()) != null) {
                String fileds[] = str.split(",");
                list.add(new Person(fileds[0].trim().replace("\"", ""), fileds[1].trim().replace("\"", ""),
                        Integer.parseInt(fileds[2].trim()), Float.parseFloat(fileds[3].trim().replace("\"", "")),
                        Float.parseFloat(fileds[4].trim().replace("\"", ""))));

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String args[]) {
        CSVFileParseDemo demo = new CSVFileParseDemo();
        List<Person> list = demo.parse(args[0]);
        list.stream().forEach(System.out::println);
    }

    class Person {
        String name;
        String sex;
        int age;
        float height;
        float weight;

        public Person(String name, String sex, int age, float height, float weight) {
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.height = height;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Name:\t" + this.name + "\r\nSex:\t" + this.sex + "\r\nAge:\r" + this.age + "\r\nHeight:\t"
                    + this.height + "in\r\nWeight:\t" + this.weight + "lbs";
        }
    }
}
```

```tip
`CSVFileParseDemo`代码实现使用了`stream`特性，需要使用Java 8及以上版本编译。
```

## Step 3: CSV 文件高级规则解析 {#step-3}

Step 0 - 2 仅实现了 CSV 以下三个基本规则的解析：

- 分隔符：逗号(,)
- 引用符：引号(")
- 换行符：回车(\r\n)

[Apache Commons CSV](https://commons.apache.org/proper/commons-csv/)提供了对 多种 CSV 标准规则的支持。
`CSVFormat`类中预定义了以下规则：

- Microsoft Excel
- Informix UNLOAD
- Informix UNLOAD CSV
- MySQL
- Oracle
- PostgreSQL CSV
- PostgreSQL Text
- RFC 4180
- TDF(Tab Separated Values)

```tip
可以尝试自己动手实现更复杂一点的CSV规则的解析，如引用符`""`内出现`,`或`\r\n`或`"`的情况。
```
