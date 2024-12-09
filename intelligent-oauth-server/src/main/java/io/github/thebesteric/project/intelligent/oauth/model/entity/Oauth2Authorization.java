package io.github.thebesteric.project.intelligent.oauth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityClass;
import io.github.thebesteric.framework.agile.plugins.database.core.annotation.EntityColumn;
import lombok.Data;

import java.util.Date;

/**
 * 授权信息
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-30 00:34:40
 */
@Data
@TableName("oauth2_authorization")
@EntityClass(comment = "授权信息")
public class Oauth2Authorization {

    @TableId(type = IdType.ASSIGN_UUID)
    @EntityColumn(length = 100, primary = true, comment = "主键")
    private String id;

    @EntityColumn(length = 100, nullable = false, comment = "客户端 ID")
    private String registeredClientId;

    @EntityColumn(length = 200, nullable = false, comment = "客户端 ID 名称")
    private String principalName;

    @EntityColumn(length = 100, nullable = false, comment = "认证授权类型")
    private String authorizationGrantType;

    @EntityColumn(length = 1000, comment = "认证授权范围")
    private String authorizedScopes;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "认证授权属性")
    private String attributes;

    @EntityColumn(length = 500, comment = "状态")
    private String state;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "认证 CODE 值")
    private String authorizationCodeValue;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "认证 CODE 创建时间")
    private Date authorizationCodeIssuedAt;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "认证 CODE 过期时间")
    private String authorizationCodeExpiresAt;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "认证 CODE 元数据")
    private String authorizationCodeMetadata;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "访问 TOKEN")
    private String accessTokenValue;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "访问 TOKEN 创建时间")
    private String accessTokenIssuedAt;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "访问 TOKEN 过期时间")
    private String accessTokenExpiresAt;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "访问 TOKEN 元数据")
    private String accessTokenMetadata;

    @EntityColumn(length = 100, comment = "访问 TOKEN 类型")
    private String accessTokenType;

    @EntityColumn(length = 100, comment = "访问 TOKEN 范围")
    private String accessTokenScopes;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "认证 ID TOKEN")
    private String oidcIdTokenValue;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "认证 ID TOKEN 创建时间")
    private Date oidcIdTokenIssuedAt;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "认证 ID TOKEN 过期时间")
    private Date oidcIdTokenExpiresAt;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "认证 ID TOKEN 元数据")
    private String oidcIdTokenMetadata;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "刷新 TOKEN")
    private String refreshTokenValue;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "刷新 TOKEN 创建时间")
    private Date refreshTokenIssuedAt;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "刷新 TOKEN 过期时间")
    private Date refreshTokenExpiresAt;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "刷新 TOKEN 元数据")
    private String refreshTokenMetadata;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "用户 CODE")
    private String userCodeValue;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "用户 CODE 创建时间")
    private Date userCodeIssuedAt;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "用户 CODE 过期时间")
    private Date userCodeExpiresAt;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "用户 CODE 元数据")
    private String userCodeMetadata;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "设备 CODE")
    private String deviceCodeValue;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "设备 CODE 创建时间")
    private Date deviceCodeIssuedAt;

    @EntityColumn(type = EntityColumn.Type.TIMESTAMP, comment = "设备 CODE 创建时间")
    private Date deviceCodeExpiresAt;

    @EntityColumn(type = EntityColumn.Type.BLOB, comment = "设备 CODE 元数据")
    private String deviceCodeMetadata;
}
