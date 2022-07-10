package jp.ac.keio.pro2finalproject.Repository;

import jp.ac.keio.pro2finalproject.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByName(String username);
}