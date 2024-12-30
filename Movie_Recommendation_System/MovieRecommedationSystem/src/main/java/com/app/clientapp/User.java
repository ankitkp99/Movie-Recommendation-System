package com.app.clientapp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.app.model.MovieModel;
import com.app.model.UserModel;
import com.app.services.AdminServices;
import com.app.services.AdminServicesImpl;
import com.app.services.UserServices;
import com.app.services.UserServicesImpl;

public class User {
	static Scanner sc = ScannerClass.getScanner();
	static void showProfile()
	{
		UserServices userServices = new UserServicesImpl();
		
		UserModel profile = userServices.getProfile(MovieRecommendationSystem.getUsername());// username as parameter
		if (profile != null) {
			System.out.println("\n===== YOUR PROFILE =====\n");
			System.out.println("Username: " + profile.getUsername() + "\n\nName: " + profile.getName()
					+ "\nEmail: " + profile.getEmail() + "\nContact: " + profile.getContact() + "\n");

			UpdateProfile.editProfile(MovieRecommendationSystem.getUsername());
		} else {
			System.out.println("\n===== !!! Something went wrong !!! =====\n");
		}
	}
	
	static void displayMoviesList() {
		AdminServices adminService = new AdminServicesImpl();
		int count = 1;
		String movieName="";
		List<MovieModel> movieData = new ArrayList<>(adminService.getAllMovies());
		if (!movieData.isEmpty()) {
			System.out.println("\n  ***** All Movies List *****\n");
			for (MovieModel model : movieData) {
				System.out.println("\t" + count++ + ".  " + model.getMovieName());
			}

			while(true) {
				System.out.println("\nEnter movie name (𝓟𝓻𝓮𝓼𝓼 0 𝓯𝓸𝓻 𝓔𝔁𝓲𝓽) ");
				movieName = sc.nextLine();
				if(movieName.equals("0"))
				{
					break;
				}
				movieData = new ArrayList<>(adminService.getMoviesBySearch(movieName, "movieName"));
				SearchMovie.displayMovieHelper(movieData);
				if(!movieData.isEmpty())
				MovieOptions.movieOptions(movieName); 
			}
			
		} else {
			System.out.println("\n===== !!! No Movie Available !!! =====\n");
		}
	}
	static void userFunctionality() {
		
		UserServices userServices = new UserServicesImpl();
		AdminServices adminService = new AdminServicesImpl();
		String userChoice = null;
		do {
			System.out.println("-------------------------------");
			System.out.println("=======    User View    =======");
			System.out.println("-------------------------------");
			System.out.println(
					"\n1: Show All Movies\n2: Trending Movies\n3: Your Interest\n4: Search Movie\n5: User History\n6: Watchlist"
							+ "\n7: Profile\n8: Logout\nEnter your choice");
			userChoice = sc.nextLine();

			switch (userChoice) {
			case "1":
				displayMoviesList();
				break;
				
			case "2":
				List<MovieModel>movieData = new ArrayList<>(userServices.getTrendingMovies());
			
				int count = 1;
				System.out.println("\n***** Top 10 Trending Movies *****\n");
				for (MovieModel m : movieData) {
					System.out.println("\t"+count++ + ". " + m.getMovieName());
				}

				System.out.println("___________________________________");

				String movieName = "";
				boolean flag = false;
				List<MovieModel> selectedMovieData = new ArrayList<>();
				while (true) {
					System.out.println("\nEnter name of movie from trending list: (𝓟𝓻𝓮𝓼𝓼 0 𝓯𝓸𝓻 𝓔𝔁𝓲𝓽)");
					movieName = sc.nextLine();

					if (movieName.equals("0"))
						break;
					for (MovieModel m : movieData) {
						if (m.getMovieName().equalsIgnoreCase(movieName)) {
							selectedMovieData.add(m);
							flag = true;
							break;
						}

					}
					if (flag) {
						System.out.println("\n=======================================================\nDescription:");
						SearchMovie.displayMovieHelper(selectedMovieData);

						MovieOptions.movieOptions(movieName);
						break;
					} else {

						System.out.println("\n===== !!! Invalid Input !!! =====\n");
					}
				}

				break;

			case "3":
				Map<String, Double> userInterestMovie = new LinkedHashMap<>(
						userServices.recommendedMovie(MovieRecommendationSystem.getUsername()));
				count = 1;
				String more = "";
				System.out.println("\n***** MOVIES MAY YOU LIKE *****\n");
				System.out.println("\tId  MovieName\n________________________________");
				for (Map.Entry<String, Double> map : userInterestMovie.entrySet()) {

					if (count < 11) {
						// System.out.print("\t"+map.getValue());
						System.out.println("\t" + count++ + ".  " + map.getKey());

					} else if (count == 11) {
						System.out.println("\n\tNext --> (Press (Y/N) to show more...)");
						more = sc.nextLine();
					}

					if (more.equalsIgnoreCase("n") && count == 11)
						break;
					if (more.equalsIgnoreCase("y")) {
						// System.out.print("\t"+map.getValue());
						System.out.println("\t" + count++ + ".  " + map.getKey());
					}
				}

				while (true) {

					System.out.println("\nEnter movie name (𝓟𝓻𝓮𝓼𝓼 0 𝓯𝓸𝓻 𝓔𝔁𝓲𝓽)");
					movieName = sc.nextLine();
					if (movieName.equals("0")) {
						break;
					}
					List<MovieModel> recommendedMovieData = new ArrayList<>(
							adminService.getMoviesBySearch(movieName, "movieName"));
					SearchMovie.displayMovieHelper(recommendedMovieData);
					MovieOptions.movieOptions(movieName);
				}

				break;

			case "4":
				SearchMovie.movieSearchOperation("user");
				break;

			case "5":
				UserHistory.userHistory();
				break;

			case "6":
				Watchlist.watchListFunctinality();
				break;

			case "7":
				showProfile();
				break;
			case "8":
				System.out.println("Return to Login Section.....");
				break;

			default:
				System.out.println("\n===== !!! Invalid Input !!! =====\n");
				break;
			}

		} while (!userChoice.equals("8"));
	}
}
