package com.nyu.coincome.controller;

import com.nyu.coincome.entity.Result;
import com.nyu.coincome.entity.Coin;
import com.nyu.coincome.entity.Exchange;
import com.nyu.coincome.entity.dto.AdminUserDTO;
import com.nyu.coincome.entity.dto.AdminUserUpdateDTO;
import com.nyu.coincome.service.AdminManageService;
import com.nyu.coincome.service.AdminService;
import com.nyu.coincome.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminManageService adminManageService;
    private final JwtUtil jwtUtil;

    public AdminController(AdminService adminService,
                           AdminManageService adminManageService,
                           JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.adminManageService = adminManageService;
        this.jwtUtil = jwtUtil;
    }

    // =======================
    // ⭐ 工具：管理员鉴权
    // =======================
    private Integer checkAdmin(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        String userId = jwtUtil.getUserId(token.substring(7));
        if (userId == null) return null;

        boolean isAdmin = adminService.isAdmin(Integer.valueOf(userId));
        return isAdmin ? Integer.valueOf(userId) : null;
    }

    // =======================
    // 获取交易所
    // =======================
    @GetMapping("/exchanges")
    public Result getExchanges(HttpServletRequest request) {

        if (checkAdmin(request) == null) {
            return Result.error("Not authorized: Admin only.");
        }

        List<Exchange> list = adminManageService.listExchanges();
        return Result.success(list);
    }

    // =======================
    // 新增交易所
    // =======================
    @PostMapping("/exchanges")
    public Result addExchange(@RequestBody AddExchangeDTO dto,
                              HttpServletRequest request) {

        if (checkAdmin(request) == null) {
            return Result.error("Not authorized: Admin only.");
        }

        if (dto.getExchangeName() == null || dto.getExchangeName().isEmpty()) {
            return Result.error("Exchange name is required.");
        }

        adminManageService.addExchange(dto.getExchangeName(), dto.getExchangeLink());
        return Result.success();
    }

    @Data
    static class AddExchangeDTO {
        private String exchangeName;
        private String exchangeLink;
    }

    // =======================
    // 获取币种
    // =======================
    @GetMapping("/coins")
    public Result getCoins(HttpServletRequest request) {

        if (checkAdmin(request) == null) {
            return Result.error("Not authorized: Admin only.");
        }

        List<Coin> list = adminManageService.listCoins();
        return Result.success(list);
    }

    // =======================
    // 新增币种
    // =======================
    @PostMapping("/coins")
    public Result addCoin(@RequestBody AddCoinDTO dto,
                          HttpServletRequest request) {

        if (checkAdmin(request) == null) {
            return Result.error("Not authorized: Admin only.");
        }

        if (dto.getCoinName() == null || dto.getCoinName().isEmpty()) {
            return Result.error("Coin name is required.");
        }

        adminManageService.addCoin(dto.getCoinName(), dto.getCoinType());
        return Result.success();
    }

    @Data
    static class AddCoinDTO {
        private String coinName;
        private String coinType;
    }

    // GET /api/admin/users  列出所有用户
    @GetMapping("/users")
    public Result listUsers() {
        List<AdminUserDTO> list = adminService.listAllUsers();
        return Result.success(list);
    }

    // PUT /api/admin/users/{userId}  修改用户
    @PutMapping("/users/{userId}")
    public Result updateUser(@PathVariable Integer userId,
                             @RequestBody AdminUserUpdateDTO dto) {
        try {
            dto.setUserId(userId);
            adminService.updateUser(dto);
            return Result.success();
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("Update failed: " + e.getMessage());
        }
    }


}
