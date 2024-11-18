package com.xx.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.mapper.UserInterfaceInfoMapper;
import com.xx.model.entity.UserInterfaceInfo;
import com.xx.common.ErrorCode;
import com.xx.exception.BusinessException;
import com.xx.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
* @author 13200
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2024-10-13 19:10:31
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{
    public void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        // 判断接口信息对象是否为空,为空则抛出参数错误的异常
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 如果是添加操作,所有参数必须非空,否则抛出参数错误的异常
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或用户不存在！");
            }
        }

        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余调用次数小于0！");
        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {

        if(interfaceInfoId <= 0 || userId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 构造查询请求
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
//        updateWrapper.set("leftNum", "leftNum - 1");
//        updateWrapper.set("totaLNum", "totalNum + 1");
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        //int updated = userInterfaceInfoMapper.update(null, updateWrapper);
        return this.update(updateWrapper);
    }
}




