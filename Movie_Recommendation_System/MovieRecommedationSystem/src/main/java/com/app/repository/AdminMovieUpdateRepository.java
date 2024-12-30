package com.app.repository;

public interface AdminMovieUpdateRepository {

	public boolean isUpdateMovie(String movieName, String newMovieData, String movieDataType);
}
