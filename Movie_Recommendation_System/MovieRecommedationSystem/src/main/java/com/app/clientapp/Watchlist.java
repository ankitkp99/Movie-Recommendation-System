package com.app.clientapp;

import java.util.List;
import java.util.*;
import com.app.services.UserServices;
import com.app.services.UserServicesImpl;

public class Watchlist {

	static void watchListFunctinality() {
		UserServices userService = new UserServicesImpl();
		Scanner sc = ScannerClass.getScanner();
		String userName = MovieRecommendationSystem.getUsername();

		List<String> watchlist = new ArrayList<>();
		watchlist = userService.getUserWatchlist(userName);
		if (!watchlist.isEmpty()) {
			System.out.println("\n***** Your Watchlist *****\n");
			System.out.println("ID\tMovie Name\n_______________________________________________");
			
			int count = 1;
			
			for(String str : watchlist)
			{
				System.out.println(count++ +"\t"+str);
			}

			String userChoice = "";
			do {
				System.out.println("\n1:Delete by name\n2:Clear watchlist\n3:Exit\n4:Logout\nEnter your choice");
				userChoice = sc.nextLine();

				switch (userChoice) {
				case "1":
					System.out.println("Enter movie name");
					String movieName = sc.nextLine();
					System.out.println(userService.isDeleteWatchlist(userName, movieName)?"\n***** "+movieName+" movie removed "
							+ "from watchlist *****\n":"\n===== !!! Something Went Wrong !!! =====\n");
					break;

				case "2":
					System.out.println(userService.isDeleteAllWatchlist(userName)?"\n***** Watchlist is cleared !!! *****\n":
						"\n===== !!! Something Went Wrong !!! =====\n");
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
			} while (!userChoice.equals("3"));
		}
		else {
			System.out.println("\n===== !!! Watchlist is emplty !!! ======\n");
		}
	}
}
