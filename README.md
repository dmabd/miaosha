# miaosha
慕课网的秒杀项目
Springboot+Mysql+Mybatis+Druid+Redis+RabbitMQ+html+css+Jquery+bootstrap
实现了登录，展示商品列表，商品详情，订单详情页
重在于对项目的优化，而不是增删改查
做了如下优化
1.明文密码做了两次MD5处理
2.JSR303参数校验+全局异常处理器
3.分布式Session
4.页面缓存+url缓存+对象缓存
5.页面静态化，前后端分离
6.静态资源优化
7.cdn优化
8.redis预减库存减少数据库访问
9. 内存标记减少redis访问
10. RabbitMQ队列缓存，异步下单，增强用户体验
11.秒杀地址隐藏
12. 数学验证码
13. 接口防刷
