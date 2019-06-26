package com.sxzhongf.ad.mysql.dto;

import com.sxzhongf.ad.mysql.constant.OperationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TableTemplate for Binlog日志中 字段索引 -> 字段名称 的一个转换映射
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {
    private String tableName;
    private String level;

    private Map<OperationTypeEnum, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * Binlog日志中 字段索引 -> 字段名称 的一个转换映射
     */
    private Map<Integer, String> posMap = new HashMap<>();
}
