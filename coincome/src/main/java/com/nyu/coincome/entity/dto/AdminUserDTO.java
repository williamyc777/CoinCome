// src/main/java/com/nyu/coincome/entity/dto/AdminUserDTO.java
package com.nyu.coincome.entity.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AdminUserDTO {
    private Integer userId;
    private String username;
    private String email;
    private String passwordHash;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean isAdmin;   // 是否在 Admin 表里
}
