package com.sxzhongf.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.mysql.constant.OperationTypeEnum;
import com.sxzhongf.ad.mysql.dto.BinlogTemplate;
import com.sxzhongf.ad.mysql.dto.ParseCustomTemplate;
import com.sxzhongf.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * TemplateHolder for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
@Slf4j
@Component
public class TemplateHolder {
    private ParseCustomTemplate template;

    private final JdbcTemplate jdbcTemplate;

    private String SQL_SCHEMA = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION FROM information_schema.COLUMNS " +
            "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 需要在容器加载的时候，就载入数据信息
     */
    @PostConstruct
    private void init() {
        loadJSON("template.json");
    }

    /**
     * 对外提供加载服务
     */
    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    /**
     * 加载需要监听的binlog json文件
     */
    private void loadJSON(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);

        try {
            BinlogTemplate binlogTemplate = JSON.parseObject(
                    inputStream,
                    Charset.defaultCharset(),
                    BinlogTemplate.class
            );

            this.template = ParseCustomTemplate.parse(binlogTemplate);
            loadMeta();
        } catch (IOException ex) {
            log.error((ex.getMessage()));
            throw new RuntimeException("fail to parse json file");
        }
    }

    /**
     * 使用表索引到列名称的映射关系
     */
    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();

            List<String> updateFields = table.getOpTypeFieldSetMap().get(
                    OperationTypeEnum.UPDATE
            );
            List<String> insertFields = table.getOpTypeFieldSetMap().get(
                    OperationTypeEnum.ADD
            );
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(
                    OperationTypeEnum.DELETE
            );

            jdbcTemplate.query(SQL_SCHEMA, new Object[]{
                            template.getDatabase(), table.getTableName()
                    }, (rs, i) -> {
                        int pos = rs.getInt("ORDINAL_POSITION");
                        String colName = rs.getString("COLUMN_NAME");

                        if ((null != updateFields && updateFields.contains(colName))
                                || (null != insertFields && insertFields.contains(colName))
                                || (null != deleteFields && deleteFields.contains(colName))) {
                            table.getPosMap().put(pos - 1, colName);
                        }
                        return null;
                    }
            );
        }
    }
}
