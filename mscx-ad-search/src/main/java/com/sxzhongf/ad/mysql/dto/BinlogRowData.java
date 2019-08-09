package com.sxzhongf.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * BinlogRowData for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
@Data
public class BinlogRowData {

    private TableTemplate tableTemplate;

    private EventType eventType;

    private List<Map<String, String>> before;

    //只有update操作才会有
    private List<Map<String, String>> after;

}
