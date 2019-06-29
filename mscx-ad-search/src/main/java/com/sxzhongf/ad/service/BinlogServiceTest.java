package com.sxzhongf.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

/**
 * BinlogServiceTest for 测试Mysql binlog 监控
 * {@code
 * Mysql8 连接提示 Client does not support authentication protocol requested by server; consider upgrading MySQL client 解决方法
 * USE mysql;
 * ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
 * FLUSH PRIVILEGES;
 * }
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/26
 */
public class BinlogServiceTest {

    /**
     * --------Update-----------
     * UpdateRowsEventData{tableId=90, includedColumnsBeforeUpdate={0, 1, 2, 3, 4, 5, 6, 7}, includedColumns={0, 1, 2, 3, 4, 5, 6, 7}, rows=[
     *     {before=[11, 10, Test Bin Log, 1, Tue Jun 25 08:00:00 CST 2019, Tue Jun 25 08:00:00 CST 2019, Tue Jun 25 08:00:00 CST 2019, Tue Jun 25 08:00:00 CST 2019], after=[11, 10, zhangpan test Binlog, 1, Tue Jun 25 08:00:00 CST 2019, Tue Jun 25 08:00:00 CST 2019, Tue Jun 25 08:00:00 CST 2019, Tue Jun 25 08:00:00 CST 2019]}
     * ]}
     *
     * --------Insert-----------
     * WriteRowsEventData{tableId=91, includedColumns={0, 1, 2, 3, 4, 5, 6, 7}, rows=[
     *     [10, 11, ad unit test binlog, 1, 0, 1236.7655, Thu Jun 27 08:00:00 CST 2019, Thu Jun 27 08:00:00 CST 2019]
     * ]}
     */

    public static void main(String[] args) throws IOException {

//        //构造BinaryLogClient，填充mysql链接信息
//        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306,
//                "root", "12345678"
//        );
//
//        //设置需要读取的Binlog的文件以及位置，否则，client会从"头"开始读取Binlog并监听
////        client.setBinlogFilename("binlog.000035");
////        client.setBinlogPosition();
//
//        //给客户端注册监听器，实现对Binlog的监听和解析
//        //event 就是监听到的Binlog变化信息，event包含header & data 两部分
//        client.registerEventListener(event -> {
//            EventData data = event.getData();
//            if (data instanceof UpdateRowsEventData) {
//                System.out.println("--------Update-----------");
//                System.out.println(data.toString());
//            } else if (data instanceof WriteRowsEventData) {
//                System.out.println("--------Insert-----------");
//                System.out.println(data.toString());
//            } else if (data instanceof DeleteRowsEventData) {
//                System.out.println("--------Delete-----------");
//                System.out.println(data.toString());
//            }
//        });
//
//        client.connect();
    }
}
