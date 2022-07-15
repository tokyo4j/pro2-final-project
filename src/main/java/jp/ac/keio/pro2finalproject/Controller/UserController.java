package jp.ac.keio.pro2finalproject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.ac.keio.pro2finalproject.Entity.User;
import jp.ac.keio.pro2finalproject.Service.FurnService;
import jp.ac.keio.pro2finalproject.Service.UserService;
import jp.ac.keio.pro2finalproject.exception.AuthorizationException;
import jp.ac.keio.pro2finalproject.exception.DataIntegrityException;

@RestController
@RequestMapping("api")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    FurnService furnService;

    @GetMapping("users")
    public List<User> getAllUsers(@CookieValue("id") Long userId) {
        if (userId != 1) {
            throw new AuthorizationException();
        }
        var users = userService.findAll();

        return users;
    }

    @PutMapping("user")
    public String addUser(
            @CookieValue("id") Long userId,
            @RequestParam("user_name") String userName,
            @RequestParam("password") String password) {
        if (userId != 1) {
            throw new AuthorizationException();
        }
        userService.addUser(userName, password);
        return "User added.";
    }

    @DeleteMapping("user/{userId}")
    public String deleteUser(
            @CookieValue("id") Long userId,
            @PathVariable("userId") Long targetUserId) {
        if (userId != 1) {
            throw new AuthorizationException();
        }
        if (targetUserId == 1) {
            throw new DataIntegrityException("Administrator cannot be deleted.");
        }
        userService.deleteById(targetUserId);
        return "User deleted.";
    }

    @PatchMapping("user/{userId}")
    public String updateUser(
            @CookieValue("id") Long userId,
            @PathVariable("userId") Long targetUserId,
            @RequestParam(value = "name", required = false) String userName,
            @RequestParam(value = "password", required = false) String password) {

        if (userId != 1) {
            throw new AuthorizationException();
        }

        if (targetUserId == 1) {
            throw new DataIntegrityException("Administrator cannot be edited.");
        }

        userService.updateUser(targetUserId, userName, password);

        return "User updated.";
    }
}