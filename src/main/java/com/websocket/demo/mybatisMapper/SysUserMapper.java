package com.websocket.demo.mybatisMapper;

import com.websocket.demo.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserMapper {

    /**
     *  根据用户查询用户系统信息
     * @param usercode
     * @return
     */
    @Select("SELECT * FROM sysUser WHERE userCode=#{userCode}")
    SysUser getSysUserByUserCode(@Param("userCode") String usercode);
}
