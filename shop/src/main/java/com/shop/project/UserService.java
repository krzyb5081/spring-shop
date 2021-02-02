package com.shop.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserSession userSession;
	
	public User getByUserName(String userName) {
		for(User user: userRepository.findAll()) {
			if(user.getUserName().equals(userName)) {
				return user;
			}
		}
		return null;
	}
	
	public boolean loginUser(User user) {
		User resultUser = getByUserName(user.getUserName());
		if( (resultUser != null) && (resultUser.getPassword().equals(user.getPassword())==true) ) {
			userSession.setUserId(resultUser.getId());
			userSession.setUserName(resultUser.getUserName());
			return true;
		}
		return false;
	}
	
	public void logoutUser() {
		userSession.setUserId(0);
		userSession.setUserName(null);
	}
	
	public boolean registerUser(User user) {
		User resultUser = getByUserName(user.getUserName());
		if(resultUser == null) {
			userRepository.save(user);
			return true;
		}
		return false;
	}
	
	public String getSessionUserName() {
		return userSession.getUserName();
	}
	
	public long getSessionUserId() {
		return userSession.getUserId();
	}
	
	public User getUserBySession() {
		return userRepository.findById(userSession.getUserId()).get();
	}
}
