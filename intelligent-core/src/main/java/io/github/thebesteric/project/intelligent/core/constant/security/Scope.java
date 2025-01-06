package io.github.thebesteric.project.intelligent.core.constant.security;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 授权范围
 *
 * @author wangweijun
 * @since 2024/6/17 18:45
 */
@Getter
public enum Scope {
    PROFILE("profile"), OPENID("openid");

    private final String name;

    Scope(String name) {
        this.name = name;
    }

    public static String getScopesStr(Scope... scopes) {
        scopes = scopes == null ? values() : scopes;
        return Arrays.stream(scopes).map(Scope::getName).collect(Collectors.joining(" "));
    }

    public static Scope of(String name) {
        return Arrays.stream(Scope.values()).filter(scope -> scope.getName().equals(name)).findFirst().orElse(null);
    }

    public static List<Scope> to(String scope) {
        return Arrays.stream(scope.split(" ")).map(Scope::of).toList();
    }
}