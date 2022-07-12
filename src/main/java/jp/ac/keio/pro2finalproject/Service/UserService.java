package jp.ac.keio.pro2finalproject.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.keio.pro2finalproject.Entity.User;
import jp.ac.keio.pro2finalproject.Repository.UserRepository;
import jp.ac.keio.pro2finalproject.exception.AuthenticationException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public long login(String username, String password) {
        var user = userRepository.findByName(username);
        if (user.getPassword().equals(password)) {
            return user.getId();
        } else {
            throw new AuthenticationException("Password does not match.");
        }
    }

    public void addUser(String useranme, String password) {
        var user = new User();
        user.setName(useranme);
        user.setPassword(password);
        userRepository.save(user);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
