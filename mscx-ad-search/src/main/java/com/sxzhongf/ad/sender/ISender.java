package com.sxzhongf.ad.sender;

import com.sxzhongf.ad.mysql.dto.MysqlRowData;

/**
 * ISender for 投递增量数据 方法定义接口
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/27
 */
public interface ISender {

    void sender(MysqlRowData rowData);
}
