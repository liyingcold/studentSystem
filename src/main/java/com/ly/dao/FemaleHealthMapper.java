package com.ly.dao;

import com.ly.pojo.FemaleHealth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FemaleHealthMapper {


    FemaleHealth selectByPrimaryKey(@Param("id") Long id);


}