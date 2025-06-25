//package com.plan.dto;
//
//public class CouponDto {
//}

package com.plan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CouponDto {

    private Long id;
    private String couponCode;
    private Integer discountPercent;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
