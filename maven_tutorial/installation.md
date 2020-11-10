---
sort: 1
---

# Maven 安装

安装 Maven 只需要将安装包解压后，将包含`mvn`命令的`bin`文件夹路径添加到`PATH`环境变量中。

## 下载 {#maven-download}

从[Maven 官方网站](https://maven.apache.org/download.cgi)下载最新版本的安装文件，如：下载`apache-maven-3.6.3-bin.zip`

## 安装 {#maven-install}

确保已经配置了`JAVA_HOME`环境变量。

```
echo %JAVA_HOME%
```

解压安装包到任意目录，如：`c:/`。

```shell
unzip apache-maven-3.6.3-bin.zip
或者
tar xzvf apache-maven-3.6.3-bin.tar.gz
```

将`apache-maven-3.6.3`文件夹的`bin`目录添加到`PATH`环境变量中。

重启命令终端（`⊞ Win`+`R`, 然后输入`cmd`），使用`mvn -v`命令验证是否配置成功。

## 配置

### `settings.xml` 配置文件

Maven `settings.xml` 配置文件不仅包含了针对工程的配置, 而且还是一个全局性的配置, 另外也可能会包含一个敏感信息, 如: 密码等.

Maven 有两个`settings.xml`配置文件同时有效:

- 在 Maven 安装目录内: `$M2_HOME/conf/settings.xml` (全局配置)
- 在用户目录下: `${user.home}/.m2/settings.xml` (用户配置)

```tip
`{user.home}`是指当前用户目录.

`M2_HOME`是一个环境变量, 表示Maven的安装目录, 配置方式参考`JAVA_HOME`
```

这两个文件都不是必须的, 如果同时存在, 那么用户目录下的配置会覆盖全局的配置.

如果不确定两个文件是否存在, 也可以使用 `mvn -X`命令打印出 Maven 的配置信息.

```console
shell> mvn -X
Apache Maven 3.0.3 (r1075438; 2011-02-28 18:31:09+0100)
Maven home: /usr/java/apache-maven-3.0.3
Java version: 1.6.0_12, vendor: Sun Microsystems Inc.
Java home: /usr/java/jdk1.6.0_12/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "2.6.32-32-generic", arch: "i386", family: "unix"
[INFO] Error stacktraces are turned on.
[DEBUG] Reading global settings from /usr/java/apache-maven-3.0.3/conf/settings.xml
[DEBUG] Reading user settings from /home/myhome/.m2/settings.xml
```

```tip
注意 `[DEBUG] Reading global settings from` 和 `[DEBUG] Reading user settings from` 后面的信息
```

### 修改国内镜像

在`settings.xml`文件中添加以下代理配置.

```
<mirrors>
    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
  　　<url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
</mirrors>
```
