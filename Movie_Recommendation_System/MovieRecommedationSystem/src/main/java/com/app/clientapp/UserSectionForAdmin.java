package com.app.clientapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.model.UserModel;
import com.app.services.AdminServices;
import com.app.services.AdminServicesImpl;

public class UserSectionForAdmin {

	static void getUserDataHelper(List<UserModel> userList)
	{
		List<UserModel> userData = new ArrayList<>(userList);
		System.out.println("ID  Name\tContact\t\tDate & Time\t\tEmail\t\t\tUsername\n________________________________"
				+ "_____________________________________________________________________");
		int count = 1;
		
		for(UserModel model : userData) {
			System.out.println(count++ +"  "+model.getName()+"\t"+model.getContact()+"\t"+model.getDate()+
					"\t"+model.getEmail()+"\t"+model.getUsername());
		}
	}
	
	static void userSection()
	{
		Scanner sc = ScannerClass.getScanner();
		AdminServices adminService = new AdminServicesImpl();
		String choice="";		
		do {
			System.out.println("------------------------------");
			System.out.println("=======  User Section  =======");
			System.out.println("------------------------------");
			System.out.println("\n1:Show All Users\n2:Block User\n3:Unblock User\n4:Exit\n5:Logout");
			choice = sc.nextLine();
			
			switch(choice)
			{
			case "1":
				System.out.println("\n\t\t\t\t********** All Users **********\n");
				List<UserModel> userData = new ArrayList<>(adminService.getAllUserData());
				getUserDataHelper(userData);
				break;
				
			case "2":
				System.out.println("\n\t\t\t\t********** All Users **********\n");
				getUserDataHelper(adminService.getAllUserData());
				System.out.println("\nEnter username from user list");
				String username = sc.nextLine();
				System.out.println(adminService.isBlockedUser(username)?"\n*** User Blocked Successfully ***\n":
					"\n===== !!! Something went wrong !!! =====\n");
				break;
				
			case "3":
				List<UserModel> blockedUser = new ArrayList<>(adminService.getAllBlockedUser());
				if(!blockedUser.isEmpty())
				{
					System.out.println("\n\t\t\t********** All Blocked Users **********\n");
					getUserDataHelper(blockedUser);
					System.out.println("\nEnter username to unblock");
					username = sc.nextLine(); 
					System.out.println(adminService.isUnblockUser(username)?"\n*** User Unblocked Successfully ***\n":
						"\n===== !!! Something went wrong !!! =====\n"); 
				}
				else {
					System.out.println("\n===== !!! Blacklist is Empty !!! =====\n");
				}
				
				break;
				
			case "4":
				break;
				
			case "5":
				MovieRecommendationSystem.callMain();
				break;
				
			default :
				System.out.println("\n===== !!! Invalid Input !!! =====\n");
				break;
			}
			
		}while(!choice.equals("4"));
	}
}
