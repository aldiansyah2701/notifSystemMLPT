package com.siemo.notif.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "history_notification_execution")
public class HistoryNotificationExecution {
		@Id
		private String id;
		
		@Column
		@NotNull
		private String action;
		
		@Column
		@NotNull
		private Date executionDate;
		
		@Column
		private String masterDataId;
		
		@Lob
		@Column
		@NotNull
		private String requestObject;
		
		@Lob
		@Column
		@NotNull
		private String responseObject;
		
		@Column
		@NotNull
		private String status;
		
		public enum action {
			SEND_ONE, SEND_GROUP, SEND_ALL
		}

		public HistoryNotificationExecution() {
			
		}
		
		public HistoryNotificationExecution(String action, Date executionDate, String request, String response, String masterDataId, String status) {
			this.action=action;
			this.executionDate=executionDate;
			this.requestObject=request;
			this.responseObject=response;
			this.masterDataId=masterDataId;
			this.status=status;
		}

		
		@Id
		@GenericGenerator(strategy = "uuid", name = "system-uuid")
		@GeneratedValue(generator = "system-uuid")
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public Date getExecutionDate() {
			return executionDate;
		}

		public void setExecutionDate(Date executionDate) {
			this.executionDate = executionDate;
		}

		public String getRequestObject() {
			return requestObject;
		}

		public void setRequestObject(String requestObject) {
			this.requestObject = requestObject;
		}

		public String getResponseObject() {
			return responseObject;
		}

		public void setResponseObject(String responseObject) {
			this.responseObject = responseObject;
		}

		public String getMasterDataId() {
			return masterDataId;
		}

		public void setMasterDataId(String masterDataId) {
			this.masterDataId = masterDataId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}






}
