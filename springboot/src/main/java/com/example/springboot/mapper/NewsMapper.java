package com.example.springboot.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.News;
import org.apache.ibatis.annotations.Mapper;


@Mapper  //表示这是一个mybatis的mapper类，也就是dao
//@Component
public interface NewsMapper extends BaseMapper<News> {

}
