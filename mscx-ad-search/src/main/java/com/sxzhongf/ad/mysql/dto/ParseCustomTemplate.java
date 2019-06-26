package com.sxzhongf.ad.mysql.dto;

import com.sxzhongf.ad.mysql.constant.OperationTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * ParseCustomTemplate for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
@Data
public class ParseCustomTemplate {

    private String database;

    /**
     * key -> TableName
     * value -> {@link TableTemplate}
     */
    private Map<String, TableTemplate> tableTemplateMap;

    public static ParseCustomTemplate parse(BinlogTemplate _template) {
        ParseCustomTemplate template = new ParseCustomTemplate();
        template.setDatabase(_template.getDatabase());

        for (JsonTable jsonTable : _template.getTableList()) {
            String name = jsonTable.getTableName();
            Integer level = jsonTable.getLevel();

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            template.tableTemplateMap.put(name, tableTemplate);

            //遍历操作类型对应的列信息
            Map<OperationTypeEnum, List<String>> operationTypeListMap = tableTemplate.getOpTypeFieldSetMap();

            for (JsonTable.Column column : jsonTable.getInsert()) {
                getAndCreateIfNeed(
                        OperationTypeEnum.ADD,
                        operationTypeListMap,
                        ArrayList::new
                ).add(column.getColumnName());
            }

            for (JsonTable.Column column : jsonTable.getUpdate()) {
                getAndCreateIfNeed(
                        OperationTypeEnum.UPDATE,
                        operationTypeListMap,
                        ArrayList::new
                ).add(column.getColumnName());
            }

            for (JsonTable.Column column : jsonTable.getDelete()) {
                getAndCreateIfNeed(
                        OperationTypeEnum.DELETE,
                        operationTypeListMap,
                        ArrayList::new
                ).add(column.getColumnName());
            }
        }

        return template;
    }

    /**
     * 从Map中获取对象，如果不存在，创建一个
     */
    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map, Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
