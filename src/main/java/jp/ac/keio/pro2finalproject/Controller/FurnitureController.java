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
import org.springframework.web.multipart.MultipartFile;

import jp.ac.keio.pro2finalproject.Entity.Furn;
import jp.ac.keio.pro2finalproject.Service.FurnService;
import jp.ac.keio.pro2finalproject.Service.UserService;
import jp.ac.keio.pro2finalproject.exception.AuthorizationException;

@RestController
@RequestMapping("api")
public class FurnitureController {
    @Autowired
    UserService userService;

    @Autowired
    FurnService furnService;

    @GetMapping("furnitures")
    public List<Furn> getFurns() {
        return furnService.getAll();
    }

    @PutMapping("furniture")
    public String addFurn(
            @CookieValue("id") Long userId,
            @RequestParam("name") String furnName,
            @RequestParam("amount") Integer amount,
            @RequestParam(value = "img_file", required = false) MultipartFile imgFile) {
        if (userId != 1) {
            throw new AuthorizationException();
        }
        furnService.saveFurn(furnName, amount, imgFile);
        return "Furniture saved.";
    }

    @DeleteMapping("furniture/{furnId}")
    public String deleteFurn(
            @CookieValue("id") Long userId,
            @PathVariable("furnId") Long furnId) {
        if (userId != 1) {
            throw new AuthorizationException();
        }
        furnService.deleteFurn(furnId);
        return "Furniture deleted";
    }

    @PatchMapping("furniture/{furnId}")
    public String updateFurn(
            @CookieValue("id") Long userId,
            @PathVariable("furnId") Long furnId,
            @RequestParam(value = "name", required = false) String furnName,
            @RequestParam(value = "amount", required = false) Integer amount,
            @RequestParam(value = "img_file", required = false) MultipartFile imgFile) {
        if (userId != 1) {
            throw new AuthorizationException();
        }
        furnService.updateFurn(furnId, furnName, amount, imgFile);
        return "";
    }
}