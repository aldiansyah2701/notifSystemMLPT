package com.siemo.notif.system.service.impl;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siemo.notif.system.base.service.BaseBackendService;
import com.siemo.notif.system.base.util.service.RestUtil;
import com.siemo.notif.system.message.BaseResponse;
import com.siemo.notif.system.message.GetAllDataResponse;
import com.siemo.notif.system.message.GetDataRequest;

import com.siemo.notif.system.message.BatchMessage;
import com.siemo.notif.system.message.BatchRecipients;

import com.siemo.notif.system.message.SaveRequest;

import com.siemo.notif.system.message.SendAllRequest;
import com.siemo.notif.system.message.SendBatchRequest;
import com.siemo.notif.system.message.SendBatchResponse;

import com.siemo.notif.system.message.SendGroupRequest;
import com.siemo.notif.system.message.SendOneRequest;
import com.siemo.notif.system.model.Group;
import com.siemo.notif.system.model.HistoryNotificationExecution;
import com.siemo.notif.system.model.HistoryNotificationExecution.action;
import com.siemo.notif.system.model.MasterData;
import com.siemo.notif.system.repository.RepositoryGroup;
import com.siemo.notif.system.repository.RepositoryHistory;
import com.siemo.notif.system.repository.RepositoryNotif;
import com.siemo.notif.system.service.ServiceNotif;
import com.siemo.notif.system.specification.SearchCriteria;
import com.siemo.notif.system.specification.UserSpecification;

import springfox.documentation.spring.web.json.Json;


@Service
@PropertySource(value = "classpath:/config/path.properties")
@PropertySource(value = "classpath:/config/batch.properties")
@Configuration

public class ServiceNotifImpl implements ServiceNotif {

	
	@Autowired
	@Qualifier("batchrest")
	private BaseBackendService batchrest;


	@Autowired
	Environment env;
	
	@Autowired
	private RestUtil restUtil;
	
	@Autowired
	private RepositoryNotif repositoryNotif;
	
	@Autowired
	private RepositoryGroup repositoryGroup;
	
	@Autowired
	private RepositoryHistory repositoryHistory;

	@Override
	public BaseResponse saveData(SaveRequest request) {
		Group idGroup = repositoryGroup.findByCategoryAndDetail(request.getCategory(), request.getDetail());
		Date time = new Date();
		MasterData masterData = new MasterData(request.getUserId(), request.getTokenDevice(), request.getChannel(), request.getStatus(), request.getVersi(), idGroup, time);
		masterData = repositoryNotif.save(masterData);
		BaseResponse response = new BaseResponse();
		response.setMessage("simpan");
		response.setStatus("berhasil");

		return response;

	}

	@Override
	public GetAllDataResponse getAllData() {
		GetAllDataResponse response = new GetAllDataResponse();
		List<MasterData> listData = (List<MasterData>) repositoryNotif.findAll();
		response.setListData(listData);
		return response;
	}

	@Override
	public GetAllDataResponse getData(GetDataRequest request) {
		GetAllDataResponse response = new GetAllDataResponse();
		List<MasterData> listData = repositoryNotif.findByUserId(request.getUserId());
		response.setListData(listData);
		return response;
	}

