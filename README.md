## 主键生成策略
> ### 系统根据各表主键关系建立字段关联，主键均在 *Vo实体 构造函数* 中进行统一生成传递

## 解析标准说明
> ### 页面展示根据 *数据库注释* 配置进行解析，格式如下
> ### \<\!\> 字段名 *CN* #1#2#3#4#5#6
> > #### 若注释以 *\<\!\>* 开头, 表示该字段隐藏不显示，则忽略以下注释配置
> > #### 若字段名以 *CN* 结尾,则表明该字段为中文输入，长度校验时，字段校验规则为: *数据库字段长度/3\*2* ; 其他类型长度与数据库字段长度一致
> > #### 注释含义如下:
> > > #### 1. 页面是否必填
> > > #### 2. 页面是否可编辑
> > > #### 3. 页面是否显示当字段无 *<!>* 以该字段判断页面是否显示
> > > #### 4. 字典名称 : 以 *数据库字典表* 名命名
> > > #### 5. 页面字段展示类型
> > > > ###### DATE : 日期控件
> > > > ###### DATETIME : 日期控件（时分秒）
> > > > ###### COBBOX : 字典
> > > > ###### TEXTBOX : 普通输入框
> > > > ###### TEXTAREA : 多换文本框

> > > #### 6. 排列顺序 : 页面按从小到大属性排列 

## [AutoCreator.java](src/main/java/com/founder/main/AutoCreator.java)

### 生成逻辑可根据实际情况调整

* createXW() : 生成行为逻辑
* createST() : 生成实体逻辑
* createAspect() : 关联行为和实体关系

> * 基于包名管理业务.逻辑相同的一组业务位于同一包名之下, 由[xxxIntercept](src/main/resources/ftl/aspect.ftl)管理
> * 具体的插入关联逻辑后期根据业务需求在 [xxxIntercept](src/main/resources/ftl/aspect.ftl) 中单独管理
> * Service默认实现 [CRUDInterface.java](src/main/resources/ftl/service/CRUDInterface.java)接口, 具有事务性的逻辑由 [xxxIntercept](src/main/resources/ftl/aspect.ftl) 中 [CRUDService](src/main/resources/ftl/base/service/CRUDService.java) 统一执行插入

### 如:

```
- !!com.founder.yaml.anjian.model.RelationConfig
  stClasses: [
  com.founder.anjian.autoEntry.st.service.TbStSawpService,
  com.founder.anjian.autoEntry.st.service.TbStWpService
  ]
  xwPackage: com.founder.anjian.autoEntry.xw.dsqz
- !!com.founder.yaml.anjian.model.RelationConfig
  stClasses: [
  com.founder.anjian.autoEntry.st.service.TbStSawpService,
  com.founder.anjian.autoEntry.st.service.TbStWpService
  ]
  xwPackage: com.founder.anjian.autoEntry.xw.dsdy
```

> * 表示 xwPackage 包内对象插入时同时插入 com.founder.anjian.autoEntry.st.service.TbStSawpService 和 com.founder.anjian.autoEntry.st.service.TbStWpService

> * stClasses和xwPackage统一映射规则 [BeanCopyUtil.java](src/main/resources/ftl/base/BeanCopyUtil.java)



