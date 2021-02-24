# 如何从源码编译Spring Framework，并且在IDEA中调试
我尝试了截止目前（2021年2月24日）最新的版本5.3.4.RELEASE，虽然能够运行，但是所有Type都会index到kotlin或者Groovy文件夹中，
导致满眼飘红，而且无法自动import，更无法代码提示，使用起来很不方便。  
因此尝试切换到稍低版本，也就是5.2.15.SNAPSHOT，现在就没有问题了。  

回头会单独抽时间找出5.3.4导入IDEA异常的问题。

## 准备工作

### 准备代码
clone SpringFramework from github，当然clone速度比较慢，如果愿意稍微配置一下git的代理应该会快一些。  
当然，也可以直接开着电脑或者浏览器的代理，在github网页上下载code zip，解压缩后就可以了。  

### 安装合适版本的jdk
接下来安装jdk。由于我电脑上有多版本JDK，为了方便管理，便使用了SDKMAN这个工具。gradle也可以通过它来管理。  

```shell
sdk list java #列出所有java的candidate
sdk install java <version>  #根据上一条命令列出的candidate对应的version，完整替换<version>
```
安装完成后，通过以下命令测试gradle是否已经安装成功并且可以使用：
```shell
java -version
which java  # 查看java路径
```
如果运行失败的话，首先去~/.sdkman/candidates/java中查看java的路径，然后在系统环境变量中添加，这个网上有很多教程，我就不再介绍了。

## 导入IDEA
在导入IDEA之前，根据[官方的建议](https://github.com/spring-projects/spring-framework/blob/master/import-into-idea.md)，
应当在spring源码文件夹中执行下面这条命令，：
```shell
./gradlew :spring-oxm:compileTestJava
```

然后打开IDEA，在`File -> New -> Project from Existing Sources...`打开新建项目窗口，然后选择spring源码的`build.gradle`文件。  
第一次导入时，可能会花费数十分钟（十几分钟到几十分钟不等），其主要过程是Download gradle、Sync dependencies。
所以要么使用代理，要么提前将gradle下载好进行替换，并将remote repository添加诸如aliyun等，这个网上也有一大堆。就不介绍了。  

接下来我检查了一下，各个依赖都没有问题了，IDEA中也不飘红了。  

## 测试
网上目前有2种测试方法，要么直接在某个Module中添加自己的测试代码，比如在spring-context/src/main/java/下添加自己的package和java。
这种方式比较粗暴，但是不用担心各种新出现的依赖问题。但是缺点也很明显，不***美***，整个项目层级都被搞乱了。  

所以我推崇第二种方式，也就是自己建一个Module。  
建module比较简单，就不写了，但是看到`build.gradle`中第18行，
```properties
    moduleProjects = subprojects.findAll { it.name.startsWith("spring-") }
```
如果要想顺利编译（连带着自己写的和spring源码），那么module名字应当以`spring-`开头。所以我的Module名就是`spring-wudidebug`。  

当然，这里是WuDi debug的意思，不是WuDi的bug。  

然后将比如spirng-web下的spring-web.gradle复制到自建的module文件夹内，并重命名为"<moduleName>.gradle"的形式，
这是在`settings.gradle`中规定的。
```properties
  project.buildFileName = "${project.name}.gradle"
```

接下来，最重要的，在`settings.gradle`中，那么多的include里，添加一个`include "spring-wudidebug`。
这么做的目的是为运行时添加上与其他module关联的classpath。不然虽然IDE的editor不报错，但是运行时可能会找不到相关的class。  

最后就是编写相关的代码了。这些参见spring-wudidebug模块。