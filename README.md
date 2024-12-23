# Intelligent
## 架构设计
![architecture.png](asserts/images/architecture.png)

## 组件版本
- **[Require] Spring Boot 3.3.6**
- **[Require] Spring Cloud 2023.0.3**
- **[Require] Spring Cloud Alibaba 2023.0.1.3**
- **[Require] Spring Security 6.3.5**
- **[Require] Spring OAuth Server 1.3.3**
- **[Require] Agile Framework 1.0.17.3**
- **[Require] JDK-17**
> 注意：升级主版本的情况下，需要做全面测试，否则将会有一些未知问题

## 服务依赖关系
```text
intelligent                         根 
├── asserts                         资源文件夹
├── intelligent-core                核心包
├── intelligent-core-api            核心 API
├── intelligent-gateway-server      网关
├── intelligent-oauth-server        认证服务器
├── intelligent-modules-common      模块公共包
└── intelligent-modules             模块
    ├── intelligent-module-crm      微服务-客户服务
    └── intelligent-module-product  微服务-商品服务
    └── ...                         微服务-...
```

## Quick Start
> 确保 MySQL、Nacos 服务都正常运行
### 启动步骤
1. **（必须）** 启动`intelligent-oauth-server`认证服务
2. **（非必须）** 启动`intelligent-gateway-server`网管服务（非必须）
3. **（非必须）** 启动`intelligent-core-api`核心 API 服务
4. **（必须）** 启动`intelligent-modules`下对应的微服务模块服务
> 核心 API 服务`intelligent-core-api`会提前创建项目所依赖的基础数据：如权限、用户等，如果已经创建过，可以直接跳过启动
### 访问规则
服务启动完成后，需要先获取认证所需要的`token`进行接口访问
- grant_type: 固定值，表示授权模式为: 账号密码授权
- username: 账号，详见：`intelligent-core-api.t_core_user` 表
- password: 密码，采用 BCRYPT 加密，详见：`intelligent-core-api.t_core_user` 表
- scope: 授权范围，可选值：profile、openid，多个之间用空格分割
#### Token 请求示例
> 请求头添加：`Authorization`，值为：`Basic xxx`，其中 xxx 为认证信息  
> Basic 值，由`intelligent-oauth-server.oauth2_registered_client` 表中的`client_id`和`client_secret`提供，其中`client_secret`为`BCrypt`加密  
> 加密规则：使用`Base64`对`client_id:client_secret`进行加密，其中`client_secret`为明文
```curl
curl --location --request POST 'http://localhost:6666/oauth2/token?grant_type=authorization_password&username=admin&password=123456&scope=openid%20profile' \
--header 'Authorization: Basic dGVzdC1jbGllbnQtaWQ6c2VjcmV0'
```
#### Token 响应结果
```json
{
    "access_token": "xxx",
    "refresh_token": "xxx",
    "scope": "openid profile",
    "id_token": "xxx",
    "token_type": "Bearer",
    "expires_in": 28799
}
```
#### API 访问示例
> 除不认证的接口，否则必须携带 token 访问，详见[认证相关](#认证相关)  
> 请求头添加：`Authorization`，值为：`Bearer xxx`，其中 xxx 为前面获取到的 token
```curl
curl --location 'http://127.0.0.1:8888/crm/api/test/index' \
--header 'Authorization: Bearer xxx'
```

## 数据库表设计约定
### 表名
- 普通表：使用`t_`开头，加具体业务名称，如：`t_crm_customer_discount_info`表示，`t`表示表的前缀，`crm`表示客户服务模块，`customer_discount_info`表示具体业务
- 关联表：使用`t_xxx_r_`开头，表示 relation 关系，后面加具体关联的表，如：`t_core_r_user_role`，`core`表示客户服务模块，`r`表示这是一张关联表，`user_role`表示这是用户和角色表的关联关系

### 继承建议
1. 原则上所有表都要继承`BaseEntity`或`BaseBizEntity`，以保证业务的安全和扩展性
2. 业务表建议继承`BaseBizEntity`，和租户相关的表建议继承`BaseTenantBizEntity`
3. 字典表，可以不继承基类

### 字段约定
- **id**：主键：使用雪花算法，数据库对应类型 bigint
- **created_at**：创建日期：无需手动维护，对应数据库类型 datetime
- **updated_at**：更新日期：无需手动维护，对应数据库类型 datetime
- **created_by**：创建人：无需手动维护，对应数据库类型 varchar(32)
- **updated_by**：更新人：无需手动维护，对应数据库类型 varchar(32)
- **desc**：描述：对应数据库类型 varchar(255)
- **state**：状态：0-禁用，1-启用（可根据业务扩充）
- **version**：乐观锁
- **deleted**：逻辑删除：无需手动维护，0-正常，1-已删除
- **extra**：扩展字段：对应数据库类型 json

## 业务异常约定
所有业务异常均应继承`BizException`，同时使用`BizCode`作为异常代码，如：
```java
public class DataNotFoundException extends BizException {
    @Serial
    private static final long serialVersionUID = 6225586610980929742L;
    
    public DataNotFoundException() {
      this("数据不存在");
    }
    
    public DataNotFoundException(String message) {
      super(BizCode.DATA_NOT_FOUND, message);
    }
}
```
>`BizCode`定义了所有业务异常的 code，如需要扩展新的 code，可以扩充此类

## 分页相关约定
- 入参：可以继承`PagingRequest`做为入参的基类，该类提供了`size`和`current`参数
- 出参：可以继承或直接使用`PagingResponse`做为出参，该类提供了分页相关参数，以及扩展字段`extension`字段的扩展
```java
@PostMapping("/page")
@Operation(summary = "等级列表")
public R<PagingResponse<CustomerLevelResponse>> page(@Validated @RequestBody CustomerLevelSearchRequest searchRequest) {
    return R.success(levelService.page(searchRequest));
}

@Override
public PagingResponse<CustomerLevelResponse> page(CustomerLevelSearchRequest searchRequest) {
    IPage<CustomerLevel> page = this.lambdaQuery()
            .like(StringUtils.isNotBlank(searchRequest.getName()), CustomerLevel::getName, searchRequest.getName())
            .page(searchRequest.getPage(CustomerLevel.class));
    List<CustomerLevelResponse> records = page.getRecords().stream().map(r -> {
      return (CustomerLevelResponse) new CustomerLevelResponse().transform(r);
    }).toList();
    return PagingResponse.of(page.getCurrent(), page.getSize(), page.getTotal(), records);
}
```

## 分支管理约定
### 基本原则
- master 为保护分支，不直接在 master 上进行代码修改和提交。
- 开发日常需求或者项目时，owner 从 master 分支上 checkout 一个 feature 分支进行开发或者 bugfix 分支进行 bug 修复，
    - 如果是新功能：开发人员从 owner 切的分支中再 checkout 一个后缀为自己姓名的分支，命名为：`feature_20240331_member_center_yourname`，当开发完毕后合并到：`feature_20240331_member_center`分支中并提交到 stg 分支，功能测试完毕并且项目发布上线后，将 feature 分支合并到主干 master，并且打 tag 发布，最后删除开发分支。
    - 如果是缺陷：开发人员从 master 分支 checkout 一个 bugfix 分支，并命名为：`bugfix_20240331_member_center_yourname`，当缺陷修复后，合并到 stg 分支进行测试。功能测试完毕后，将 bugfix 分支合并到主干 master，并且打 tag 发布，最后删除开发分支。

### 分支/提交类型
- **feat**：新功能
- **bugfix**：缺陷修复
- **fix**：普通修复，非 Bug 类修复
- **refactor**：重构，即不是新增功能，也不是修改缺陷的代码变动
- **perf**：优化相关，比如提升性能、体验等
- **test**：增加或修改测试用例
- **chore**：构建过程或辅助工具的变动，非系统架构类型
- **docs**：增加文档或相关注释信息
- **revert**：回退版本

### Branch 命名规范
- 分支版本命名规则：分支类型_分支发布日期_分支功能。比如：`feature_20240331_member_enter`
    - 日期使用 yyyy-MM-dd 进行命名，不足 2 位补 0
    - 分支功能命名使用 snake case 命名法，即下划线命名。

### Comments 提交规范
- 提交格式：`类型(范围) + 半角冒号 + 空格 + 主题/n正文`，其中`(范围)`可选，当是 bugfix 的时候，通常 Scope 为 bug 的编号，如：
```text
feat: 新增会员查询功能
支持日期范围搜索、按用户名模糊匹配

bugfix(10284,10285): 新增会员查询功能
支持日期范围搜索、按用户名模糊匹配
```

### Tag 命名规范
- Tag 包括 3 位版本，前缀使用v。比如：v1.2.31。
    - 架构更新或变更使用第 1 位版本号、新功能开发使用第 2 位版本号、缺陷修复使用第 3 位版本号
    - 核心基础库或者中间价变更使用灰度版本号，在版本后面加上后缀，用中划线分隔。alpha 或者 belta 后面加上次数，即第几次alpha，如：v2.0.0-alpha.1、v2.0.0-belta.1
    - 版本正式发布前需要生成 changelog 文档，然后再发布上线

## 组件开启方式
### XXL-JOB 开启方式
在 yaml 文件中配置，其中`xxl-job.enable`表示是否开启，默认为 **false**，`xxl-job.executor.address`的端口号如果同一台主机中的多个服务均使用了 xxl-job，那么端口号必须保障全局唯一
```yaml
application:
  components:
    xxl-job:
      enable: true
      assess-token: default_token
      admin:
        address: http://127.0.0.1:8215/xxl-job-admin
      executor:
        app-name: ${application.name}
        log-path: ./logs/xxl-job
        address: http://192.168.64.1:10999
```
### Swagger 开启方式
在 application.yml 中配置如下
```yaml
application:
  components:
    swagger:
      enable: true
      production: false
```
> 注意：生产环境建议把`application.components.swagger.production`设置为`true`

## 认证相关
默认情况下，所有方法均需要认证，如需要跳过认证，需要按跳过认证的方式进行配置
### 跳过认证
#### 方式一，使用注解
类或方法上添加 `@SkipAuth` 注解，添加在类上，表示所有的方法都会跳过认证，@SkipAuth 只在 `@Contorller` 和 `@RestController` 类生效

### 方式二，使用配置类
```java
@Component
public class CustomSecurityRequestMatcherIgnoredUriProcessor extends AbstractSecurityRequestMatcherIgnoredUriProcessor {
    @Override
    public String[] ignoredUris() {
        // 设置不需要认证的 URI 地址
        return List.of("/api/authorization/**", "/api/open/**").toArray(new String[0]);
    }
}
```

## 多数据源的使用方式
采用了`dynamic-datasource`的开源框架，详见：[dynamic-datasource](https://www.kancloud.cn/tracy5546/dynamic-datasource/2264611)
```yaml
spring:
  datasource:
    dynamic:
      primary: master-db
      strict: false
      grace-destroy: true
      datasource:
        master-db:
          type: com.zaxxer.hikari.HikariDataSource
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://localhost:3306/master?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
          username: username
          password: password
        other-db:
          type: com.zaxxer.hikari.HikariDataSource
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://localhost:3306/other?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
          username: username
          password: password
```
> @DS 注解可以注解在方法上或类上，同时存在就近原则，方法上注解优先于类上注解；默认使用 primary 指定的数据源
### 手动切换数据源
```java
// 切换到 master
DynamicDataSourceContextHolder.push("master");
// 执行业务逻辑...
// 清空切换
DynamicDataSourceContextHolder.clear();
```

## Agile Framework 使用
> 具体使用详见：https://github.com/thebesteric/agile
### Agile 数据库正向工程
```xml
<dependency>
    <groupId>io.github.thebesteric.framework.agile.plugins</groupId>
    <artifactId>database-plugin</artifactId>
    <version>${latest.version}</version>
</dependency>
```
相关配置项
```yaml
sourceflag:
  agile:
    database:
      enable: true
      show-sql: true
      ddl-auto: update
      format-sql: true
      delete-column: true
```
```java
@TableName("foo")
@EntityClass(comment = "测试表")
public class Foo extends BaseEntity {

    @EntityColumn(length = 32, unique = true, nullable = false, forUpdate = "hello", defaultExpression = "'foo'")
    private String name;

    @EntityColumn(name = "t_phone", unique = true, nullable = false, defaultExpression = "18", comment = "电话", unsigned = true)
    private Integer age;

    @EntityColumn(unique = true, defaultExpression = "'test'")
    private String address;

    @EntityColumn(length = 10, precision = 3, unique = true)
    private BigDecimal amount;

    @EntityColumn(nullable = false, type = EntityColumn.Type.SMALL_INT, unsigned = true)
    private Season season;

    @EntityColumn(length = 10, precision = 2)
    private Float state;

    @EntityColumn(type = EntityColumn.Type.DATETIME, defaultExpression = "now()")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("t_test")
    @EntityColumn(length = 64, nullable = false)
    private String test;
}
```
### Agile 日志链路
在 Spring Boot 启动类上加上`@EnableAgile`注解
```java
@SpringBootApplication
@MapperScan(ApplicationConstants.MAPPER_PACKAGE_PATH)
@ComponentScan(ApplicationConstants.COMPONENT_PACKAGE_PATH)
@ConfigurationPropertiesScan(ApplicationConstants.COMPONENT_PACKAGE_PATH)
@EnableAgile
public class QuickstartServiceDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuickstartServiceDemoApplication.class, args);
    }
}
```
在类上或方法上使用`@AgileLogger`注解，注解在类上表示该类所有方法均会打印日志，如果注解在方法上则表示该方法会打印日志
```java
@RestController
@RequestMapping("/test")
@AgileLogger
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/foo")
    public R<String> foo() {
        String result = testService.foo();
        return R.success(result);
    }
    
    // @IgnoreMethod 表示忽略日志
    @IgnoreMethod
    @GetMapping("/bar")
    public R<Void> bar() {
        return R.success();
    }

}
```
### Agile 接口幂等
使用`@Idempotent`注解 Controller 方法，同时可以设置幂等校验时间，在参数或请求提上使用`@IdempotentKey`作为唯一标识
```java
@Idempotent(timeout = 200, timeUnit = TimeUnit.MILLISECONDS)
@PostMapping("/sign")
@Operation(summary = "注册")
public R<Void> sign(@Validated @RequestBody SignRequest request) {
    agreementService.agreementSign(request);
    return R.success();
}

@Data
public class SignRequest {
  @IdempotentKey
  @Schema(description = "连锁ID")
  @NotEmpty(message = "连锁ID不可为空")
  private String allianceId;

  @IdempotentKey
  @Schema(description = "账号")
  @NotNull(message = "用户名不能为空")
  private String username;

  @Schema(description = "密码")
  @NotNull(message = "密码不能为空")
  private String password;
}
```
### Agile 接口限流
使用`@RateLimiter`注解 Controller 方法，同时可以设置超时时间和访问次数，即限制一段时间内访问次数
```java
@PostMapping("/limit")
@RateLimiter(timeout = 10, count = 10)
public R<Id2Vo> limit(@RequestBody Id2Vo id2Vo) {
  return R.success(id2Vo);
}
```