package com.nyu.coincome.service.impl;

import java.sql.Timestamp;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import com.nyu.coincome.entity.Users;
import com.nyu.coincome.entity.dto.AdminUserDTO;
import com.nyu.coincome.entity.dto.AdminUserUpdateDTO;
import com.nyu.coincome.mapper.AdminMapper;
import com.nyu.coincome.mapper.UsersMapper;
import com.nyu.coincome.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final AdminMapper adminMapper;

    @Autowired
    private final UsersMapper usersMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AdminServiceImpl(AdminMapper adminMapper, UsersMapper usersMapper) {
        this.adminMapper = adminMapper;
        this.usersMapper = usersMapper;
    }

    @Override
    public boolean isAdmin(Integer userId) {
        if (userId == null) return false;
        return adminMapper.isAdmin(userId);
    }



    @Override
    public List<AdminUserDTO> listAllUsers() {
        return usersMapper.findAllWithAdminFlag();
    }

    @Override
    public void updateUser(AdminUserUpdateDTO dto) {

        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("userId is required");
        }

        Users dbUser = usersMapper.selectById(dto.getUserId());
        if (dbUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        // 1️⃣ 检查用户名是否重复（排除自己）
        if (StringUtils.hasText(dto.getUsername())) {
            long count = usersMapper.selectCount(
                    new LambdaQueryWrapper<Users>()
                            .eq(Users::getUsername, dto.getUsername())
                            .ne(Users::getUserId, dto.getUserId())
            );
            if (count > 0) {
                throw new IllegalArgumentException("Username already exists");
            }
            dbUser.setUsername(dto.getUsername());
        }

        // 2️⃣ 检查邮箱是否重复（排除自己）
        if (StringUtils.hasText(dto.getEmail())) {
            long count = usersMapper.selectCount(
                    new LambdaQueryWrapper<Users>()
                            .eq(Users::getEmail, dto.getEmail())
                            .ne(Users::getUserId, dto.getUserId())
            );
            if (count > 0) {
                throw new IllegalArgumentException("Email already exists");
            }
            dbUser.setEmail(dto.getEmail());
        }

        // 3️⃣ 可选：修改密码（如果你在页面里允许改）
        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            String hash = encoder.encode(dto.getNewPassword());
            dbUser.setPasswordHash(hash);
        }

        dbUser.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        usersMapper.updateById(dbUser);
    }
}
