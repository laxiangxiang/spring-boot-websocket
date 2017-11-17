package com.websocket.demo.mybatisMapper;

import com.websocket.demo.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findUserById(@Param("id")String id);

    @Insert("INSERT INTO users (NAME,age) VALUES (#{name},#{age})")
    boolean insertUsers(@Param("name")String name,@Param("age")Integer age);
}
