package com.xx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xx.model.entity.Post;

import java.util.Date;
import java.util.List;

/**
 * 帖子数据库操作
 *
 * @author xiongxi
 *  
 */
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 查询帖子列表（包括已被删除的数据）
     */
    List<Post> listPostWithDelete(Date minUpdateTime);

    /**
    * @author 13200
    * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
    * @createDate 2024-11-09 16:26:29
    * @Entity com.xx.model.entity.UserInterfaceInfo.UserInterfaceInfo
    */
}




