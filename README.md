# Spring Boot Starter Swagger

[![Build Status](https://www.travis-ci.org/taccisum/spring-boot-starter-swagger.svg?branch=master)](https://www.travis-ci.org/taccisum/spring-boot-starter-swagger)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.taccisum/spring-boot-starter-swagger.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.taccisum%22%20AND%20a:%22spring-boot-starter-swagger%22)

此项目是对应[springfox swagger2](https://github.com/springfox/springfox/tree/master/springfox-swagger2)的spring boot starter。其

- 完美适配springfox-swagger2，几乎支持通过yaml文件进行所有配置
- 提供拦截器，允许用户自行扩展自定义配置
- 同时支持spring boot1和spring boot2

## Release Note

### v1.0.1

- 修复部分配置为null时会报NPE的bug


## Getting Started

引入依赖

**pom.xml**
```xml
<!-- spring boot1用户 -->
<dependency>
    <groupId>com.github.taccisum</groupId>
    <artifactId>swagger-spring-boot1-starter</artifactId>
    <version>{lastest.version}</version>
</dependency>

<!-- spring boot2用户 -->
<dependency>
    <groupId>com.github.taccisum</groupId>
    <artifactId>swagger-spring-boot2-starter</artifactId>
    <version>{lastest.version}</version>
</dependency>
```

通过yaml文件进行配置，更多配置详情可以参考[全配置一览](#全配置一览)

**application.yml**
```yaml
swagger:
  base-package: com.github.taccisum.controller
```

## 通过拦截器监听Docket实例的构建事件

```java
@Component
public class LogDocketBuilderInterceptor implements DocketBuilderInterceptor {
    public void before(BeforeInitializeDocketEvent event) {
        System.out.println("before docket initialize");
    }

    public void after(AfterInitializeDocketEvent event) {
        System.out.println("after docket initialize");
    }

    public int getOrder() {
        return 0;
    }
}
```

## 展示更多的API信息

```yaml
swagger:
  info:
    description:
      show-hostname: true
      show-profiles: true
      show-start-date: true
```

效果如下

![api info](/assets/pics/api_info.png)

## 只为符合路径规则的API生成文档

```yaml
swagger:
# 要包括进去的路径，ANT风格
  include-paths:
    - /**
# 要排除掉的路径，ANT风格
  exclude-paths:
    - /**/dev/**
```

## 添加认证ApiKey

```properties
swagger.auth.api-key.{key}.keyName=Authorization
swagger.auth.api-key.{key}.passAs=header
swagger.auth.api-key.{key}.includePaths[0]=/**
swagger.auth.api-key.{key}.excludePaths[0]=/**/dev/**
swagger.auth.api-key.{key}.excludePaths[1]=/foo/test
```

ApiKey可以配置多个，只要上面的{key}部分不同即可。

## 全配置一览

```yaml
swagger:
  enabled: true # 是否开启swagger
  base-package: com.github.taccisum.swagger.starter.sample.controller # controller扫描路径
  path-mapping: / # servlet路径
  enable-url-templating: false # 使用rfc6570 url模板规范
  generic-model-substitutes:  # 使用泛型中的T代替model的类型列表
    - com.github.taccisum.GenericResponse
  info:
    contact: # 联系人信息
      name: taccisum
      url: https://taccisum.github.io
      email: 3277800095@qq.com
    description:
#      html: # 描述html，会覆盖其它配置
      show-hostname: true # 展示当前机器hostname
      show-profiles: true # 展示当前应用的active profiles
      show-start-date: true # 展示当前应用启动时间
    license: MIT  # 协议
    license-url: https://mit-license.org/ # 协议url
    title: Sample docs  # 文档标题
    version: 1.0  # 文档版本号
    terms-of-service-url: https://www.baidu.com # 服务条款url
  include-paths:  # 包括的api路径
    - /**
  exclude-paths:  # 排除的api路径
    - /**/dev/**
  ui:
    deep-linking: false # 
    display-operation-id: true  # 展示operation id
    default-models-expand-depth: 2  # 点击models面板时的默认展开深度
    default-model-expand-depth: 1 # model默认展开深度
    default-model-rendering: model  # model默认渲染类型
    display-request-duration: true  # 展示请求耗时
    doc-expansion: none # 文档默认展开方式
    filter: true  # 是否开启过滤功能
    max-displayed-tags: 10  # 最大展示的tag数量
    operations-sorter: alpha  # operations的排序方式
    show-extensions: false  # 展示扩展信息
    tags-sorter: alpha  # tags的排序方式
#    validator-url:   # 指定swagger规范校验url，null表示不校验
    supported-submit-methods: # 支持在文档上[try it out]功能的方法列表
      - get
      - post
```

部分配置（如Map类型）需要通过.properties文件编写才有提示，建议这部分配置可以单独编写。

```properties
# 配置ApiKey认证方式
# 参数名称
swagger.auth.api-key.test.keyName=Authorization
# 参数传递方式
swagger.auth.api-key.test.passAs=header
# ApiKey关联的路径配置，ANT风格
swagger.auth.api-key.test.includePaths[0]=/**
swagger.auth.api-key.test.excludePaths[0]=/**/dev/**
swagger.auth.api-key.test.excludePaths[1]=/foo/test

# 配置全局参数
# model引用类型
swagger.global-parameters.Platform.modelRef=string
# 参数类型
swagger.global-parameters.Platform.parameterType=header
# 参数描述
swagger.global-parameters.Platform.description=source platform of request
# 默认值
swagger.global-parameters.Platform.defaultValue=Bearer
# 是否必填
swagger.global-parameters.Platform.required=true
# 是否隐藏
swagger.global-parameters.Platform.hidden=false
```


