#编译运行

##说明
此工程采用SpringBoot + JDBC集成hive。

在工程目录下，执行如下命令：
`mvn clean package -DskipTests`
编译成功后在target会找到indu-hive2-api.jar
为减少jar体积，打包时将依赖、配置与代码分离，部署时确保resource、lib、.jar在同级目录
运行采用如下命令：
`nohup java -jar indu-hive2-api.jar >/dev/null &` 
停止使用命令： `ps -ef|grep indu-hive2-api.jar`找到pid kill

#端口与数据库参数配置
在src/main/resources目录下application.properties
