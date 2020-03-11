package com.komia.kmsys.po;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("SysUser")
public class User implements Serializable{
	private static final long serialVersionUID = 5847984888698536252L;
	
	private int id;
	private String username;
	private String password;
	private String nickname;
	private int status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
