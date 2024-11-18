package com.xx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.model.entity.UserInterfaceInfo;

/**
* @author 13200
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-10-13 19:10:31
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    public void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
