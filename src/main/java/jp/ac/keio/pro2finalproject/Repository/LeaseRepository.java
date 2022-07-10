package jp.ac.keio.pro2finalproject.Repository;

import jp.ac.keio.pro2finalproject.Entity.Lease;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    public List<Lease> findByUserId(long userId);

    public List<Lease> findByFurnId(long furnId);

    public List<Lease> findByUserIdAndFurnId(long userId, long furnId);

    public void deleteByUserIdAndId(long userId, long leaseId);

    public Lease findByUserIdAndId(long userId, long leaseId);
}
