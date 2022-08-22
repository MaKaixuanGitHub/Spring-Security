package com.xxxx.springsecurityoauth2demo.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult implements Serializable {
    private Integer status; //200业务执行成功 201业务执行失败
    private String msg; //服务器的提示信息
    private Object data; //封装后台返回值


    public static SysResult fail(){
        return new SysResult(201,"业务执行失败",null);
    }

    public static SysResult success(){
        return new SysResult(200,"业务执行成功",null);
    }

    //服务器返回业务数据
    public static SysResult success(Object data){
        return new SysResult(200,"业务执行成功",data);
    }

    public static SysResult success(String msg,Object data){
        return new SysResult(200,msg,data);
    }
}
