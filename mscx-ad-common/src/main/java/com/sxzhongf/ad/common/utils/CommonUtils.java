package com.sxzhongf.ad.common.utils;


import com.sxzhongf.ad.common.exception.AdException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * CommonUtils for 工具类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/19
 */
@Slf4j
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

    /**
     * 处理字符串用'-'拼接
     *
     * @param args 可变参数数组
     * @return 拼接后结果
     */
    public static String stringConcat(String... args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg);
            builder.append("-");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    /**
     * Thu Jun 27 08:00:00 CST 2019
     */
    public static Date parseBinlogString2Date(String dateString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy",
                    Locale.US
            );
            return DateUtils.addHours(dateFormat.parse(dateString), -8);

        } catch (ParseException ex) {
            log.error("parseString2Date error:{}", dateString);
            return null;
        }
    }
}
