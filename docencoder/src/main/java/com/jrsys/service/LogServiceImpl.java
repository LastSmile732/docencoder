package com.jrsys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrsys.repository.LogRepository;
import com.jrsys.model.Logs;

@Service
public class LogServiceImpl implements LogService {

	//@Resource
	private LogRepository logRepository;
	
	@Override
	@Transactional
	public Logs create(Logs log) {
		Logs genLog = log;
		return logRepository.save(genLog);
	}

	@Override
	@Transactional
	public Logs delete(int id) {
		Logs delLog = logRepository.findOne(id);
		
		if (delLog == null){
			return null;
		}
		logRepository.delete(delLog);
		return delLog;
	}

	@Override
	@Transactional
	public List<Logs> findAll() {
		return logRepository.findAll();
	}

	@Override
	@Transactional
	public Logs update(Logs logs) {
		Logs updateLog = logRepository.findOne(logs.getId());
		
		if(updateLog==null){
			return null;
		}
		updateLog.setAction(logs.getAction());
		updateLog.setCertname(logs.getCertname());
		updateLog.setCerttype(logs.getCerttype());
		updateLog.setDatetime(logs.getDatetime());
		updateLog.setException(logs.getException());
		updateLog.setExceptionstack(logs.getExceptionstack());
		updateLog.setPdfName(logs.getPdfName());
		updateLog.setPdftype(logs.getPdftype());
		return updateLog;
	}

	@Override
	@Transactional
	public Logs findById(int id) {
		
		return logRepository.findOne(id);
	}

	@Override
	@Transactional
	public Logs logError(Logs logs, String exception) {
		Logs genLog = logs;
		genLog.setExceptionstack(exception);
		return logRepository.save(genLog);
	}

}
