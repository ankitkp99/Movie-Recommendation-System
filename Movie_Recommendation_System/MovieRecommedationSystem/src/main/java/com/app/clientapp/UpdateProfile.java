package com.app.clientapp;

import java.util.Scanner;

import com.app.services.UserServices;
import com.app.services.UserServicesImpl;

public class UpdateProfile {

	static void editProfile(String username) {
		UserServices userService = new UserServicesImpl();
		Scanner sc = ScannerClass.getScanner();
		String choice = "";
		do {
			System.out.println("1:Update Name\n2:Update Email\n3:Update Contact\n4:Change Password\n5:Previous Menu\n"
					+ "6:Logout\n\nEnter your choice");
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				System.out.println("Enter your name");
				String newName = sc.nextLine();
				System.out.println(userService.isUpdateProfileByName(newName, username)
						? "\n***** Name Updated Successfully *****\n"
						: "\n=====!!! Something went wrong  !!!=====\n");
				break;

			case "2":
				System.out.println("Enter your email");
				String newEmail = sc.nextLine();
				System.out.println(userService.isUpadateProfileEmail(newEmail, username)
						? "\n***** Name Updated Successfully *****\n"
						: "\n=====!!! Something went wrong  !!!=====\n");
				break;

			case "3":
				System.out.println("Enter your contact");
				String newContact = sc.nextLine();
				System.out.println(userService.isUpadateProfileContact(newContact, username)
						? "\n***** Name Updated Successfully *****\n"
						: "\n=====!!! Something went wrong  !!!=====\n");
				break;

			case "4":
				System.out.println("Enter new password");
				String password = sc.nextLine();
				if (password.length() > 5 && password.length() < 17) {
					System.out.println("Repeat your new password");

					if (password.equals(sc.nextLine())) {
						System.out.println(userService.isUpadatePassword(password, username)
								? "\n***** Password Updated Successfully *****\n"
								: "\n=====!!! Something went wrong  !!!=====\n");
					} else {
						System.out.println("\n=====!!! Password Missmatch !!!=====\n");
					}
				} else {
					System.out.println("\n=====!!! Password must between 6-16 !!!=====\n");
				}
				break;

			case "5":
				break;

			case "6":
				MovieRecommendationSystem.callMain();
				break;

			default:
				System.out.println("\n===== !!! Invalid Input !!! =====\n");
				break;
			}
		} while (!choice.equals("5"));
	}
}
