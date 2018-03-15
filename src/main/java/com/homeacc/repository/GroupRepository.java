package com.homeacc.repository;

import com.homeacc.entity.Groups;

public interface GroupRepository {

	Groups getByNameAndPassword(String name, String password);
}
