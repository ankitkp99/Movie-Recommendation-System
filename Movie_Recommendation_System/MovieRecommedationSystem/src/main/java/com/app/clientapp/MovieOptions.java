package com.app.clientapp;

import java.util.Scanner;

import com.app.services.UserServices;
import com.app.services.UserServicesImpl;

public class MovieOptions {

	static void movieOptions(String movieName) {
		Scanner sc = ScannerClass.getScanner();
		UserServices userServices= new UserServicesImpl();
		String movieOptionsChoice= null;
		String userName = MovieRecommendationSystem.getUsername();
		do {
			
			System.out.println("1: Watch Movie\n2: Add to watchlist\n3: Rate Movie\n4: Exit\n5: Logout\n"
					+ " Enter your choice");
			movieOptionsChoice = sc.nextLine();
			
			switch(movieOptionsChoice) {
			
			case "1":
				System.out.println(userServices.isMovieAddInHistory(userName, movieName)?"\n***** You Have Watched Movie !!! *****\n":
					"\n===== !!! You Have Watched Movie !!! =====\n");
				break;
				
			case "2":
				System.out.println(userServices.isAddMovieInWatchList(userName, movieName)?
						"\n***** Added in watchlist successfully *****\n":"\n===== !!! Movie may already exist or "
								+ "something went wrong !!! =====\n");
				break;
				
			case "3":
				try {
					System.out.println("Enter rating for \" "+movieName+" \" movie out of 10");
					String rating = sc.nextLine();
					double userRating = Double.parseDouble(rating);
					if(userRating<=10 && userRating >0)
					System.out.println(userServices.hasMovieRated(userName, movieName, rating)?"\n***** Thank You For Rating :) *****\n":
						"\n===== !!! Oops! Something went wrong :( !!! or You not yet watched movie =====\n");
					else {
						System.out.println("\n=====!!! Rating should be out of 10 !!!=====\n");
					}
					break;
				}
				catch(Exception e)
				{
					System.out.println("\n=====!!! Rating should be out of 10 !!!=====\n");
				}
					
				
			case "4":
				System.out.println("Return to user section");
				break;
			case "5":
				MovieRecommendationSystem.callMain();
				break;
				
			default:
				System.out.println("\n===== !!! Invalid Input !!! =====\n");
				break;
			}
		}while(!movieOptionsChoice.equals("4"));
	}
}
