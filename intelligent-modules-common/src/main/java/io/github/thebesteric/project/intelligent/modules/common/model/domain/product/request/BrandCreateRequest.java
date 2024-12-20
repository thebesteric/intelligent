package io.github.thebesteric.project.intelligent.modules.common.model.domain.product.request;

import cn.hutool.extra.pinyin.PinyinUtil;
import io.github.thebesteric.project.intelligent.core.base.BaseRequest;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.Brand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * BrandCreateRequest
 *
 * @author wangweijun
 * @version v1.0
 * @since 2024-12-20 11:48:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BrandCreateRequest extends BaseRequest<Brand> {
    @Serial
    private static final long serialVersionUID = -8964842869295821499L;

    @Schema(description = "全名")
    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "别名")
    private List<String> alias = new ArrayList<>();

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "官网")
    private String website;

    @Schema(description = "排序")
    private Integer seq = 0;

    @Schema(description = "图标 ID")
    private Long iconImageId;

    @Schema(description = "状态：0-禁用，1-启用")
    @Range(min = 0, max = 1, message = "状态只能是 {min} 和 {max}")
    protected Integer state = 1;

    public void setKeyword(String keyword) {
        // 如果关键字为空，则自动生成拼音码
        if (StringUtils.isBlank(keyword)) {
            keyword = PinyinUtil.getPinyin(this.name, "");
        }
        this.keyword = keyword.toUpperCase();
    }
}
