package jp.ac.keio.pro2finalproject.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.ac.keio.pro2finalproject.Service.UserService;

@RestController
@RequestMapping("api")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("auth/login")
    public String login(@RequestParam("user_name") String userName, @RequestParam("password") String password,
            HttpServletResponse response) {
        try {
            var id = userService.login(userName, password);
            var cookie = new Cookie("id", String.valueOf(id));
            cookie.setPath("/");
            response.addCookie(cookie);
            return "authenticated";
        } catch (RuntimeException e) {
            var cookie = new Cookie("id", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            response.setStatus(401);
            return "Not authenticated.";
        }
    }
}
