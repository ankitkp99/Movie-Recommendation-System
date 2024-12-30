package com.app.services;

import java.util.List;
import java.util.Map;

import com.app.model.MovieModel;
import com.app.model.UserModel;
import com.app.repository.UserRepository;
import com.app.repository.UserRepositoryImpl;

public class UserServicesImpl implements UserServices {

	UserRepository userRepo = new UserRepositoryImpl();
	@Override
	public List<MovieModel> getTrendingMovies() {
		// TODO Auto-generated method stub
		return userRepo.getTrendingMovies();
	}
	@Override
	public boolean isMovieAddInHistory(String username, String movieName) {
		// TODO Auto-generated method stub
		return userRepo.isMovieAddInHistory(username, movieName);
	}
	@Override
	public boolean isAddMovieInWatchList(String username, String movieName) {
		// TODO Auto-generated method stub
		return userRepo.isAddMovieInWatchList(username, movieName);
	}
	@Override
	public boolean hasMovieRated(String username, String movieName, String rating) {
		// TODO Auto-generated method stub
		return userRepo.hasMovieRated(username, movieName, rating);
	}
	@Override
	public Map<List<String>,String> getUserHistory(String username) {
		// TODO Auto-generated method stub
		return userRepo.getUserHistory(username);
	}
	@Override
	public boolean isDeleteHistory(String username, String movieName) {
		// TODO Auto-generated method stub
		return userRepo.isDeleteHistory(username, movieName);
	}
	@Override
	public boolean isDeleteAllHistory(String username) {
		// TODO Auto-generated method stub
		return userRepo.isDeleteAllHistory(username);
	}
	@Override
	public List<String> getUserWatchlist(String username) {
		// TODO Auto-generated method stub
		return userRepo.getUserWatchlist(username);
	}
	@Override
	public boolean isDeleteWatchlist(String username, String movieName) {
		// TODO Auto-generated method stub
		return userRepo.isDeleteWatchlist(username, movieName);
	}
	@Override
	public boolean isDeleteAllWatchlist(String username) {
		// TODO Auto-generated method stub
		return userRepo.isDeleteAllWatchlist(username);
	}
	@Override
	public UserModel getProfile(String userName) {
		// TODO Auto-generated method stub
		return userRepo.getProfile(userName);
	}
	@Override
	public boolean isUpdateProfileByName(String newName, String userName) {
		// TODO Auto-generated method stub
		return userRepo.isUpdateProfileByName(newName, userName);
	}
	@Override
	public boolean isUpadateProfileEmail(String newName, String username) {
		// TODO Auto-generated method stub
		return userRepo.isUpadateProfileEmail(newName, username);
	}
	@Override
	public boolean isUpadateProfileContact(String newContact, String userName) {
		// TODO Auto-generated method stub
		return userRepo.isUpadateProfileContact(newContact, userName);
	}
	@Override
	public boolean isUpadatePassword(String newPass, String userName) {
		// TODO Auto-generated method stub
		return userRepo.isUpadatePassword(newPass, userName);
	}
	@Override
	public Map<String, Double> recommendedMovie(String username) {
		// TODO Auto-generated method stub
		return CollaborativeFilteringMRSAlgo.mvoieRecommendationAlgo(username);
	}
	@Override
	public Map<String, Integer> getAllGenres() {
		// TODO Auto-generated method stub
		return userRepo.getAllGenres();
	}
}
