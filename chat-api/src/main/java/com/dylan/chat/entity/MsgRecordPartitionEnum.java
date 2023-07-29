package com.dylan.chat.entity;

import java.time.LocalDateTime;

/**
 * @Classname PartitionEnum
 * @Description PartitionEnum
 * @Date 6/20/2023 6:26 PM
 */
public enum MsgRecordPartitionEnum {

    P202307(202308, "P202307"),
    P202312(202401, "P202312"),
    P202406(202407, "P202406"),
    P202412(Integer.MAX_VALUE, "P202412")
    ;
    private Integer month;

    private String partitionName;

    MsgRecordPartitionEnum(Integer month, String partitionName) {
        this.month = month;
        this.partitionName = partitionName;
    }

    public Integer getMonth() {
        return month;
    }

    public String getPartitionName() {
        return partitionName;
    }

    /**
     * 根据时间获取对应的分区枚举
     * @param localDateTime
     * @return
     */
    public static MsgRecordPartitionEnum getPartition(LocalDateTime localDateTime){

        LocalDateTime partition202307 = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        LocalDateTime partition202312 = LocalDateTime.of(2023, 8, 1, 0, 0, 0);
        LocalDateTime partition202406 = LocalDateTime.of(2024, 7, 1, 0, 0, 0);
        LocalDateTime partition202412 = LocalDateTime.of(2077, 8, 1, 0, 0, 0);

        if (localDateTime.isBefore(partition202307)){
            return P202307;
        }
        if (localDateTime.isAfter(partition202307) && localDateTime.isBefore(partition202312)){
            return P202312;
        }
        if (localDateTime.isAfter(partition202312) && localDateTime.isBefore(partition202406)){
            return P202406;
        }
        if (localDateTime.isAfter(partition202406) && localDateTime.isBefore(partition202412)){
            return P202412;
        }
        return P202307;
    }
}
