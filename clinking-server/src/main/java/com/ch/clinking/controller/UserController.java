package com.ch.clinking.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ch.clinking.entity.*;
import com.ch.clinking.entity.Dto.AddMemberRequest;
import com.ch.clinking.mapper.ProductDesignerMapper;
import com.ch.clinking.mapper.UserMapper;
import com.ch.clinking.service.UserService;
import com.ch.clinking.service.UserSetService;
import com.ch.clinking.service.UserShopService;
import com.ch.clinking.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserShopService userShopService;

    @Resource
    private UserSetService userSetService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ProductDesignerMapper productDesignerMapper;


    @PostMapping("/checkLogin")
    public Result checkLogin( HttpServletRequest request){
        //从request中的cookie中获取token
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    // 2. 解析 Token
                    // 3️⃣ **验证 JWT 令牌**
                    Claims claims = JwtUtils.validateJWT(token).getClaims();

                    String account = claims.getId();
                    String password = claims.getSubject();
                    if (userService.checkLogin(account, password) == null) {
                        return Result.error(400, "账号或密码错误");
                    }
                }
            }
        }
        return Result.success();

    }




    @PostMapping("/login")
    public Result checkLogin(@RequestParam("account") String account,@RequestParam("password")  String password, HttpServletResponse response){
        if (account == null || password == null) {
            return Result.error(400, "账号和密码不能为空");
        }
        User user = userService.checkLogin(account, password);
        // 生成 JWT Token
        String token = JwtUtils.createJWT(account,password, 1000 * 60 * 60 * 24);


        // 3. 将 Token 存入 HttpOnly Cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);  // 前端 JS 不能访问
        cookie.setSecure(false);   // 开发环境先关闭 HTTPS，生产环境建议开启
        cookie.setPath("/");       // 作用域为整个站点
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 天有效期
        response.addCookie(cookie);
        response.setStatus(200);

        user.setToken(token);


       return Result.success(user);
    }

    @GetMapping("/getBoundDesigners")
    public Result getBoundDesigners(@RequestParam("skcId") String skcId, @RequestParam("shopId") String shopId) {
        List<String> designers = productDesignerMapper.selectAllDesignersByShopIdAndSkcId(skcId,shopId);


        // 需要移除的特定元素
        Set<String> elementsToRemove = new HashSet<>(Arrays.asList("林招财", "沫瀛唸", "Top"));

// 使用 removeIf 移除
        designers.removeIf(elementsToRemove::contains);

        return Result.success(designers);
    }


    @PostMapping("/insert")
    public Result insertUser(@RequestBody User user) {
        userService.save(user);
        System.out.println(user.getAccount());
        return Result.success();
    }

    @PostMapping("/selectByAccount")
    public User selectById(@RequestParam("account") String account) {
        User user = userService.getOne(new QueryWrapper<User>().eq("account", account));
        System.out.println(user);
        return user;
    }

    @PostMapping("/selectMemberByShopId")
    public List<User> selectMemberByShopId(@RequestParam("shopId") String shopId) {
        List<User> users = userService.selectMemberByShopId(shopId);
        System.out.println("member" + users);
        return users;
    }

    @PostMapping("/updateSaveMoney")
    public Result updateSaveMoney(@RequestParam("saveMoney") String saveMoney, @RequestParam("account") String account) {
        userService.updateSaveMoney(Double.parseDouble(saveMoney), account);
        return Result.success();
    }

    @PostMapping("/updateTotalMoney")
    public Result updateTotalMoney(@RequestParam("totalMoney") String totalMoney, @RequestParam("account") String account) {
        userService.updateTotalMoney(Double.parseDouble(totalMoney), account);
        return Result.success();
    }

    @PostMapping("/updateHeadImage")
    public Result updateHeadImage(@RequestParam("headImage") String headImage, @RequestParam("account") String account) {
        userService.updateHeadImage(headImage, account);
        return Result.success();
    }

    @PostMapping("/updateUserProfile")
    public Result updateUserProfile(@RequestParam("nickName") String nickName, @RequestParam("gender") String gender, @RequestParam("updateTime") String updateTime, @RequestParam("updateBy") String updateBy, @RequestParam("account") String account) {
        System.out.println(nickName + gender + updateTime + updateBy + account);
        userService.updateUserProfile(nickName, gender, updateTime, updateBy, account);
        return Result.success();
    }

    @PostMapping("/addMember")
    public Result addMember(@RequestBody AddMemberRequest request) {
        System.out.println("addMember"+request);
        userService.save(request.getUser());
        for (String shopId : request.getShopIdList()) {
            UserShop userShop = new UserShop();
            userShop.setAccount(request.getUser().getAccount());
            userShop.setShopId(shopId);
            userShopService.save(userShop);
        }
        return Result.success();
    }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody AddMemberRequest request) {
        System.out.println("changePassword"+request);
        userService.saveOrUpdate(request.getUser());
        return Result.success();
    }

    @PostMapping("/updateShowSonMer")
    public Result updateShowSonMer(@RequestParam("account") String account, @RequestParam("showSonMer") int showSonMer) {
        UserSet set = userSetService.getOne(new QueryWrapper<UserSet>().eq("account", account));
        System.out.println(set);
        if (set == null) {
            UserSet s = new UserSet();
            s.setAccount(account);
            s.setShowSonMer(showSonMer);
            userSetService.saveOrUpdate(s);
        } else {
            set.setShowSonMer(showSonMer);
            userSetService.saveOrUpdate(set);
        }
        return Result.success();
    }

    @PostMapping("/selectShowSonMerByAccount")
    public UserSet selectShowSonMerByAccount(@RequestParam("account") String account) {
        return userSetService.getOne(new QueryWrapper<UserSet>().eq("account", account));
    }




    @GetMapping("/test")
    public Result test() {
        return Result.success();
    }


}
