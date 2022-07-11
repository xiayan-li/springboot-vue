package com.example.springboot.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper  //表示这是一个mybatis的mapper类，也就是dao
//@Component
public interface BookMapper extends BaseMapper<Book> {
    // 根据用户id查询图书信息

}
