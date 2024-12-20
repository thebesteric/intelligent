package io.github.thebesteric.project.intelligent.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import io.github.thebesteric.project.intelligent.core.security.util.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * MyBatisPlusConfig
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-03-20 21:24:51
 */
@Configuration
public class MyBatisPlusConfig {

    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 溢出总页数后是否进行处理，默认 false 不处理，true 表示如果超出页数的范围，会返回第一页的数据
        paginationInnerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        // 乐观锁插件
        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);

        return interceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {

            /** 自动添加创建: @TableField(fill = FieldFill.INSERT) */
            @Override
            public void insertFill(MetaObject metaObject) {
                // 创建时间、更新时间处理
                Date date = new Date();
                this.setFieldValByName("createdAt", date, metaObject);
                this.setFieldValByName("updatedAt", date, metaObject);
                // 创建用户、更新用户处理
                String createdBy = getCurrentUsername();
                this.setFieldValByName("createdBy", createdBy, metaObject);
                this.setFieldValByName("updatedBy", createdBy, metaObject);
            }

            /** 自动添加修改: @TableField(fill = FieldFill.UPDATE) */
            @Override
            public void updateFill(MetaObject metaObject) {
                // 更新时间处理
                this.setFieldValByName("updatedAt", new Date(), metaObject);
                // 更新用户处理
                String updatedBy = getCurrentUsername();
                this.setFieldValByName("updatedBy", updatedBy, metaObject);
            }
        };
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityUtils.getAuthentication();
        return authentication == null ? null : authentication.getName();
    }
}
