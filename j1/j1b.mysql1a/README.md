# mysql-jdbc-examples

This is a sample project implementing 

- maven
- mysql
- jdbc

## mysql installation on mac
 
- brew install mysql
- brew cask install mysqlworkbench
- brew services mysql start
- brew services mysql stop
 
- mysql -u root -p
- show databases
- use simpledatabase

## setup

```bash

- Download sql connector from https://dev.mysql.com/downloads/connector/j/
Unzip and copy the connector jar to 
- src/main/lib/mysql-connector-java-5.1.45-bin.jar

```

## cli

```bash

 java -cp ${project.loc}/src/main/lib/mysql-connector-java-5.1.46-bin.jar:${project.loc}/target/classes/ d.w3.hw.scheme.depot.lot.JdbcProfile

```