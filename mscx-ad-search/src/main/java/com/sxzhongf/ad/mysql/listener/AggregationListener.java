package com.sxzhongf.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.sxzhongf.ad.mysql.TemplateHolder;
import com.sxzhongf.ad.mysql.dto.BinlogRowData;
import com.sxzhongf.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * AggregationListener for 监听Binlog, 收集mysql binlog datas
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/27
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tbName;

    private Map<String, Ilistener> listenerMap = new HashMap<>();

    @Autowired
    private TemplateHolder templateHolder;

    private String genKey(String dbName, String tbName) {
        return dbName + ":" + tbName;
    }

    /**
     * 根据表实现注册信息
     */
    public void register(String dbName, String tbName, Ilistener listener) {
        log.info("register : {}-{}", dbName, tbName);
        this.listenerMap.put(genKey(dbName, tbName), listener);
    }

    @Override
    public void onEvent(Event event) {

        EventType type = event.getHeader().getEventType();
        log.info("Event type: {}", type);

        //数据库增删改之前，肯定有一个table_map event 的binlog
        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tbName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        //EXT_UPDATE_ROWS 是Mysql 8以上的type
        if (type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_DELETE_ROWS
                ) {
            return;
        }

        // 检查表名和数据库名是否已经正确填充
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tbName)) {
            log.error("Meta data got error. tablename:{},database:{}", tbName, dbName);
            return;
        }

        //找出对应数据表敏感的监听器
        String key = genKey(this.dbName, this.tbName);
        Ilistener ilistener = this.listenerMap.get(key);
        if (null == ilistener) {
            log.debug("skip {}", key);
        }

        log.info("trigger event:{}", type.name());

        try {
            BinlogRowData rowData = convertEventData2BinlogRowData(event.getData());
            if (null == rowData) {
                return;
            }
            rowData.setEventType(type);
            ilistener.onEvent(rowData);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            this.dbName = "";
            this.tbName = "";
        }
    }

    /**
     * 解析Binlog数据到Java实体对象的映射
     *
     * @param data binlog
     * @return java 对象
     */
    private BinlogRowData convertEventData2BinlogRowData(EventData data) {
        TableTemplate tableTemplate = templateHolder.getTable(tbName);
        if (null == tableTemplate) {
            log.warn("table {} not found.", tbName);
            return null;
        }

        List<Map<String, String>> afterMapList = new ArrayList<>();

        for (Serializable[] after : getAfterValues(data)) {
            Map<String, String> afterMap = new HashMap<>();

            int columnLength = after.length;
            for (int i = 0; i < columnLength; ++i) {
                //取出当前位置对应的列名
                String colName = tableTemplate.getPosMap().get(i);
                //如果没有，则说明不需要该列
                if (null == colName) {
                    log.debug("ignore position: {}", i);
                    continue;
                }

                String colValue = after[i].toString();
                afterMap.put(colName, colValue);
            }

            afterMapList.add(afterMap);
        }

        BinlogRowData binlogRowData = new BinlogRowData();
        binlogRowData.setAfter(afterMapList);
        binlogRowData.setTableTemplate(tableTemplate);

        return binlogRowData;
    }

    /**
     * 获取不同事件的变更后数据
     * Add & Delete变更前数据假定为空
     */
    private List<Serializable[]> getAfterValues(EventData eventData) {

        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows()
                                                    .stream()
                                                    .map(Map.Entry::getValue)
                                                    .collect(Collectors.toList()
                                                    );
        }

        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        return Collections.emptyList();
    }
}
