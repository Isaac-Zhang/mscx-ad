package com.sxzhongf.ad.common.utils;


import com.sxzhongf.ad.common.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * CommonUtils for 工具类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/19
 */
public class CommonUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    /**
     * md5 加密
     */
    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    /**
     * 日期转换
     */
    public static Date parseStringDate(String dateStr) throws AdException {
        Date date = null;

        try {
            date = DateUtils.parseDate(dateStr, parsePatterns);
        } catch (ParseException pex) {
            throw new AdException("日期转换错误：" + pex.getMessage());
        }
        return date;
    }
}
