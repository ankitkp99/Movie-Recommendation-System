package com.app.repository;

import java.sql.SQLException;

public class AdminMovieUpdateRepositoryImpl extends DBConnection implements AdminMovieUpdateRepository {

	boolean updateMovieName(String query, String oldMovieName, String newMovieData)
	{
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newMovieData);
			pstmt.setString(2, oldMovieName);
			int result = pstmt.executeUpdate();
			return result == 1 ? true:false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean isUpdateMovie(String movieName, String newMovieData, String movieDataType) {

		switch(movieDataType)
		{
		case "movieName":
			String query="update movies set moviename = ? where moviename = ?";
			return updateMovieName(query,movieName,newMovieData);
	
		case "movieYear":
			/*
			 *  create procedure updatemovieyear(myear varchar(4),mname varchar(100)) begin 
			 *  insert ignore into movieyear(releaseyear) values(myear);  
			 *  update movies set yearid = (select yearid from movieyear where releaseyear = myear) 
			 *  where moviename= mname; end//
			 */
			
			try {
				pstmt = conn.prepareCall("{call updatemovieyear(?,?)}");
				pstmt.setString(1, newMovieData);
				pstmt.setString(2, movieName);
				int result = pstmt.executeUpdate();
				return result>=1?true:false;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
		case "movieRating":
			query = "update movies set rating = ? where moviename = ?";
			return updateMovieName(query,movieName,newMovieData);
			
		case "movieDuration":
			query = "update movies set duration = ? where moviename = ?";
			return updateMovieName(query,movieName,newMovieData);
					
		case "movieDescription":
			query = "update movies set moviedescrip = ? where moviename = ?";
			return updateMovieName(query,movieName,newMovieData);
		}
		return false;
	}
	

}
