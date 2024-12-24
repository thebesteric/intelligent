package io.github.thebesteric.project.intelligent.module.crm.controller.datum;

import io.github.thebesteric.framework.agile.plugins.logger.annotation.AgileLogger;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CustomerAuditController
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-24 17:31:08
 */
@AgileLogger
@RestController
@RequestMapping("/datum/audit")
@RequiredArgsConstructor
@Tag(name = "客户-资料-客户等级")
@PreAuthorize("@auth.hasAuthority('customer:datum:audit')")
public class CustomerAuditController {

    

}
