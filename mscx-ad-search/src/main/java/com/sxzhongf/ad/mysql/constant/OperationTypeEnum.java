package com.sxzhongf.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * OperationTypeEnum for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
public enum OperationTypeEnum {
    ADD,
    UPDATE,
    DELETE,
    OTHER;

    public static OperationTypeEnum convert(EventType type) {
        switch (type) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
