package com.example.springboot.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper  //表示这是一个mybatis的mapper类，也就是dao
//@Component
public interface UserMapper extends BaseMapper<User> {

}
