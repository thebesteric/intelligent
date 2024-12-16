package io.github.thebesteric.project.intelligent.module.crm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.module.crm.mapper.CustomerRegionMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.CustomerRegion;
import io.github.thebesteric.project.intelligent.module.crm.service.CustomerRegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CustomerRegionServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:50:16
 */
@Service
@RequiredArgsConstructor
public class CustomerRegionServiceImpl extends ServiceImpl<CustomerRegionMapper, CustomerRegion> implements CustomerRegionService {
}
