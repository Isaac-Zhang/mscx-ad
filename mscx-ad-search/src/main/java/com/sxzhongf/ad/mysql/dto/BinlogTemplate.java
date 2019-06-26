package com.sxzhongf.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BinlogTemplate for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinlogTemplate {

    private String database;
    private List<JsonTable> tableList;
}
