package com.sxzhongf.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * PlanGetRequestVO for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanGetRequestVO {
    private Long userId;
    private List<Long> planIds;

    public boolean validate() {
        return userId != null && !CollectionUtils.isEmpty(planIds);
    }
}
