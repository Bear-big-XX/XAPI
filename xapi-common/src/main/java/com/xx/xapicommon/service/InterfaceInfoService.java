package com.xx.xapicommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.xapicommon.model.entity.InterfaceInfo;

/**
* @author 13200
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-10-22 11:20:18
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    /**
     * 从数据库中查询接口是否存在（请求路径，请求方法，请求参数）
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
