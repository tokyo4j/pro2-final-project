package jp.ac.keio.pro2finalproject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.ac.keio.pro2finalproject.Entity.Lease;
import jp.ac.keio.pro2finalproject.Service.LeaseService;

@RestController
@RequestMapping("api")
public class LeaseController {
    @Autowired
    LeaseService leaseService;

    @GetMapping("leases")
    public List<Lease> getLeases(
            @CookieValue("id") Long userId,
            @RequestParam(value = "user_id", required = false) Long targetUserId,
            @RequestParam(value = "furniture_id", required = false) Long furnId) {
        if (userId == 1) {
            return leaseService.getLeasesByUserIdAndFurnId(targetUserId, furnId);
        } else {
            return leaseService.getLeasesByUserId(userId);
        }
    }

    @PutMapping("lease")
    public String addLease(
            @CookieValue("id") Long userId,
            @RequestParam("user_id") Long targetUserId,
            @RequestParam("furniture_id") Long furnId,
            @RequestParam("amount") Integer amount) {

        if (userId == 1) {
            leaseService.addLease(targetUserId, furnId, amount);
        } else {
            leaseService.addLease(userId, furnId, amount);
        }

        return "Lease added.";
    }

    @DeleteMapping("lease/{lease_id}")
    public String deleteLease(
            @CookieValue("id") Long userId,
            @PathVariable("lease_id") Long leaseId) {
        if (userId == 1) {
            leaseService.removeLease(leaseId);
        } else {
            leaseService.removeLeaseIfUserMatches(userId, leaseId);
        }
        return "Lease removed.";
    }
}