package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
	private String name;
	private String email;
	private String contact;
	private String username;
	private String password;
	private String date;
	private String type;
}
