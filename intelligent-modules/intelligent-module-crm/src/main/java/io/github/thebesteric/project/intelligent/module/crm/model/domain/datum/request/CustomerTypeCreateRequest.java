package io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request;

import cn.hutool.extra.pinyin.PinyinUtil;
import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;

/**
 * 客户类型-新增
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-23 17:59:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerTypeCreateRequest extends BaseRequest<CustomerType> {
    @Serial
    private static final long serialVersionUID = -907720126668791188L;

    @Schema(description = "客户类型名称")
    @NotBlank(message = "客户类型名称不能为空")
    @Length(min = 1, max = 64, message = "名称长度范围在 {min} 和 {max} 之间")
    private String name;

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "是否默认")
    @NotNull(message = "是否默认不能为空")
    private Boolean isDefault = false;

    @Schema(description = "是否启用，0: 禁用，1: 启用")
    @Range(min = 0, max = 1, message = "状态值范围在 {min} 和 {max} 之间")
    private Integer state = 1;

    public void setKeyword(String keyword) {
        // 如果关键字为空，则自动生成拼音码
        if (StringUtils.isBlank(keyword)) {
            keyword = PinyinUtil.getFirstLetter(this.name, "");
        }
        this.keyword = keyword.toUpperCase();
    }
}
