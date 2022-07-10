package jp.ac.keio.pro2finalproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jp.ac.keio.pro2finalproject.Entity.User;
import jp.ac.keio.pro2finalproject.Repository.UserRepository;

@SpringBootApplication
public class Pro2FinalProjectApplication {
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Pro2FinalProjectApplication.class, args);
	}

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Paths.get("./data/img"));
		} catch (IOException e) {
			System.err.println("Could not create directory data/img");
		}

		var user = new User();
		user.setId(1L);
		user.setName("admin");
		user.setPassword("admin");
		userRepository.save(user);
	}
}
