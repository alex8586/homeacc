package com.homeacc.repository;

import com.homeacc.entity.Groups;

public interface GroupRepository {

	Groups getByName(String name, String password);
}
