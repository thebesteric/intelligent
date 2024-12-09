package io.github.thebesteric.project.intelligent.oauth.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.oauth.mapper.ShadowUserMapper;
import io.github.thebesteric.project.intelligent.oauth.model.entity.ShadowUser;
import io.github.thebesteric.project.intelligent.oauth.service.ShadowUserService;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-11-25 13:47:54
 */
@Service
@DS(ApplicationConstants.DataSource.INTELLIGENT_CORE_API)
public class ShadowUserServiceImpl extends ServiceImpl<ShadowUserMapper, ShadowUser> implements ShadowUserService {
}
