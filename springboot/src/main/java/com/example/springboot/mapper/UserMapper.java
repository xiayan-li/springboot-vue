package com.example.springboot.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.controller.dto.UserAddressDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper  //表示这是一个mybatis的mapper类，也就是dao
//@Component
public interface UserMapper extends BaseMapper<User> {
    //一对多查询
    //Page<User> findPage(Page<User> page, @Param("nickName") String nickName);

    //统计用户地址，画Echarts
    @Select("select count(id) count, address from user GROUP BY address")
    List<UserAddressDto> countAddress();
}
