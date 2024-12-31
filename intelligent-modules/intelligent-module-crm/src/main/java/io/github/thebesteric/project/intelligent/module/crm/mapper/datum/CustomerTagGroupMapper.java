package io.github.thebesteric.project.intelligent.module.crm.mapper.datum;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.module.crm.model.domain.datum.response.CustomerTagGroupResponse;
import io.github.thebesteric.project.intelligent.module.crm.model.entity.datum.CustomerTagGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CustomerTagGroupMapper extends IBaseMapper<CustomerTagGroup> {

    /**
     * 分页查询
     *
     * @param tenantId 租户 ID
     * @param name     标签组名称
     * @param page     分页参数
     *
     * @return IPage<CustomerTagGroupResponse>
     *
     * @author wangweijun
     * @since 2024/12/27 17:26
     */
    @Select("""
            <script>
                SELECT g.id, g.name, count(t.id) as count
                FROM t_crm_customer_tag_group g LEFT JOIN t_crm_customer_tag t ON g.id = t.group_id
                <where>
                    <if test="name != null and name != 'null' and name != ''">
                        AND g.name like CONCAT('%',#{name},'%')
                    </if>
                    AND g.deleted = 0 AND g.tenant_id = #{tenantId}
                </where>
                GROUP BY g.id
            </script>
            """)
    IPage<CustomerTagGroupResponse> page(@Param("tenantId") String tenantId, @Param("name") String name, IPage<CustomerTagGroupResponse> page);
}
