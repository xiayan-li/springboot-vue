package com.example.springboot.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper  //表示这是一个mybatis的mapper类，也就是dao
//@Component
public interface UserMapper extends BaseMapper<User> {
    //一对多查询

}
