package com.laboratory.demo.mapper;

import com.laboratory.demo.entry.Lab;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface LabMapper {
    @Select("select * from lab")
    List<Lab> getAllLab();
}
