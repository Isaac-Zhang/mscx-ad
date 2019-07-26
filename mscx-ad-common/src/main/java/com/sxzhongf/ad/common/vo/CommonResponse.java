package com.sxzhongf.ad.common.vo;

import lombok.*;

import java.io.Serializable;

/**
 * CommonResponse for 统一响应体
 * 因为我们不确定要返回的数据的Data类型，因此我们使用泛型T来替代
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @since 2019/6/13
 */

/**
 * @Data是下属注解的组合注解
 *
 * @see Getter
 * @see Setter
 * @see RequiredArgsConstructor
 * @see ToString
 * @see EqualsAndHashCode
 * @see lombok.Value
 */
@Data
@NoArgsConstructor //无参构造函数
@AllArgsConstructor //全参构造函数
public class CommonResponse<T> implements Serializable {
    private Integer code = 0;
    private String message = "success";
    /**
     * 具体的数据对象信息
     */
    private T data;

    public CommonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse(T data) {
        this.data = data;
    }
}
