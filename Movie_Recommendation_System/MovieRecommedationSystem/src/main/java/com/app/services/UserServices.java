package com.app.services;

import java.util.List;
import java.util.Map;

import com.app.model.MovieModel;
import com.app.model.UserModel;

public interface UserServices {

	public List<MovieModel> getTrendingMovies();

	public boolean isMovieAddInHistory(String username, String movieName);

	public boolean isAddMovieInWatchList(String username, String movieName);

	public boolean hasMovieRated(String username, String movieName, String rating);

	public Map<List<String>, String> getUserHistory(String username);

	public boolean isDeleteHistory(String username, String movieName);

	public boolean isDeleteAllHistory(String username);
	
	public List<String> getUserWatchlist(String username);

	public boolean isDeleteWatchlist(String username, String movieName);

	public boolean isDeleteAllWatchlist(String username);
	
	public UserModel getProfile(String userName);
	
	public boolean isUpdateProfileByName(String newName, String userName);
	
	public boolean isUpadateProfileEmail(String newName, String username);
	
	public boolean isUpadateProfileContact(String newContact, String userName);
	
	public boolean isUpadatePassword(String newPass, String userName);
	
	public Map<String,Integer> getAllGenres();
	
	public Map<String, Double> recommendedMovie(String username);
}
