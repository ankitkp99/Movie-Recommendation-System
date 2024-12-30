package com.app.clientapp;

import java.util.*;

import com.app.services.UserServices;
import com.app.services.UserServicesImpl;

public class UserHistory {

	static void userHistory() {
		UserServices userService = new UserServicesImpl();
		Scanner sc = ScannerClass.getScanner();
		String historyChoice = "";
		String userName = MovieRecommendationSystem.getUsername();

		Map<List<String>,String>userHistory = new LinkedHashMap<>();
		userHistory = userService.getUserHistory(userName);
		int count = 1;

		if (!userHistory.isEmpty()) {
			System.out.println("\n***** User History *****\n");
			System.out.println("ID\tDate\t\tUser Rating\tMovie Name\n_______________________________________________");
			for (Map.Entry<List<String>,String> m : userHistory.entrySet()) {
				
				
				System.out.print(count+++"\t"+m.getValue());
				for(String str: m.getKey())
				{
					System.out.print("\t"+str+"\t");
				}
				System.out.println();
			}

			do {
				System.out.println("\n1:Delete History By Name\n2:Delete All History\n3:Exit\n4:Logout\n\nEnter your choice");
				historyChoice = sc.nextLine();
				switch (historyChoice) {

				case "1":
					System.out.println("Enter movie name");
					String movieName = sc.nextLine();
					System.out.println(userService.isDeleteHistory(userName, movieName)?"\n***** "+movieName+" movie removed "
							+ "from your history *****\n":"\n===== !!! Something went wrong !!! =====\n");
					System.out.println();
					break;

				case "2":
					System.out.println(userService.isDeleteAllHistory(userName)?"\n***** All history cleared *****\n":"\n===== !!! Something went wrong !!! =====\n");
					break;

				case "3":
					System.out.println("Return to user section.....");
					break;
					
				case "4":
					MovieRecommendationSystem.callMain();
					break;
					
				default:
					System.out.println("\n===== !!! Invalid Input !!! =====\n");
					break;
				}
			} while (!historyChoice.equals("3"));
		}
		else
		{
			System.out.println("\n===== !!! No user history available !!! =====\n");
		}
	}
}
