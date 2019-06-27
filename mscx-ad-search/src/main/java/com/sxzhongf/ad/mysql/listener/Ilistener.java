package com.sxzhongf.ad.mysql.listener;

import com.sxzhongf.ad.mysql.dto.BinlogRowData;

/**
 * Ilistener for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
public interface Ilistener {

    void register();

    void onEvent(BinlogRowData eventData);
}
