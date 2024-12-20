# Intelligent
## 架构设计
![img.png](asserts/images/architecture.png)

## 组件版本
- **[Require] Spring Boot 3.3.6**
- **[Require] Spring Cloud 2023.0.3**
- **[Require] Spring Cloud Alibaba 2023.0.1.3**
- **[Require] Spring Security 6.3.5**
- **[Require] Spring OAuth Server 1.3.3**
- **[Require] Agile Framework 1.0.17.3**
- **[Require] JDK-17**
> 注意：升级主版本的情况下，需要做全面测试，否则将会有一些未知问题

## 快速入门
参考：service-quickstart-demo 模块

## 服务依赖关系
```text
intelligent                         根 
├── intelligent-core                核心包
├── intelligent-core-api            核心 API
├── intelligent-gateway-server      网关
├── intelligent-oauth-server        认证服务器
├── intelligent-modules-common      模块公共包
└── intelligent-modules             模块
    ├── intelligent-module-crm      微服务-客户服务
    └── intelligent-module-product  微服务-商品服务
```



## 数据库表设计约定
### 表名
- 普通表：使用`t_`开头，加具体业务名称，如：`t_d_a_agreement`表示，`t`表示表的前缀，`d_a`表示分账业务，`agreement`表示具体业务
- 关联表：使用`r_`开头，表示 relation 关系，后面加具体关联的表，如：`r_user_role`，`r`表示这是一张关联表，`user_role`表示这是用户和角色表的关联关系

### 继承建议
1. 原则上所有表都要继承 BaseEntity 或 BaseBusinessEntity，以保证业务的安全和扩展性
2. 业务表建议继承 BaseBusinessEntity
3. 非需要留存历史记录的关联关系表，可以不继承基类

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

### 业务异常约定
所有业务异常均应继承`BusinessException`，同时使用`BusinessCode`作为异常代码，如：
```java
@Getter
public class ParameterNotValidException extends BusinessException {
    @Serial
    private static final long serialVersionUID = -5919323526825575010L;

    private final BusinessCode businessCode;

    public ParameterNotValidException(String message) {
        super(message);
        this.businessCode = BusinessCode.VALID_PARAMETER_ERROR;
    }
}
```
>`BusinessCode`定义了所有业务异常的 code，如需要扩展新的 code，可以扩充此类

### 分页相关约定
- 入参：可以继承`PagingRequest`做为入参的基类，该类提供了`size`和`current`参数
- 出参：可以继承或直接使用`PagingResponse`做为出参，该类提供了分页相关参数，以及扩展字段`extension`字段的扩展

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

### 多数据源的使用方式
采用了`dynamic-datasource`的开源框架，详见：[dynamic-datasource](https://www.kancloud.cn/tracy5546/dynamic-datasource/2264611)
```yaml
spring:
  datasource:
    dynamic:
      primary: oauth
      strict: false
      grace-destroy: true
      datasource:
        oauth:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://xxx:3306/xxx?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
          username: xxx
          password: xxx
        pms:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://yyy:3306/yyy?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
          username: yyy
          password: yyy
```
> @DS 注解可以注解在方法上或类上，同时存在就近原则，方法上注解优先于类上注解；默认使用 primary 指定的数据源
#### 手动切换数据源
```
DynamicDataSourceContextHolder.push("master"); // 切换到 master
// 执行 SQL
DynamicDataSourceContextHolder.clear(); // 清空切换
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

### AgileLogger 日志链路使用
在 Spring Boot 启动类上加上`@EnableAgile`注解
```java
@SpringBootApplication
@ComponentScan(ApplicationConstants.COMPONENTS_PACKAGE_PATH)
@ConfigurationPropertiesScan(ApplicationConstants.COMPONENTS_PACKAGE_PATH)
@EnableAgile
public class QuickstartServiceDemoApplication {
    public static void main(String[] args) {
        // 注册服务名，用于 nacos 的服务发现
        System.setProperty(ApplicationConstants.Application.APPLICATION_NAME_KEY, ApplicationConstants.Application.Service.QuickStart.APPLICATION_NAME);
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

    @GetMapping("/no-auth")
    public R<String> noAuth() {
        String foo = testService.foo();
        return R.success(foo, "no-auth path");
    }

}
```
> 具体使用详见：https://github.com/thebesteric/agile

### Agile 接口幂等使用
使用`@Idempotent`注解 Controller 方法，同时可以设置幂等校验时间，在参数或请求提上使用`@IdempotentKey`作为唯一标识
```java
@Idempotent(timeout = 200, timeUnit = TimeUnit.MILLISECONDS)
@PostMapping("/signAndSetting")
@Operation(summary = "签署电子协议、保存集团默认分账规则配置")
public R<Void> sign(@Validated @RequestBody StoreTypeDefaultPeriodSettingRequest request) {
    agreementService.agreementSign(request);
    return R.success();
}

@Data
@EqualsAndHashCode(callSuper = true)
public class StoreTypeDefaultPeriodSettingRequest extends BaseRequest {

  @Schema(description = "分账周期：1-日，2-周，3-月")
  @NotNull(message = "请设置分账周期")
  private Integer period;

  @Schema(description = "支付手续费：1-门店承担，2-集团承担，3-各自承担")
  @NotNull(message = "请设置支付手续费")
  private Integer feeType;

  @Schema(description = "分账种类对应分账规则列表")
  private List<StoreTypeDefaultSettingRequest> storeTypeList;

  @Schema(description = "付呗商户号")
//    @NotNull(message = "付呗商户号不可为空")
  private String merchantId;

  @IdempotentKey
  @Schema(description = "连锁ID")
  @NotEmpty(message = "连锁ID不可为空")
  private String allianceId;

  /** 分账组ID */
  private String groupId;

}
```

## 常见问题
### 如何将 URI 设置为无需认证
继承`AbstractSecurityRequestMatcherIgnoredUriProcessor`抽象类，并重写`ignoredUris`方法，并定义成 Spring Bean  
参考：service-quickstart-demo 的 `com.xuanzhu.group.service.quickstart.demo.config.CustomSecurityRequestMatcherIgnoredUriProcessor` 配置
> 注意：URI 的设置不需要加 context-path 前缀，同时访问的时候不能走网关，直接访问资源服务器的地址

### 关于 @DS 多数据源生效问题
@DS 注解用于切换数据源，通常注解在 ServiceImpl 的直接实现类或 Mapper 上

### 关于分表事务问题
如果相关表使用到分表，同时还需要操作事务，那么需要使用`@DSTransactional`注解

### 关于分表更新数据的问题
如果需要更新分表的数据表，请使用`update + lambda`的方式更新表，`updateById`和`updateBatchById`会失败
```java
storeService.update(recordStore, new LambdaUpdateWrapper<BalanceRecordStore>()
        .eq(BalanceRecordStore::getId, recordStore.getId())
        .eq(BalanceRecordStore::getAllianceId, recordStore.getAllianceId())
        .eq(BalanceRecordStore::getStoreId, recordStore.getStoreId())
```

# 数据校验
DataValidator

# 基类
## IBaseService

## IBaseMapper

# 相关注解
## 日志 @AgileLogger

## 限流 @RateLimiter

## 幂等 @Idempotent
