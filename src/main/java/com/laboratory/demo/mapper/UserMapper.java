package com.laboratory.demo.mapper;

import com.laboratory.demo.entry.User;
import org.apache.ibatis.annotations.*;
import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select id,no,name,isdelete,lasttime from user where no=#{no} and password=#{password} limit 1")
    List<User> getUser(@Param("no") String no, @Param("password") String password);

    @Select("select id,no,name,isdelete,lasttime from admin where no=#{no} and password=#{password} limit 1")
    List<User> getAdmin(@Param("no") String no, @Param("password") String password);

    @Select("select id,no,name,isdelete,lasttime from user where id=#{id}")
    List<User> getUserById(@Param("id") Integer id);

    @Select("select id,no,name,lasttime from user limit #{start},#{limit}")
    List<User> getUsers(@Param("start") int start, @Param("limit") int limit);

    @Select("select id,no,name,lasttime from user where no=#{no}")
    List<User> getUserByNo(@Param("no") String no);

    @Select("select id,no,name,lasttime from admin where id=#{id}")
    List<User> getAdminById(@Param("id") Integer id);

    @Select("select password from user where id=#{id}")
    String selectUserPassById(@Param("id") int id);

    @Select("select password from admin where id=#{id}")
    String selectAdminPassById(@Param("id") int id);

    @Update("update user set lasttime=#{lasttime} where no=#{no}")
    void updateTime(@Param("lasttime") Date time, @Param("no") String no);

    @Update("update user set password=#{password} where id=#{id}")
    void updateUserPass(@Param("id") int id, @Param("password") String password);

    @Update("update admin set password=#{password} where id=#{id}")
    void updateAdminPass(@Param("id") int id, @Param("password") String password);

    @Insert("insert into user (no, password, name, isdelete, lasttime) values (#{no}, #{password}, #{name}, 0, #{lasttime})")
    void insertUser(@Param("no") String no, @Param("password") String password, @Param("name") String name, @Param("lasttime") Date lasttime);

    @Delete("delete from user where id=#{id}")
    void deleteUserById(@Param("id") int id);

    @Update("update user set no=#{no}, name=#{name} where id=#{id}")
    void updateUser(@Param("no") String no, @Param("name") String name, @Param("id") int id);
}
