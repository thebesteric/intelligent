package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerTypeMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerType;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerTypeService;
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
