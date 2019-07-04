package com.siemo.notif.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
import com.siemo.notif.system.message.Message;
import com.siemo.notif.system.message.Recipients;
import com.siemo.notif.system.message.SaveRequest;
import com.siemo.notif.system.message.SendBatchRequest;
import com.siemo.notif.system.message.SendGroupRequest;
import com.siemo.notif.system.message.SendRequest;
import com.siemo.notif.system.model.MasterData;
import com.siemo.notif.system.repository.RepositoryNotif;
import com.siemo.notif.system.service.ServiceNotif;


@Service
@PropertySource(value="classpath:/config/path.properties")
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
	
	@Override
	public BaseResponse saveData(SaveRequest request) {
		MasterData masterData = new MasterData(request.getUserId(), request.getTokenDevice(), request.getChannel(), request.getSystemOperasi());
		masterData = repositoryNotif.save(masterData);	
		BaseResponse response = new BaseResponse();
		response.setMessage("simpan");
		response.setStatus("berhasil");
		
		
		return  response;
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
	public BaseResponse sendOne(SendRequest request) {
		
		String uri = env.getProperty("batch.send.notification");
		String inqUri = restUtil.generateURI(uri);
		
		SendBatchRequest inqRequest = new SendBatchRequest();
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			inqRequest.setCustom_payload("{}");
			inqRequest.setDeeplink("http://google.fr");
			inqRequest.setGroup_id("batch_push_sender");
			Message message = new com.siemo.notif.system.message.Message();
			message.setBody("tes dari spring boot");
			inqRequest.setMessage(message);
			inqRequest.setPush_time("now");
			
			Recipients recipients = new Recipients();
			List<String> data = new ArrayList<String>();
			data.add("fWhihRGubNk:APA91bGAP-KN1xkqeYgHE8s3CD5awn4icASYzOt6eg2tNF4NmVQowSHI8Z0bbyzOA4WUFhjwEltKVTrG-HwZG74ZqaZcPkYzQ7DkMXFAzLL3196-vTNo5QzhTM-wcyZRVCUiFfTJchJm");
			data.add("fxxdKe7sldk:APA91bHoZZYP1exoftloq0nre3hobotfIpSRsfkXpgmpwM6X1byB022nnP9sxOf4kUBp_106N-CRFZeeJEFZDrm91hECW9ox0heqGsM1ImmivFKh2FdvbIlmwrzqDSzNYlOXAmvGdFPr");
			
			recipients.setTokens(data);
			inqRequest.setRecipients(recipients);
			inqRequest.setSandbox(false);
			String jsonRequest = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(inqRequest);
			System.out.println(jsonRequest);
			String json = jsonRequest;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResponseEntity<String> inqOmni_response = batchrest.postForEntity(inqUri, inqRequest, String.class);
		HttpStatus inqHttpStatus = inqOmni_response.getStatusCode();
		String bodyOmniResponse = inqOmni_response.getBody();
		
		System.out.println(uri);
		
		
		return null;
	}

	@Override
	public BaseResponse sendGroup(SendGroupRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseResponse sendAll() {
		// TODO Auto-generated method stub
		return null;
	}


}
