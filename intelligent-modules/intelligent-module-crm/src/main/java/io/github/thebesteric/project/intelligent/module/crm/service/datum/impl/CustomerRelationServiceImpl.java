package io.github.thebesteric.project.intelligent.module.crm.service.datum.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.thebesteric.project.intelligent.module.crm.mapper.datum.CustomerRelationMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerRelation;
import io.github.thebesteric.project.intelligent.module.crm.service.datum.CustomerRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CustomerRelationServiceImpl
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-12 15:52:25
 */
@Service
@RequiredArgsConstructor
public class CustomerRelationServiceImpl extends ServiceImpl<CustomerRelationMapper, CustomerRelation> implements CustomerRelationService {
}
