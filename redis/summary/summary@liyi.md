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



## redis config
- 获取config  

`config get key`  

        1)key  
        2)value  
`config get *`

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
- hincr, hincrby(无hdecr, hdecrby, 可能通过incr 负数实现
decr， 也体现了作者的极简主义)  
    `hincrby key field incrment`
- hexists 判断属性是否存在(存在返回1)  
    `hexists key field`  
- hlen 属性(字段)个数
    `hlen key`  
- hkeys 返回所有属性  
    `hkeys key`  
- hvals 返回所有属性的values
    `hvals key`
