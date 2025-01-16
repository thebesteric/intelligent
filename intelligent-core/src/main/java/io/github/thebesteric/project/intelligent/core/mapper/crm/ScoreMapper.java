package io.github.thebesteric.project.intelligent.core.mapper.crm;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.thebesteric.project.intelligent.core.base.IBaseMapper;
import io.github.thebesteric.project.intelligent.core.constant.ApplicationConstants;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.ScoreCheckInTotalSearchRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.request.ScoreTotalSearchRequest;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreCheckInTotalResponse;
import io.github.thebesteric.project.intelligent.core.model.domain.crm.response.ScoreTotalResponse;
import io.github.thebesteric.project.intelligent.core.model.entity.crm.Score;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@DS(ApplicationConstants.Application.Module.CRM.DATASOURCE_INTELLIGENT_MODULE_CRM)
public interface ScoreMapper extends IBaseMapper<Score> {

    /**
     * 积分统计列表
     *
     * @param tenantId 租户 ID
     * @param request  请求
     * @param page     分页参数
     *
     * @return IPage<ScoreTotalResponse>
     *
     * @author wangweijun
     * @since 2025/1/16 15:31
     */
    @Select("""
            <script>
            SELECT 
                c.id AS customer_id, c.username AS customer_username, c.name AS customer_name, c.serial_no AS customer_serial_no, 
                l.id AS customer_level_id, l.name AS customer_level_name, 
                t.id AS customer_type_id, t.name AS customer_type_name, 
                SUM(s.change_score) AS total_score, SUM(case when s.score_type = 'SUB' then abs(s.change_score) else 0 end) AS total_used_score, 
                MAX(s.created_at) as last_used_at, MAX(s.updated_at) as last_adjust_at 
            FROM t_crm_score s
                LEFT JOIN t_crm_customer c ON c.id = s.customer_id
                LEFT JOIN t_crm_customer_type t ON t.id = c.customer_type_id
                LEFT JOIN t_crm_customer_level l ON l.id = c.customer_level_id
            <where>
                <if test="request != null">
                    <if test="request.keyword != null and request.keyword != 'null' and request.keyword != ''">
                        AND (c.username like CONCAT('%',#{request.keyword},'%') OR c.name like CONCAT('%',#{request.keyword},'%') OR c.serial_no like CONCAT('%',#{request.keyword},'%'))
                    </if>
                    <if test="request.customerLevelId != null">
                        AND c.customer_level_id = #{request.customerLevelId}
                    </if>
                    <if test="request.customerTypeId != null">
                        AND c.customer_type_id = #{request.customerTypeId}
                    </if>
                </if>
                AND s.deleted = 0 AND c.tenant_id = #{tenantId}
            </where>
            GROUP BY c.id, c.username, c.name, c.serial_no, l.id, l.name, t.id, t.name
            ORDER BY total_score DESC
            </script>
            """)
    IPage<ScoreTotalResponse> page(@Param("tenantId") String tenantId, @Param("request") ScoreTotalSearchRequest request, IPage<ScoreTotalResponse> page);

    /**
     * 签到积分统计列表
     *
     * @param tenantId 租户 ID
     * @param request  请求
     * @param page     分页参数
     *
     * @return IPage<ScoreCheckInTotalResponse>
     *
     * @author wangweijun
     * @since 2025/1/16 15:31
     */
    @Select("""
            <script>
            SELECT 
                c.id AS customer_id, c.username AS customer_username, c.name AS customer_name, c.serial_no AS customer_serial_no, 
                SUM(s.change_score) AS total_check_in_score,
                MAX(s.created_at) AS last_check_in_at,
                (SELECT change_score 
                    FROM t_crm_score
                    WHERE customer_id = c.id
                    ORDER BY created_at DESC
                    LIMIT 1) AS last_check_in_score
            FROM t_crm_score s
                LEFT JOIN t_crm_customer c ON c.id = s.customer_id
            <where>
                <if test="request != null">
                    <if test="request.keyword != null and request.keyword != 'null' and request.keyword != ''">
                        AND (c.username like CONCAT('%',#{request.keyword},'%') OR c.name like CONCAT('%',#{request.keyword},'%') OR c.serial_no like CONCAT('%',#{request.keyword},'%'))
                    </if>
                    <if test="request.startDate != null and request.endDate == null">
                        AND s.created_at &gt;= #{request.startDate}
                    </if>
                    <if test="request.startDate == null and request.endDate != null">
                        AND s.created_at &lt;= #{prequest.endDate}
                    </if>
                    <if test="request.startDate != null and request.endDate != null">
                        AND s.created_at between #{request.startDate} and #{request.endDate}
                    </if>
                </if>
                AND s.deleted = 0 AND s.tenant_id = #{tenantId} AND s.consume_type = 'CHECK_IN'
            </where>
            GROUP BY c.id, c.username, c.name, c.serial_no
            ORDER BY total_check_in_score DESC
            </script>
            """)
    IPage<ScoreCheckInTotalResponse> checkInPage(@Param("tenantId") String tenantId, @Param("request") ScoreCheckInTotalSearchRequest request, IPage<ScoreCheckInTotalResponse> page);
}
