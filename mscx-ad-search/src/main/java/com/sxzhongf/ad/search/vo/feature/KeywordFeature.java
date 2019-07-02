package com.sxzhongf.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * KeywordFeature for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/7/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeywordFeature {
    private List<String> keywords;
}
