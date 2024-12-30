package com.app.services;
import com.app.repository.*;

import java.util.List;

import com.app.model.MovieModel;
import com.app.model.UserModel;

public class AdminServicesImpl implements AdminServices {
	AdminRepository adminRepo = new AdminRepositoryImpl();
	AdminMovieUpdateRepository movieRepo = new AdminMovieUpdateRepositoryImpl();
	
	@Override
	public boolean isRegister(UserModel userModel) {
		// TODO Auto-generated method stub
		return adminRepo.isRegister(userModel);
	}
	@Override
	public List<String>getNameByLogin(String username, String password) {
		// TODO Auto-generated method stub
		return adminRepo.getNameByLogin(username, password);
	}
	@Override
	public List<String> getUserEmail() {
		// TODO Auto-generated method stub
		return adminRepo.getUserEmail();
	}
	@Override
	public List<String> getUserContact() {
		// TODO Auto-generated method stub
		return adminRepo.getUserContact();
	}
	@Override
	public List<String> getUsername() {
		// TODO Auto-generated method stub
		return adminRepo.getUsername();
	}
	@Override
	public boolean isAddMovie(MovieModel model) {
		return adminRepo.isAddMovie(model);
	}
	@Override
	public List<MovieModel> getAllMovies() {
		// TODO Auto-generated method stub
		return adminRepo.getAllMovies();
	}
//	@Override
//	public List<MovieModel> getMoviesByName(String movieName) {
//		// TODO Auto-generated method stub
//		return adminRepo.getMoviesByName(movieName);
//	}
//	@Override
//	public List<MovieModel> getMoviesByYear(String movieYear) {
//		// TODO Auto-generated method stub
//		return adminRepo.getMoviesByYear(movieYear);
//	}
//	@Override
//	public List<MovieModel> getMoviesByGenre(String movieGenre) {
//		// TODO Auto-generated method stub
//		return adminRepo.getMoviesByGenre(movieGenre);
//	}
//	@Override
//	public List<MovieModel> getMoviesByDirector(String director) {
//		// TODO Auto-generated method stub
//		return adminRepo.getMoviesByDirector(director);
//	}
//	@Override
//	public List<MovieModel> getMoviesByActor(String actor) {
//		// TODO Auto-generated method stub
//		return adminRepo.getMoviesByActor(actor);
//	}
//	@Override
//	public List<MovieModel> getMoviesByActress(String actress) {
//		// TODO Auto-generated method stub
//		return adminRepo.getMoviesByActress(actress);
//	}
	@Override
	public List<MovieModel> getMoviesBySearch(String searchData, String searchType) {
		// TODO Auto-generated method stub
		return adminRepo.getMoviesBySearch(searchData, searchType);
	}
	@Override
	public boolean isDeleteMovie(String movieName, String movieYear) {
		// TODO Auto-generated method stub
		return adminRepo.isDeleteMovie(movieName, movieYear);
	}
	@Override
	public List<UserModel> getAllUserData() {
		// TODO Auto-generated method stub
		return adminRepo.getAllUserData();
	}
	@Override
	public boolean isBlockedUser(String username) {
		// TODO Auto-generated method stub
		return adminRepo.isBlockedUser(username);
	}
	@Override
	public List<UserModel> getAllBlockedUser() {
		// TODO Auto-generated method stub
		return adminRepo.getAllBlockedUser();
	}
	@Override
	public boolean isUnblockUser(String username) {
		// TODO Auto-generated method stub
		return adminRepo.isUnblockUser(username);
	}
	@Override
	public boolean isUpdateMovie(String movieName, String newMovieData, String movieDataType) {
		// TODO Auto-generated method stub
		return movieRepo.isUpdateMovie(movieName, newMovieData, movieDataType);
	}
	@Override
	public List<String> getBlockedMovie() {
		// TODO Auto-generated method stub
		return adminRepo.getBlockedMovie();
	}
	@Override
	public boolean isUnblockMovie(String MovieName) {
		// TODO Auto-generated method stub
		return adminRepo.isUnblockMovie(MovieName);
	}
}
