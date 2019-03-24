package com.redis;

import org.redisson.connection.CRC16;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/24
 * Time: 18:18
 * Desc:
 */
public class CRCDemo {

    public static final int MAX_SLOT = 16384;

    public static void main( String args[] ) throws Exception{
        System.out.println(CRC16.crc16("999".getBytes())% MAX_SLOT);
        System.out.println(CRC16.crc16("998".getBytes())% MAX_SLOT);
        System.out.println(CRC16.crc16("997".getBytes())% MAX_SLOT);
        System.out.println(CRC16.crc16("996".getBytes())% MAX_SLOT);
        System.out.println(CRC16.crc16("995".getBytes())% MAX_SLOT);
        /**
         835
         4962
         8845
         12972
         719
         * */
    }
}
