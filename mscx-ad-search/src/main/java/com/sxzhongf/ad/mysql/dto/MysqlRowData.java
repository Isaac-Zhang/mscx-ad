package com.sxzhongf.ad.mysql.dto;

import com.sxzhongf.ad.mysql.constant.OperationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MysqlRowData for 简化{@link BinlogRowData} 以方便实现增量索引的实现
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MysqlRowData {

    //实现多数据的时候，需要传递数据库名称
    //private String database;

    private String tableName;
    private String level;
    private OperationTypeEnum operationTypeEnum;
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();
}
