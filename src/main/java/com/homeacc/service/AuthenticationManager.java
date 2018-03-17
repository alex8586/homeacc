package com.homeacc.service;

public interface AuthenticationManager {

	void registerGroup(String name, String password);

	void loginGroup(String name, String password);
}
