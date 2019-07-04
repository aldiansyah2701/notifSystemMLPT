package com.siemo.notif.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "master_data")
public class MasterData {
	@Id
	private String id;
	
	@Column
	private String userId;
	
	@Column
	private String tokenDevice;
	
	@Column
	private String createdBy;
	
	@Column
	private Date createdDated;
	
	@Column
	private String updateBy;
	
	@Column
	private Date updateDated;
	
	@Column
	private String channel;
	
	@Column
	private String versi;
	
	@Column
	private String status;
	
	
	@ManyToOne
	private Group group;

	
	public MasterData() {
		
	}
	
	public MasterData(String userId) {
		this.userId = userId;
	}
	
	public MasterData(String userId, String tokenDevice, String channel, String status, String versi, Group group, Date createdDated) {
		this.userId = userId;
		this.tokenDevice = tokenDevice;
		this.channel = channel;
		this.status = status;
		this.versi = versi;
		this.group = group;
		this.createdDated = createdDated;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTokenDevice() {
		return tokenDevice;
	}

	public void setTokenDevice(String tokenDevice) {
		this.tokenDevice = tokenDevice;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDated() {
		return createdDated;
	}

	public void setCreatedDated(Date createdDated) {
		this.createdDated = createdDated;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDated() {
		return updateDated;
	}

	public void setUpdateDated(Date updateDated) {
		this.updateDated = updateDated;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getVersi() {
		return versi;
	}

	public void setVersi(String versi) {
		this.versi = versi;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JoinColumn(name="groupId")
	@ManyToOne
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
			
}
