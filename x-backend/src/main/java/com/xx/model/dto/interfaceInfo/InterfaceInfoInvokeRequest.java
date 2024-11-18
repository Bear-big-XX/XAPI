package com.xx.model.dto.interfaceInfo;


import lombok.Data;

import java.io.Serializable;


/**
 * 接口调用请求
 *
 * @author xiongxi
 *  
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 请求参数
     * [
     *   {"name": "username", "type": "string"}
     * ]
     */
    private String requestParams;

   private static final long serivalVersionUID = 1L;

}