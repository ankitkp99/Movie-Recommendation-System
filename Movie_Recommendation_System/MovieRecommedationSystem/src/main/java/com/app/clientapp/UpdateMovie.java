package com.app.clientapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.model.MovieModel;
import com.app.services.AdminServices;
import com.app.services.AdminServicesImpl;

public class UpdateMovie {
	static Scanner sc = ScannerClass.getScanner();
	static boolean flag = false;
	
	static void displayMoviesList() {
		AdminServices adminService = new AdminServicesImpl();
		int count = 1;
		List<MovieModel> movieData = new ArrayList<>(adminService.getAllMovies());
		if (!movieData.isEmpty()) {
			System.out.println("\n  ***** All Movies List *****\n");
			for (MovieModel model : movieData) {
				System.out.println("\t" + count++ + ".  " + model.getMovieName());
			}
			flag=true;
		} else {
			flag = false;
			System.out.println("\n===== !!! No Movie Available !!! =====\n");
		}
	}

	
	static void movieUpdateOperationHelper(String movieDataType, String title)
	{
		AdminServices adminService = new AdminServicesImpl();
		displayMoviesList();
		if(flag) {
			System.out.println("\nEnter Movie Name");
			String movieName = sc.nextLine();
			List<MovieModel>movieData = new ArrayList<>(adminService.getMoviesBySearch(movieName, "movieName"));
			SearchMovie.displayMovieHelper(movieData);
			if(!movieData.isEmpty())
			{
				System.out.println("\nEnter New Movie "+ title);
				String newMovieData = sc.nextLine();
				
				if(adminService.isUpdateMovie(movieName, newMovieData, movieDataType))
				{
					System.out.println("\n***** Movie Updated Successfully *****\n"); 
					movieData = new ArrayList<>(adminService.getMoviesBySearch(movieName, "movieName"));
					SearchMovie.displayMovieHelper(movieData);
				}
				else {
					System.out.println("\n=====!!! Something Went Wrong !!!=====\n");
				}
			}
		}
	}
	static void updateMovie()
	{
		AdminServices adminService = new AdminServicesImpl();
		String movieChoice="";
		do
		{
			System.out.println("------------------------------");
			System.out.println("=======  Update Movie  =======");
			System.out.println("------------------------------");
			System.out.println("\n1:Update Movie Name\n2:Update Movie Year\n3:Update Movie Duration\n4:Update Movie Rating"
					+ "\n5:Update Movie Description\n6:Previous Menu\n7:Logout\nEnter your choice");
			movieChoice = sc.nextLine();
			switch(movieChoice)
			{
			case "1":
				displayMoviesList();
				if(flag) {
					System.out.println("\nEnter Movie Name");
					String movieName = sc.nextLine();
					List<MovieModel>movieData = new ArrayList<>(adminService.getMoviesBySearch(movieName, "movieName"));
					SearchMovie.displayMovieHelper(movieData);
					if(!movieData.isEmpty())
					{
						System.out.println("\nEnter New Movie Name");
						String newMovieName = sc.nextLine();
						if(adminService.isUpdateMovie(movieName, newMovieName, "movieName"))
						{
							System.out.println("\n***** Movie Name Updated Successfully *****\n"); 
							movieData = new ArrayList<>(adminService.getMoviesBySearch(newMovieName, "movieName"));
							SearchMovie.displayMovieHelper(movieData);
						}
						else {
							System.out.println("\n=====!!! Something Went Wrong !!!=====\n");
						}
					}
				}
				break;
						
			case "2":
				displayMoviesList();
				if(flag) {
					System.out.println("\nEnter Movie Name");
					String movieName = sc.nextLine();
					List<MovieModel>movieData = new ArrayList<>(adminService.getMoviesBySearch(movieName, "movieName"));
					SearchMovie.displayMovieHelper(movieData);
					if(!movieData.isEmpty())
					{
						System.out.println("\nEnter New Movie Year");
						String newMovieData = sc.nextLine();
						if(newMovieData.length()==4)
						{
							if(adminService.isUpdateMovie(movieName, newMovieData, "movieYear"))
							{
								System.out.println("\n***** Movie Updated Successfully *****\n"); 
								movieData = new ArrayList<>(adminService.getMoviesBySearch(movieName, "movieName"));
								SearchMovie.displayMovieHelper(movieData);
							}
							else {
								System.out.println("\n=====!!! Something Went Wrong !!!=====\n");
							}
						}
						else
						{
							System.out.println("\n=====!!! Invalid Movie Year !!!=====\n");
						}
					}
				}
				break;
				
			case "3":
				movieUpdateOperationHelper("movieDuration","Duration");
				break;
				
			case "4":
				try {
					displayMoviesList();
					if(flag) {
						System.out.println("\nEnter Movie Name (𝓟𝓻𝓮𝓼𝓼 0 𝓯𝓸𝓻 𝓔𝔁𝓲𝓽)");
						String movieName = sc.nextLine();
						if(movieName.equals("0"))
							break;
						List<MovieModel>movieData = new ArrayList<>(adminService.getMoviesBySearch(movieName, "movieName"));
						SearchMovie.displayMovieHelper(movieData);
						if(!movieData.isEmpty())
						{
							System.out.println("\nEnter New IMDb Movie Rating");
							String newMovieData = sc.nextLine();
							double rating = Double.parseDouble(newMovieData);
							if(rating <= 10 && rating > 0)
							{
								if(adminService.isUpdateMovie(movieName, newMovieData, "movieRating"))
								{
									System.out.println("\n***** Movie Updated Successfully *****\n"); 
									movieData = new ArrayList<>(adminService.getMoviesBySearch(movieName, "movieName"));
									SearchMovie.displayMovieHelper(movieData);
								}
								else {
									System.out.println("\n=====!!! Something Went Wrong !!!=====\n");
								}
							}
							else
							{
								System.out.println("\n=====!!! Rating should be out of 10 !!!=====\n");
							}
						}
					}
					break;
				}
				catch(Exception e)
				{
					System.out.println("\n=====!!! Rating should be out of 10 !!!=====\n");
				}
				
				
			case "5":
				movieUpdateOperationHelper("movieDescription","Description");
				break;
				
			case "6":
				break;
				
			case "7":
				MovieRecommendationSystem.callMain();
				break;
				
				default:
					System.out.println("\n===== !!! Invalid Input !!! =====\n");
					break;
			}
		}while(!movieChoice.equals("6"));
	}
}