	@Override
	public BaseResponse sendOne(SendOneRequest request) {
		BaseResponse response = new BaseResponse();
		String uri = env.getProperty("batch.send.one.notification");
		String inqUri = restUtil.generateURI(uri);
		
		SendBatchRequest inqRequest = new SendBatchRequest();
		BatchMessage body = new BatchMessage();
		body.setBody(request.getBody());

		ArrayList<String> listToken = new ArrayList<>();
		ArrayList<MasterData> listData = repositoryNotif.findTokensByUserId(request.getUserId());
		for (int i = 0; i < listData.size(); i++) {
			MasterData data = listData.get(i);
			listToken.add(data.getTokenDevice());
		}
		BatchRecipients data = new BatchRecipients();
		data.setTokens(listToken);
		
		String groupID = env.getProperty("batch.group.id");
		String custom = env.getProperty("batch.custom.payload");
		String deeplink = env.getProperty("batch.deeplink");
		String pushtime = env.getProperty("batch.push.time");
		String sandbox = env.getProperty("batch.sandbox");
		boolean sand = Boolean.valueOf(sandbox);
		
		inqRequest.setRecipients(data);
		inqRequest.setGroup_id(groupID);
		inqRequest.setCustom_payload(custom);
		inqRequest.setDeeplink(deeplink);
		inqRequest.setMessage(body);
		inqRequest.setPush_time(pushtime);
		inqRequest.setSandbox(sand);
		
		ResponseEntity<SendBatchResponse> inqBatch_response = batchrest.postForEntity(inqUri, inqRequest,
				SendBatchResponse.class);
		HttpStatus inqHttpStatus = inqBatch_response.getStatusCode();
		SendBatchResponse bodyBatchResponse = inqBatch_response.getBody();
		response.setMessage(bodyBatchResponse.getMessage());
		response.setStatus(bodyBatchResponse.getStatus());

		List<String> listId = new ArrayList<>();
		List<MasterData> listMasterDataId = repositoryNotif.findMasterDataIdByUserId(request.getUserId());
		for (int i = 0; i < listMasterDataId.size(); i++) {
			MasterData MDId = listMasterDataId.get(i);
			listId.add(MDId.getId());
		}
		
		String hist = null;
		hist = listId.toString();
		
		ObjectMapper mapper = new ObjectMapper();
		String reqHistory = null;
		try {
			reqHistory = mapper.writeValueAsString(inqRequest);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String resHistory = null;
		try {
			resHistory = mapper.writeValueAsString(bodyBatchResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date = new Date();
		HistoryNotificationExecution history = new HistoryNotificationExecution(action.SEND_ONE.toString(), date, reqHistory, resHistory, hist, bodyBatchResponse.getStatus());
		history = repositoryHistory.save(history);
		
		return response;
	}


	@Override
	public BaseResponse sendAll(SendAllRequest request) {
		BaseResponse response = new BaseResponse();
		String uri = env.getProperty("batch.send.all.notification");
		String inqUri = restUtil.generateURI(uri);
		
		SendBatchRequest inqRequest = new SendBatchRequest();
		BatchMessage body = new BatchMessage();
		body.setBody(request.getBody());

		ArrayList<String> listToken = new ArrayList<>();
		ArrayList<MasterData> listData = (ArrayList<MasterData>) repositoryNotif.findAll();
		for (int i = 0; i < listData.size(); i++) {
			MasterData data = listData.get(i);
			listToken.add(data.getTokenDevice());
		}
		BatchRecipients data = new BatchRecipients();
		data.setTokens(listToken);
		
		String groupID = env.getProperty("batch.group.id");
		String custom = env.getProperty("batch.custom.payload");
		String deeplink = env.getProperty("batch.deeplink");
		String pushtime = env.getProperty("batch.push.time");
		String sandbox = env.getProperty("batch.sandbox");
		boolean sand = Boolean.valueOf(sandbox);
		
		inqRequest.setRecipients(data);
		inqRequest.setGroup_id(groupID);
		inqRequest.setCustom_payload(custom);
		inqRequest.setDeeplink(deeplink);
		inqRequest.setMessage(body);
		inqRequest.setPush_time(pushtime);
		inqRequest.setSandbox(sand);
		
		ResponseEntity<SendBatchResponse> inqBatch_response = batchrest.postForEntity(inqUri, inqRequest,
				SendBatchResponse.class);
		HttpStatus inqHttpStatus = inqBatch_response.getStatusCode();
		SendBatchResponse bodyBatchResponse = inqBatch_response.getBody();
		response.setMessage(bodyBatchResponse.getMessage());
		response.setStatus(bodyBatchResponse.getStatus());
		return response;
	}
	
	
	@Override
	public BaseResponse sendGroup(SendGroupRequest request) {
		String category = null;
		String detail = null;
		
		BaseResponse response = new BaseResponse();
		String uri = env.getProperty("batch.send.all.notification");
		String inqUri = restUtil.generateURI(uri);
		
		SendBatchRequest inqRequest = new SendBatchRequest();
		BatchMessage body = new BatchMessage();
		body.setBody(request.getBody());
		
		//spec
		if (request.getGroup().containsKey("category")==false && request.getGroup().containsKey("detail")==true ) {
			 category = "";
			 detail = request.getGroup().get("detail").toString();
		} else if(request.getGroup().containsKey("category")==true && request.getGroup().containsKey("detail")==false) {
			category = request.getGroup().get("category").toString();
			detail = "";
		} else if(request.getGroup().containsKey("category")==false && request.getGroup().containsKey("detail")==false) {
			category ="";
			detail = "";
		} else {
			category = request.getGroup().get("category").toString();
			detail = request.getGroup().get("detail").toString();
		}
		
		UserSpecification spec1 = 
			      new UserSpecification(new SearchCriteria("category", ":", category));
			    UserSpecification spec2 = 
			      new UserSpecification(new SearchCriteria("detail", ":", detail));
			    
			    List<Group> results = repositoryGroup.findAll(Specification.where(spec1).and(spec2));
			    
			    List<MasterData> listId = null;
			    List<MasterData> listCollect = new ArrayList<MasterData>();
			    for(int i=0; i < results.size(); i++) {
			    	int id = results.get(i).getId();
			    	listId = repositoryNotif.findTokensByGroupId(id);
			    	listCollect.addAll(listId);
			    }
	
			    ArrayList<String> listToken = new ArrayList<>();
				for (int i = 0; i < listCollect.size(); i++) {
					listToken.add(listCollect.get(i).getTokenDevice());
				}
				BatchRecipients data = new BatchRecipients();
				data.setTokens(listToken);
		
				
		String groupID = env.getProperty("batch.group.id");
		String custom = env.getProperty("batch.custom.payload");
		String deeplink = env.getProperty("batch.deeplink");
		String pushtime = env.getProperty("batch.push.time");
		String sandbox = env.getProperty("batch.sandbox");
		boolean sand = Boolean.valueOf(sandbox);
		
		inqRequest.setRecipients(data);
		inqRequest.setGroup_id(groupID);
		inqRequest.setCustom_payload(custom);
		inqRequest.setDeeplink(deeplink);
		inqRequest.setMessage(body);
		inqRequest.setPush_time(pushtime);
		inqRequest.setSandbox(sand);
		
		ResponseEntity<SendBatchResponse> inqBatch_response = batchrest.postForEntity(inqUri, inqRequest,
				SendBatchResponse.class);
		HttpStatus inqHttpStatus = inqBatch_response.getStatusCode();
		SendBatchResponse bodyBatchResponse = inqBatch_response.getBody();
		response.setMessage(bodyBatchResponse.getMessage());
		response.setStatus(bodyBatchResponse.getStatus());
		return response;
	}

}
