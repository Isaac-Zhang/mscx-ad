# 广告数据索引设计
设计索引的目的是为了加快索引速度，将原始数据抽象，规划出合理的字段，在内存中构建广告数据索引。
记住，并不是所有的数据都需要放在索引中。
## 正向索引
核心思想是通过一个键（通常为主键）找到一个对象，切这种关系是确定的，即唯一键对应到唯一的对象。
- 推广计划
- 推广单元
- 创意

## 倒排索引
核心思想是通过内容去确定包含关系的对象
- 创意与推广单元的关联对象
- 推广单元地域限制
- 推广单元兴趣限制
- 推广单元关键词限制

## 广告数据索引的维护
核心思想是保证检索服务中的索引是<span style="color:red">完整的</span>
- 全量索引
- 增量索引

# Question
## #1
> 索引数据的存储与操作使用的是ConcurrentHashMap、ConcurrentSkipListSet，能否使用
> HashMap、HashSet替换呢？为什么？
```html
不能.
用HashMap和HashSet在高并发的情况下会有线程安全问题，
相比之下ConcurrentHashMap和ConcurrentSkipListSet是线程安全的类.
```

## #2
> IndexDataTableUtils.java 的作用是什么？
```html
主要是在使用bean的时候，不需要繁琐的注入。
正常情况下，都需要Autowired或者Resource注入。如果使用到的bean太多的话，
也是个苦力活。所以说DataTable.java通过获取spring上下文，
里面的of方法实现了往dataTableMap中存放相应的bean，
再加上条件判断也不需要频繁的getBean(),这样方便我们使用，
只需要注入IndexDataTableUtils,调用of方法就可以取到相应的bean.
```

## #3
> 如果广告数据太多，内存中放不下，你会怎么做?
```html
广告数据如果在 JVM 中放不下了，可以考虑基于内存的存储工具，例如 Redis 等。
```
