package io.github.thebesteric.project.intelligent.module.crm.mapper.datum;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTagResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CustomerTagMapper extends IBaseMapper<CustomerTag> {


    /**
     * 分页查询
     *
     * @param tenantId 租户 ID
     * @param keyword  标签名称/标签组名称
     * @param page     分页参数
     *
     * @return IPage<CustomerTagResponse>
     *
     * @author wangweijun
     * @since 2024/12/27 14:06
     */
    @Select("""
            <script>
                SELECT t.id, t.name, g.id as group_id, g.name as group_name
                FROM t_crm_customer_tag t LEFT JOIN t_crm_customer_tag_group g ON t.group_id = g.id
                <where>
                    <if test="keyword != null and keyword != 'null' and keyword != ''">
                        AND t.name like CONCAT('%',#{keyword},'%') OR g.name like CONCAT('%',#{keyword},'%')
                    </if>
                    AND t.deleted = 0 AND t.tenant_id = #{tenantId}
                </where>
            </script>
            """)
    IPage<CustomerTagResponse> page(@Param("tenantId") String tenantId, @Param("keyword") String keyword, IPage<CustomerTagResponse> page);
}
