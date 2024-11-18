package com.xx.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口状态信息枚举
 */
public enum InterInfoStatusEnum {
    OFFLINE("关闭",0),
    ONLINE("上线",1);

    private final String text;
    private final int value;

    InterInfoStatusEnum(String text, int value){
        this.text = text;
        this.value = value;
    }

    public  Integer getValue(){
        return this.value;
    }
}
