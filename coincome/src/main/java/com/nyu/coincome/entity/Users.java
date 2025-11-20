package com.nyu.coincome.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("Users")
public class Users {

    @TableId
    private Integer UserID;          // 主键

    private String Username;         // 用户名（唯一）
    private String Email;            // 用户 email（唯一）
    private String PasswordHash;     // 加密后的密码

    private Timestamp CreatedAt;     // 创建时间
    private Timestamp UpdatedAt;     // 更新时间
}