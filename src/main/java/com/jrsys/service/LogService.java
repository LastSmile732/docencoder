package com.jrsys.service;

import java.util.List;

import com.jrsys.model.Logs;

public interface LogService {
	public Logs create(Logs logs);
	public Logs delete(int id);
	public List<Logs> findAll();
	public Logs update(Logs logs);
	public Logs findById(int id);
	public Logs logError(Logs logs, String exception);
}
