# redis总结

## 关系型数据库与nosql数据库
- redis全称 remote dictionary server
- 网站的性能瓶颈一般是数据库，一般来说查询也是最多最消耗
资源的操作
- mysql等关系型数据库，数据以文件形式存储在硬盘里面,
redis则把数据存在内存里(也会存在硬盘从而实现数据持久化)，
即直接从内存读取数据，自然运行速度大大高于关系型数据库(存储的
数据结构也影响查询速度)
- 缓存一般分为页面缓存和数据缓存

## redis启动与关闭
- 启动redis服务器  
   `redis-server`  
- 启动redis客户端  
    `redis-cli`此命令启动redis客户端并默认连接本地服务  
    `redis-cli -h host -p port -a password`连接redis远程服务  
- 测试redis客户端连接服务是否启动  
    `PING/ping`返回`PONG`则代表启用  
- shutdown/SHUTDOWN执行以下操作  
    `shutdown [nosave] [save]`  
    - 停止所有客户端
    - 如果有至少一个保存点在等待，执行 SAVE 命令
    - 如果 AOF 选项被打开，更新 AOF 文件
    - 关闭 redis 服务器(server)

## redis config
- 以下命令不区分大小写  
- 获取config  

`config get key`  

        1)key  
        2)value  
`config get *`  
- 获取统计信息  

`info`

## redis 数据结构
### redis数据结构之字符串
- get和set  
    `set key value`  
    `get key`

- incr和decr(increase和decrease，针对数值型字符串)  
    `incr key`  
    `decr key`
    
- incrby和decrby(递增或递减指定数值)
    `incrby key incrment`  
    `decrby key decrment`
    
- append(追加，所有value都被当做字符串在最后添加)

    `set num -1`  
    `append num 10 # 返回-110的位数 4`  
    `get num  # 返回value -110`  
- exists 是否存在给定key  
    `exists key` 
    
- del 删除  
    `del key`
    

### redis数据结构之hash
- 可视作string key和string value的map容器
- hset(即hash set), 可存储多个键值对，但是单次存储一对
```redis
    hset person name liyi
    hset person age 22
    hset person gender 1
    # hset key field value
```
- hmset, 可同时存储多个键值对
```redis
    hmset person name liyi age 22 gender 1
    # hmset key field value [field value ...]
```
- hget, 单次取得一个value  
    `hget key field`
- hgetall 单次获取所有键值对  
    `hgetall key`  
- hmget, 同时获取多个value  
    `hmget key field [field ...]`  
- hdel 删除一个字段  
    `hdel key field`  
- del 删除整个hash  
    `del key`  
- hincr, hincrby 
    `hincrby key field incrment`
- hexists 判断属性是否存在(存在返回1)  
    `hexists key field`  
- hlen 属性(字段)个数
    `hlen key`  
- hkeys 返回所有属性  
    `hkeys key`  
- hvals 返回所有属性的values
    `hvals key`

## 番外
- redis的设计处处透着简洁明了， 比如安装和启动命令，甚至如此，作者的官方redis只支持linux下环境
- hash数据结构中， 只有hincr和hincrby，
而没有hdecr, hdecrby, 可能通过incr 负数实现
decr， 也体现了作者的极简主义