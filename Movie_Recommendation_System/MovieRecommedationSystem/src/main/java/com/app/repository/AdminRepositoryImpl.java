package com.app.repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.app.model.MovieModel;
import com.app.model.UserModel;

public class AdminRepositoryImpl extends DBConnection implements AdminRepository {

	@Override
	public boolean isRegister(UserModel userModel) {
		try
		{
			pstmt = conn.prepareStatement("Insert into user(name,email,contact,username,password,usertype) values(?,?,?,?,?,?)");
			pstmt.setString(1, userModel.getName());
			pstmt.setString(2, userModel.getEmail());
			pstmt.setString(3, userModel.getContact());
			pstmt.setString(4, userModel.getUsername());
			pstmt.setString(5, userModel.getPassword());
			pstmt.setString(6, userModel.getType());
			
			int result = pstmt.executeUpdate();
			return result==1?true:false;
		}catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<String> getNameByLogin(String username, String password) {
		List<String> loginInfo = new ArrayList<String>();
		try
		{
			pstmt = conn.prepareStatement("select name,usertype from user where username = ? and password = ? and userstatus = 1");
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				loginInfo.add(rs.getString(1));
				loginInfo.add(rs.getString(2));
				
			}
			return loginInfo;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return loginInfo;
		}
	}
	
