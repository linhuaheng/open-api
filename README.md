# 网仓3号开放平台

## 说明


## 项目结构说明
- xxx-common
    - 主要是定义各种元数据和接口生命。比如定义个商品类Item，定义了个商品获取的接口ItemService。这个包的pom依赖比较纯，不会依赖各种二方和三方包，防止被依赖方的工程里各种jar包冲突。调用方只要拿这个包就可以获取服务了。
- xxx-dao
    - 数据库或者缓存相关的逻辑操作。比如定义了ItemDO，DO = Database Object或者Data Object，为什么有了Item还要定义ItemDO呢？在现实环境里，Item会有个字段是features，Map类型的，放各种k-v字段，比如是否是海外商品，isOversea这个字段，是属于扩展信息，在数据库里会把features json序列化或者以某种固定格式转成String保存。那关于Item和ItemDO之间的转换可以通过工具类方法BeanUtils.copyProperties来做映射。
- xxx-biz
    - 这部分是做业务逻辑的，可以认为里面包含了Manager（调用DAO里的方法，做相关的组装），除此之外，还有各种业务计算，基本上工程里面逻辑业务比较多会在里面。然后package划分会根据模块和业务来做具体的划分
- xxx-web
    - 单独部署的页面资源、AO都在里面，包含各种容器启动配置，还有一些bean配置等。
- xxx-client
    - 封装了一些方法的，一般来说包含common的服务。比如调用商品我们一般不会直接用RPC的，先看看redis里有没有数据。那client里有通用方法，先去redis拿数据，如果没有再RPC拿数据。对于使用方来说，这个方法就是拿了Item，具体怎么拿就是client包里实现了。拓展开来，Item依赖了类目、店铺，那如果要拿到最全的数据，可以在client包里封装好方法，省得那么多使用方去写代码。当然这里依赖的包会挺多的，建议部分通用的包在maven里设置scope为provider。

以上是项目中通用的结构划分,其中client不是必须有的.在datasync项目中,由biz主要提供服务,web可能会删除,用网关代替.

- datasync-outer
    - 由于第三方接口比较多,导致biz的manager比较大,因此分离出和第三方交互的部分.这里纯粹实现和第三方交互,不依赖6+1的任何部分.如果第三方封装了sdk,无需再写额外的交互代码,可以不在outer里写.

## 用到的技术
- mongodb 
    - 请求返回日志
- redis
- oracle
    - 解析回来的业务数据
- log4j
    - 本身日志
- ELK `暂时与其他模块共用一套`
    - 对日志的收集展示
- BI
    - 存储请求返回日志。给后续提供查询。
- dubbox
    - 提供方。提供接口
- mq
    - 主动推动式的，采用mq
- springmvc
    
- mybatis
  
- elastic job


## 模块
- 目前要做的
    - 支付
    - 电商平台
        - 淘宝
        - 京东
        - 唯品会
    - 授权（微信）
    - 快递
    - 短信
    - 平台与6+1数据同步
    
- future
    - 公安
    - 天气
    - 地图

## 人员



