package com.shensheng.persistence.mapper;

import com.shensheng.persistence.beans.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by shensheng on 2017/3/27.
 */
@Mapper
public interface UserInfoMapper {
    User check(User user);
}
