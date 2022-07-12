package jp.ac.keio.pro2finalproject.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ac.keio.pro2finalproject.Entity.Lease;
import jp.ac.keio.pro2finalproject.Repository.FurnRepository;
import jp.ac.keio.pro2finalproject.Repository.LeaseRepository;
import jp.ac.keio.pro2finalproject.Repository.UserRepository;
import jp.ac.keio.pro2finalproject.exception.DataIntegrityException;

@Service
public class LeaseService {

    @Autowired
    LeaseRepository leaseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FurnRepository furnRepository;

    public List<Lease> getAllLeases() {
        var leases = leaseRepository.findAll();
        return leases;
    }

    public List<Lease> getLeasesByUserIdAndFurnId(Long userId, Long furnId) {
        if (userId != null && furnId != null) {
            return leaseRepository.findByUserIdAndFurnId(userId, furnId);
        } else if (userId != null && furnId == null) {
            return leaseRepository.findByUserId(userId);
        } else if (userId == null && furnId != null) {
            return leaseRepository.findByFurnId(furnId);
        } else {
            return leaseRepository.findAll();
        }
    }

    public List<Lease> getLeasesByUserId(Long userId) {
        return leaseRepository.findByUserId(userId);
    }

    @Transactional
    public void addLease(long userId, long furnId, int amount) {
        var user = userRepository.getReferenceById(userId);
        var furn = furnRepository.findById(furnId).get();
        var currentLeasedAmount = furn.getLeasedAmount();
        var newLeasedAmount = currentLeasedAmount + amount;
        if (newLeasedAmount > furn.getAmount()) {
            throw new DataIntegrityException("Out of stock.");
        }
        furn.setLeasedAmount(newLeasedAmount);
        var lease = new Lease();
        lease.setUser(user);
        lease.setFurn(furn);
        lease.setAmount(amount);

        furnRepository.save(furn);
        leaseRepository.save(lease);
    }

    @Transactional
    public void removeLease(long leaseId) {
        var lease = leaseRepository.findById(leaseId).get();
        var furn = lease.getFurn();
        var totalLeasedAmount = furn.getLeasedAmount();
        var leasedAmountByUser = lease.getAmount();
        furn.setLeasedAmount(totalLeasedAmount - leasedAmountByUser);

        furnRepository.save(furn);
        leaseRepository.delete(lease);
    }

    @Transactional
    public void removeLeaseIfUserMatches(long userId, long leaseId) {

        var lease = leaseRepository.findByUserIdAndId(userId, leaseId);
        var furn = lease.getFurn();
        var totalLeasedAmount = furn.getLeasedAmount();
        var leasedAmountByUser = lease.getAmount();
        furn.setLeasedAmount(totalLeasedAmount - leasedAmountByUser);

        furnRepository.save(furn);
        leaseRepository.deleteByUserIdAndId(userId, leaseId);
    }
}