	@Override
	public List<String> getUserEmail() {
		List<String> emails = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement("select email from user");
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				emails.add(rs.getString(1));
			}
			return emails;
		} catch (SQLException e) {
			e.printStackTrace();
			return emails;
		}
		
	}

	@Override
	public List<String> getUserContact() {
		List<String> contacts = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement("select contact from user");
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				contacts.add(rs.getString(1));
			}
			return contacts;
		} catch (SQLException e) {
			e.printStackTrace();
			return contacts;
		}
	}

	@Override
	public List<String> getUsername() {
		List<String> usernames = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement("select username from user");
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				usernames.add(rs.getString(1));
			}
			return usernames;
		} catch (SQLException e) {
			e.printStackTrace();
			return usernames;
		}
	}
	
	@Override
	public boolean isAddMovie(MovieModel model) {

		try {

			/*
			 *create procedure addmoviepro(moviename varchar(100), duration varchar(100), myear varchar(100), director varchar(100),
			 *actor varchar(100), actress varchar(100), moviedescrip text, rating decimal(3,1)) begin insert ignore into movieyear
			 *(releaseyear) values (myear); insert into movies (moviename, duration, rating, moviedescrip, yearid) values 
			 *(moviename, duration, rating, moviedescrip, (select yearid from movieyear where releaseyear = myear limit 1)); 
			 *insert into moviecast (castname, casttype) select director, 'director' where not exists (select 1 from moviecast 
			 *where castname = director and casttype = 'director'); insert into moviecastjoin (movieid, castid) values 
			 *((select max(movieid) from movies limit 1), (select castid from moviecast where castname = director and 
			 *casttype = 'director')); insert into moviecast (castname, casttype) select actor, 'actor' where not exists 
			 *(select 1 from moviecast where castname = actor and casttype = 'actor'); 
			 *insert into moviecastjoin (movieid, castid) values ((select max(movieid) from movies limit 1), 
			 *(select castid from moviecast where castname = actor and casttype = 'actor')); 
			 *insert into moviecast (castname, casttype) select actress, 'actress' where not exists 
			 *(select 1 from moviecast where castname = actress and casttype = 'actress'); 
			 *insert into moviecastjoin (movieid, castid) values ((select max(movieid) from movies limit 1), 
			 *(select castid from moviecast where castname = actress and casttype = 'actress')); end;
			 */
			pstmt = conn.prepareCall("{call addmoviepro(?,?,?,?,?,?,?,?)}");
			pstmt.setString(1, model.getMovieName());
			pstmt.setString(2, model.getDuration());
			pstmt.setString(3, model.getYear());
			pstmt.setString(4, model.getDirector());
			pstmt.setString(5, model.getActor());
			pstmt.setString(6, model.getActress());
			pstmt.setString(7, model.getDescription());
			pstmt.setString(8, model.getRating());

			conn.setAutoCommit(false);
			int result = pstmt.executeUpdate();

			if (result == 1) {
				Set<String> genreList = new HashSet<>(model.getGenres());
				pstmt = conn.prepareCall("{call addgenrepro(?)}");
				for (String str : genreList) {
					pstmt.setString(1, str);
					pstmt.addBatch();
				}
				int values[] = pstmt.executeBatch();
				boolean flag = true;

				for (int i : values) {
					if (i == Statement.EXECUTE_FAILED) {
						flag = false;
						break;
					}
				}

				/*
				 * create procedure addmovielang(langname varchar(50)) begin insert ignore into
				 * language(language) values(langname); insert into movielangjoin
				 * (movieid,langid) values((select max(movieid) from movies limit 1), (select
				 * langid from language where language = langname));
				 */

				Set<String> langList = new HashSet<>(model.getLanguage());
				pstmt = conn.prepareCall("{call addmovielang(?)}");
				for (String str : langList) {
					pstmt.setString(1, str);
					pstmt.addBatch();
				}

				int lang[] = pstmt.executeBatch();
				boolean flag1 = true;

				for (int i : lang) {
					if (i == Statement.EXECUTE_FAILED) {
						flag1 = false;
						break;
					}
				}

				if (flag && flag1) {
					conn.commit();
					return true;
				} else {
					conn.rollback();
					return false;
				}
			} else {
				conn.rollback();
				return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}

		List<MovieModel> getAllMoviesHelper(String getAllMovieQuery){
			
		List<MovieModel> movieData = new ArrayList<>();
		Set<String> genre;
		Set<String> lang;
		try {
			pstmt = conn.prepareStatement(getAllMovieQuery);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				genre = new HashSet<>();
				int movieId = rs.getInt(1);
				pstmt = conn.prepareStatement(
						" select g.genrename from genres g join moviegenrejoin mgj on g.genreid = mgj.genreid join"
								+ " movies m on m.movieid = mgj.movieid where m.movieid = ?");
				pstmt.setInt(1, movieId);
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					genre.add(rs1.getString(1));
				}

				lang = new HashSet<>();
				pstmt = conn.prepareStatement(
						" select l.language from language l join movielangjoin mlj on l.langid = mlj.langid join"
								+ " movies m on m.movieid = mlj.movieid where m.movieid = ?");
				pstmt.setInt(1, movieId);
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					lang.add(rs1.getString(1));
				}

				String castName[] = new String[3];
				int count = 0;
				pstmt = conn.prepareStatement(
						" select mc.castname from moviecast mc join moviecastjoin mj on mj.castid = mc.castid join movies m"
								+ " on mj.movieid =m.movieid where m.movieid = ?");
				pstmt.setInt(1, movieId);
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					castName[count++] = rs1.getString(1);
				}

				movieData.add(new MovieModel(rs.getString(2), rs.getString(4), genre, lang, rs.getString(9),
						castName[0], castName[1], castName[2], rs.getString(5), rs.getString(3)));
			}
			return movieData;
		} catch (SQLException e) {
			e.printStackTrace();
			return movieData;
		}
	}
	@Override
	public List<MovieModel> getAllMovies() {
		String getAllMovieQuery ="select m.*, y.releaseyear from movies m join movieyear y on m.yearid = y.yearid "
				+ "where m.moviestatus =1 ";
		List<MovieModel> movieData = this.getAllMoviesHelper(getAllMovieQuery);
		return movieData;
	}

	//@Override
	public List<MovieModel> getMoviesByName(String movieName) {
		List<MovieModel> moviesList = new ArrayList<>(this.getAllMovies());
		boolean flag = false;
		try {
			
			Iterator<MovieModel> iterator = moviesList.iterator();
			while(iterator.hasNext())
			{
				MovieModel model = iterator.next();
				if(!model.getMovieName().equalsIgnoreCase(movieName)) {
					iterator.remove();
					flag = true;
				}
			}
			if(flag)
			{
				return moviesList;
			}
			else {
				moviesList.clear();
				return moviesList;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			moviesList.clear();
			return moviesList;
		}
		
	}

	
	//@Override
	public List<MovieModel> getMoviesByGenre(String movieGenre) {
		
		List<MovieModel> moviesList = new ArrayList<>(this.getAllMovies());
		
		boolean flag = false;
		try {
			
			Iterator<MovieModel> iterator = moviesList.iterator();
			while(iterator.hasNext())
			{
				MovieModel model = iterator.next();
				List<String> genres = new ArrayList<>(model.getGenres());
				flag = false;
				for(String str:genres)
				{
					if(str.equalsIgnoreCase(movieGenre)) {
						
						flag = true;
					}
				}
				if(!flag)
				{
					iterator.remove();
				}
			}
			return moviesList;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			moviesList.clear();
			return moviesList;
		}
	}

	//@Override
	public List<MovieModel> getMoviesByYear(String movieYear) {
		List<MovieModel> moviesList = new ArrayList<>(this.getAllMovies());
		try {
			
			Iterator<MovieModel> iterator = moviesList.iterator();
			while(iterator.hasNext())
			{
				MovieModel model = iterator.next();
				if(!model.getYear().equalsIgnoreCase(movieYear)) {
					iterator.remove();
				}
			}
			return moviesList;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			moviesList.clear();
			return moviesList;
		}
	}

	
	//@Override
	public List<MovieModel> getMoviesByDirector(String director) {
		List<MovieModel> moviesList = new ArrayList<>(this.getAllMovies());
		boolean flag = false;
		try {
			
			Iterator<MovieModel> iterator = moviesList.iterator();
			while(iterator.hasNext())
			{
				MovieModel model = iterator.next();
				if(!model.getDirector().equalsIgnoreCase(director)) {
					iterator.remove();
					flag = true;
				}
			}
			if(flag)
			{
				return moviesList;
			}
			else {
				moviesList.clear();
				return moviesList;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			moviesList.clear();
			return moviesList;
		}
	}
	
	//@Override
	public List<MovieModel> getMoviesByActor(String actor) {
		List<MovieModel> moviesList = new ArrayList<>(this.getAllMovies());
		boolean flag = false;
		try {
			
			Iterator<MovieModel> iterator = moviesList.iterator();
			while(iterator.hasNext())
			{
				MovieModel model = iterator.next();
				if(!model.getActor().equalsIgnoreCase(actor)) {
					iterator.remove();
					flag = true;
				}
			}
			if(flag)
			{
				return moviesList;
			}
			else {
				moviesList.clear();
				return moviesList;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			moviesList.clear();
			return moviesList;
		}
	}

	//@Override
	public List<MovieModel> getMoviesByActress(String actress) {
		List<MovieModel> moviesList = new ArrayList<>(this.getAllMovies());
		boolean flag = false;
		try {
			
			Iterator<MovieModel> iterator = moviesList.iterator();
			while(iterator.hasNext())
			{
				MovieModel model = iterator.next();
				if(!model.getActress().equalsIgnoreCase(actress)) {
					iterator.remove();
					flag = true;
				}
			}
			if(flag)
			{
				return moviesList;
			}
			else {
				moviesList.clear();
				return moviesList;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			moviesList.clear();
			return moviesList;
		}
	}

	@Override
	public List<MovieModel> getMoviesBySearch(String searchData,String searchType) {

		switch(searchType)
		{
		case "movieName":
				return this.getMoviesByName(searchData);
	
		case "movieGenre":
				return this.getMoviesByGenre(searchData);
			
		case "movieYear":
				return this.getMoviesByYear(searchData);
			
		case "director":
				return this.getMoviesByDirector(searchData);
			
		case "actor":
				return this.getMoviesByActor(searchData);
			
		case "actress":
				return this.getMoviesByActress(searchData);
				
		}
		return null;
	}

	@Override
	public boolean isDeleteMovie(String movieName, String movieYear) {
		try {
			
			pstmt = conn.prepareStatement("update movies m join movieyear y on y.yearid = m.yearid "
					+ "set m.moviestatus = 0 where m.moviename = ? and y.releaseyear =?;");
			pstmt.setString(1, movieName);
			pstmt.setString(2, movieYear);
			
			int result = pstmt.executeUpdate();
			
			return result == 1?true:false;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}	
	}

	public List<UserModel> getAllUserDataHelper(String query)
	{
		List<UserModel> userData = new ArrayList<>();
		try
		{
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				userData.add(new UserModel(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),"",rs.getString(7),
						rs.getString(9)));
			}
			return userData;
		}catch(SQLException e)
		{
			e.printStackTrace();
			return userData;
		}
	}
	
	@Override
	public List<UserModel> getAllUserData() {
		String query ="select * from user where usertype != 'Admin' and userstatus = 1";
		return getAllUserDataHelper(query);
	}

	@Override
	public boolean isBlockedUser(String username) {
		try {
			pstmt = conn.prepareStatement("update user set userstatus = 0 where username = ?");
			pstmt.setString(1, username);
			int result = pstmt.executeUpdate();
			return result==1?true:false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<UserModel> getAllBlockedUser() {
		String query ="select * from user where usertype != 'Admin' and userstatus = 0";
		return getAllUserDataHelper(query);
	}

	@Override
	public boolean isUnblockUser(String username) {
		try {
			pstmt = conn.prepareStatement("update user set userstatus = 1 where username = ?");
			pstmt.setString(1, username);
			int result = pstmt.executeUpdate();
			return result==1?true:false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<String> getBlockedMovie() {
		List<String> blockedMovies = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement("select moviename from movies where moviestatus = 0");
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				blockedMovies.add(rs.getString(1));
			}
			return blockedMovies;
		} catch (SQLException e) {
			e.printStackTrace();
			return blockedMovies;
		}
	}

	@Override
	public boolean isUnblockMovie(String MovieName) {
		try {
			pstmt = conn.prepareStatement("update movies set moviestatus = 1 where moviename = ? ");
			pstmt.setString(1, MovieName);
			
			int result = pstmt.executeUpdate();
			return result >= 1?true:false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
