package com.sxzhongf.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * JsonTable for 用于表示template.json中对应的表信息
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonTable {
    private String tableName;
    private Integer level;

    private List<Column> insert;
    private List<Column> update;
    private List<Column> delete;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Column {
        private String column;
    }
}
