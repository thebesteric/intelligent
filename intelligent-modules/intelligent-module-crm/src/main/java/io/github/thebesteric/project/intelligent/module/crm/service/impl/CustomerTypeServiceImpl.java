package io.github.thebesteric.project.intelligent.module.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.module.crm.mapper.CustomerTypeMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.CustomerType;
import io.github.thebesteric.project.intelligent.module.crm.service.CustomerTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CustomerTypeServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:54:41
 */
@Service
@RequiredArgsConstructor
public class CustomerTypeServiceImpl extends ServiceImpl<CustomerTypeMapper, CustomerType> implements CustomerTypeService {
}
