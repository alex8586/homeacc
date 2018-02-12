package com.homeacc.repository;

import java.util.List;

public interface GenericRepository {

	<T> void save(T clazz);

	<T> void update(T clazz);

	<T> void delete(T clazz);

	<T> T getById(Class<T> clazz, long id);

	<T> List<T> getAll(Class<T> clazz);

}
