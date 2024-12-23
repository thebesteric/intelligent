package io.github.thebesteric.project.intelligent.modules.common.mapper.product;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.modules.common.model.entity.product.ProductBrand;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@DS(ApplicationConstants.DataSource.INTELLIGENT_MODULE_PRODUCT)
public interface ProductBrandMapper extends IBaseMapper<ProductBrand> {

    /**
     * 根据首字母获取品牌
     *
     * @param tenantId    租户 ID
     * @param firstLetter 首字母
     *
     * @return List<Brand>
     *
     * @author wangweijun
     * @since 2024/12/19 20:59
     */
    @Select("SELECT * FROM t_product_brand WHERE `tenant_id` = #{tenantId} AND LEFT(`keyword`, 1) = #{firstLetter}")
    List<ProductBrand> findByFirstLetter(String tenantId, String firstLetter);

}
