package com.sxzhongf.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.sxzhongf.ad.mysql.constant.Constant;
import com.sxzhongf.ad.mysql.constant.OperationTypeEnum;
import com.sxzhongf.ad.mysql.dto.BinlogRowData;
import com.sxzhongf.ad.mysql.dto.MysqlRowData;
import com.sxzhongf.ad.mysql.dto.TableTemplate;
import com.sxzhongf.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IncrementListener for 增量数据实现监听
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/27
 */
@Slf4j
@Component
public class IncrementListener implements Ilistener {

    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    //根据名称选择要注入的投递方式
    @Resource(name = "indexSender")
    private ISender sender;

    /**
     * 标注为 {@link PostConstruct}，
     * 即表示在服务启动，Bean完成初始化之后，立刻初始化
     */
    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListener register db and table info.");
        Constant.table2db.forEach((tb, db) -> aggregationListener.register(db, tb, this));
    }

    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table = eventData.getTableTemplate();
        EventType eventType = eventData.getEventType();

        //包装成最后需要投递的数据
        MysqlRowData rowData = new MysqlRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTableTemplate().getLevel());
        //将EventType转为OperationTypeEnum
        OperationTypeEnum operationType = OperationTypeEnum.convert(eventType);
        rowData.setOperationTypeEnum(operationType);

        //获取模版中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(operationType);
        if (null == fieldList) {
            log.warn("{} not support for {}.", operationType, table.getTableName());
            return;
        }

        for (Map<String, String> afterMap : eventData.getAfter()) {
            Map<String, String> _afterMap = new HashMap<>();
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();

                _afterMap.put(colName, colValue);
            }

            rowData.getFieldValueMap().add(_afterMap);
        }
        sender.sender(rowData);
    }
}
