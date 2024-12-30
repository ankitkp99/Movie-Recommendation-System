package com.app.repository;
import java.util.List;

import com.app.model.MovieModel;
import com.app.model.UserModel;
public interface AdminRepository {

	public boolean isRegister(UserModel userModel);
	
	public List<String> getNameByLogin(String username, String password);
	
	public List<String> getUserEmail();
	
	public List<String> getUserContact();
	
	public List<String> getUsername();
	
	public boolean isAddMovie(MovieModel model);
	
	public List<MovieModel> getAllMovies();
	
//	public List<MovieModel> getMoviesByName(String movieName);
//	
//	public List<MovieModel> getMoviesByYear(String movieYear);
//	
//	public List<MovieModel> getMoviesByGenre(String movieGenre);
//	
//	public List<MovieModel> getMoviesByDirector(String director);
//	
//	public List<MovieModel> getMoviesByActor(String actor);
//	
//	public List<MovieModel> getMoviesByActress(String actress);
	
	public List<MovieModel> getMoviesBySearch(String searchData, String searchType);
	
	public boolean isDeleteMovie(String movieName, String movieYear);
	
	public List<UserModel> getAllUserData();
	
	public boolean isBlockedUser(String username);
	
	public List<UserModel> getAllBlockedUser();
	
	public boolean isUnblockUser(String username);
	
	public List<String> getBlockedMovie();
	
	public boolean isUnblockMovie(String MovieName);
}
