package com.app.repository;

import java.util.*;

import com.app.model.MovieModel;
import com.app.model.UserModel;

public interface UserRepository {

	public List<MovieModel> getTrendingMovies();
	
	public boolean isMovieAddInHistory(String username, String movieName);
	
	public boolean isAddMovieInWatchList(String username, String movieName);
	
	public boolean hasMovieRated(String username, String movieName, String rating);
	
	public Map<List<String>,String> getUserHistory(String username);
	
	public boolean isDeleteHistory(String username, String movieName);
	
	public boolean isDeleteAllHistory(String username);
	
	public List<String> getUserWatchlist(String username);
	
	public boolean isDeleteWatchlist(String username, String movieName);
	
	public boolean isDeleteAllWatchlist(String username);
	
	public UserModel getProfile(String userName);
	
	public boolean isUpdateProfileByName(String newName, String userName);
	
	public boolean isUpadateProfileEmail(String newEmail, String userName);
	
	public boolean isUpadateProfileContact(String newContact, String userName);
	
	public boolean isUpadatePassword(String newPass, String userName);
	
	public Map<String,Integer> getAllGenres();
	
	public  List<String> getMovieGenreByUserHistory(String username);
	
}
