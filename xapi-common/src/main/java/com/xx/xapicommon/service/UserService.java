package com.xx.xapicommon.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.xapicommon.model.entity.User;
import com.xx.xapicommon.model.vo.LoginUserVO;
import com.xx.xapicommon.model.vo.UserVO;

import java.util.List;

/**
 * 用户服务
 *
 * @author xiongxi
 *  
 */
public interface UserService extends IService<User> {

    /**
     * 数据库中检索是否已经分配用户密钥（accessKey）
     * @param accessKey
     * @return
     */
    User getInvokerUser(String accessKey);
}
