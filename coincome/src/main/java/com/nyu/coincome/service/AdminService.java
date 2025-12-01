package com.nyu.coincome.service;

import com.nyu.coincome.entity.dto.AdminUserDTO;
import com.nyu.coincome.entity.dto.AdminUserUpdateDTO;

import java.util.List;

public interface AdminService {

    /**
     * 判断某个用户是否为管理员
     */
    boolean isAdmin(Integer userId);

    List<AdminUserDTO> listAllUsers();

    void updateUser(AdminUserUpdateDTO dto);
}
