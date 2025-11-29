// src/main/java/com/nyu/coincome/entity/dto/AdminUserUpdateDTO.java
package com.nyu.coincome.entity.dto;

import lombok.Data;

@Data
public class AdminUserUpdateDTO {
    private Integer userId;
    private String username;
    private String email;
    private String newPassword;  // 如果你不想在这里改密码，可以去掉这个字段
}
