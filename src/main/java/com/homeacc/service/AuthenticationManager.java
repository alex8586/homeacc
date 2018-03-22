package com.homeacc.service;

public interface AuthenticationManager {

	void registerGroup(String name, String password);

	long loginGroup(String name, String password);
}
