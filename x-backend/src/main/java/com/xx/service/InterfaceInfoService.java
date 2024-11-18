package com.xx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.model.entity.InterfaceInfo;

/**
* @author 13200
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-10-22 11:20:18
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
