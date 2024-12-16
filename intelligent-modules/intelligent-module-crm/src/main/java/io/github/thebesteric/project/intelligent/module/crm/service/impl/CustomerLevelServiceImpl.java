package io.github.thebesteric.project.intelligent.module.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.module.crm.mapper.CustomerLevelMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.CustomerLevel;
import io.github.thebesteric.project.intelligent.module.crm.service.CustomerLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CustomerLevelServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:48:47
 */
@Service
@RequiredArgsConstructor
public class CustomerLevelServiceImpl extends ServiceImpl<CustomerLevelMapper, CustomerLevel> implements CustomerLevelService {
}
