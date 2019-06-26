# Mysql Binlog
## 概念
### 什么是binlog? 
> BinLog是Mysql 维护的一种二进制日志，主要是用来记录对mysql数据更新或者潜在发生更新的SQL
> 语句，并以"事物"的形式保存在磁盘文件中

### 主要用途
- 复制：Mysql的Master-Slave协议，让Slave 可以通过监听Binlog实现数据复制（同步），达到数据一致性的目的。
- 数据恢复： 通过mysql binlog 工具恢复数据
- 增量备份
###支持的格式
- ROW
> 仅保存记录被修改细节，不记录SQL语句上下文相关信息。
> 能非常清晰的记录下每一行数据的修改细节，不需要记录上下文相关信息，因此不会发生某些特定情况下的
> procedure、function、及trigger的调用触发无法被正确复制的问题，任何情况都可以被复制，切能加快从
> 从库重放日志的效率，保证从库数据的一致性
- STATEMENT
> 每一条会修改数据的SQL都会记录在Binlog中，只需要记录执行语句的细节和上下文环境，避免了记录每一行的
> 变化，在一些修改记录较多的情况下，相比ROW类型能大大减少Binlog的日志量，节约IO，提高性能，还可以
> 用于实时的还原，同时主从版本可以不一样，从服务器版本可以比主服务器版本高
- MIXED
> 以上两种类型的混合使用，经过上述比对，可以发现各有优势，如能根据SQL语句取舍可能会有更好的性能和效果，
> MIXED便是以上两种类型的结合。

### 常用命令

| SQl语句 | 语句含义|
| ---- | ----|
|SHOW MASTER LOGS;|查看所有binlog的日志列表|
|SHOW MASTER STATUS;| 查看最后一个Binlog日志的编号名称，以及最后一个事件结束的位置（pos）|
|FLUSH LOGS;| 刷新Binlog, 此刻开始产生一个新编号的binlog日志文件|
|RESET MASTER;|清空所有binlog|
|SHOW BINLOG EVENTS;|查看第一个binlog日志|
|SHOW BINLOG EVENTS IN 'binlog.000035';|查看指定binlog|
|SHOW BINLOG EVENTS IN 'binlog.000035' FROM 100;|从指定位置开始，查看|
|SHOW BINLOG EVENTS IN '' FROM 100 LIMIT 2;|从指定位置开始，限制查询的条数|
|SHOW BINLOG EVENTS IN '' FROM 100 LIMIT 1,2;|从指定位置开始，带有偏移|

### Binlog的Event类型
MySQL Binlog Event 官方提供类型36种，常用的如下：

|Event Type | 事件 | 重要程度|
| ---- | ---- | ---- |
|QUERY| 与数据无关的操作，如begin,drop table,truncate table等|了解即可|
|XID|标记事物提交|了解即可|
|TABLE_MAP|记录下一个操作所对应的表信息，存储了数据库名和表名|非常重要|
|WRITE_ROWS|插入数据|非常重要|
|UPDATE_ROWS|更新数据|非常重要|
|DELETE_ROWS|删除数据|非常重要|
最好的方式是在数据库中创建一张测试表，然后对表进行增删改查操作，然后监控Binlog变化。

## 说明
对于MYSQL Binlog，我们可以不用过分追究Binlog里面到底包含了什么，对于应用的话，我们最重要的是要搞清楚，
Binlog的Event:每个Event都包含header 和data两个部分。
- header
> 提供了Event的创建时间，哪个服务器等信息
- data
> 提供的是针对该Event的具体信息，如具体数据的修改

我们对Binlog的解析，即为对Event的解析
- Binlog 的EventType(需要注意，不同版本的mysql,EventType可能会不同)
- Binlog 中并不会打印数据表的列名