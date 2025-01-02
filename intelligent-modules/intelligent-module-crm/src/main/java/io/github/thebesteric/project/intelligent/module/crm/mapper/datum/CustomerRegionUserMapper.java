package io.github.thebesteric.project.intelligent.module.crm.mapper.datum;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.mapper.handler.CommaStringToListTypeHandler;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.request.CustomerRegionUserSearchRequest;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerRegionUserResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.Customer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

public interface CustomerRegionUserMapper extends IBaseMapper<Customer> {


    /**
     * 分页查询客户区域用户
     *
     * @param tenantId 租户 ID
     * @param request  查询请求
     * @param page     分页参数
     *
     * @return IPage<CustomerRegionUserResponse>
     *
     * @author wangweijun
     * @since 2024/12/30 19:26
     */
    @Select("""
            <script>
                SELECT c.id as 'customer.id', c.name as 'customer.name', c.serial_no as 'customer.serial_no', c.address as 'customer.address',
                        c.customer_level_id as 'customer.customer_level.id', l.name as 'customer.customer_level.name',
                        c.customer_type_id as 'customer.customer_type.id', t.name as 'customer.customer_type.name',
                        c.sales_user_ids as 'customer.sales_user_ids',
                    r.id as 'region.id', r.name as 'region.name', r.code as 'region.code'
                FROM t_crm_customer c
                LEFT JOIN t_crm_customer_region r ON c.region_id = r.id
                LEFT JOIN t_crm_customer_level l ON c.customer_level_id = l.id
                LEFT JOIN t_crm_customer_type t ON c.customer_type_id = t.id
                <where>
                    <if test="request != null">
                        <if test="request.keyword != null and request.keyword != 'null' and request.keyword != ''">
                            AND c.name like CONCAT('%',#{request.keyword},'%') OR c.serial_no like CONCAT('%',#{request.keyword},'%')
                        </if>
                        <if test="request.customerLevelId != null">
                            AND c.customer_level_id = #{request.customerLevelId}
                        </if>
                        <if test="request.customerTypeId != null">
                            AND c.customer_type_id = #{request.customerTypeId}
                        </if>
                        <if test="request.salesUserId != null and request.salesUserId != 'null' and request.salesUserId != ''">
                            AND FIND_IN_SET(#{request.salesUserId}, c.sales_user_ids) > 0
                        </if>
                    </if>
                    AND c.deleted = 0 AND c.region_id = #{request.regionId} AND c.tenant_id = #{tenantId}
                </where>
            </script>
            """)
    @Result(property = "customer.salesUserIds", column = "customer.sales_user_ids", typeHandler = CommaStringToListTypeHandler.class)
    IPage<CustomerRegionUserResponse> page(@Param("tenantId") String tenantId, @Param("request") CustomerRegionUserSearchRequest request, IPage<CustomerRegionUserResponse> page);

}
