package com.app.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.model.MovieModel;
import com.app.model.UserModel;

public class UserRepositoryImpl extends DBConnection implements UserRepository {

	AdminRepositoryImpl adminRepoImplObj = new AdminRepositoryImpl();

	@Override
	public List<MovieModel> getTrendingMovies() {

		String trendingMovieQuery = "select m.*, y.releaseyear from movies m join movieyear y on y.yearid = m.yearid"
				+ " where y.yearid = (select yearid from movieyear where releaseyear=(select max(releaseyear) from movieyear)) "
				+ "and m.moviestatus = 1 order by m.rating desc limit 10;";

		List<MovieModel> movieData = adminRepoImplObj.getAllMoviesHelper(trendingMovieQuery);
		return movieData;
	}

	@Override
	public boolean isMovieAddInHistory(String username, String movieName) {
		try {

			pstmt = conn.prepareStatement("insert into userhistory (userid, movieid)"
					+ " select (select userid from user where username = ?), (select movieid from movies where moviename = ?) "
					+ "where not exists (select 1 from userhistory where userid = (select userid from user where username = ?) "
					+ "and movieid = (select movieid from movies where moviename = ?));");

			pstmt.setString(1, username);
			pstmt.setString(2, movieName);
			pstmt.setString(3, username);
			pstmt.setString(4, movieName);
			int result = pstmt.executeUpdate();

			return result == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean isAddMovieInWatchList(String username, String movieName) {
		try {
			pstmt = conn.prepareStatement("insert into watchlist (userid, movieid) select "
					+ "(select userid from user where username = ?), (select movieid from movies where moviename = ?) "
					+ "where not exists (select 1 from watchlist where userid = (select userid from user where username = ?) "
					+ "and movieid = (select movieid from movies where moviename = ?));");

			pstmt.setString(1, username);
			pstmt.setString(2, movieName);
			pstmt.setString(3, username);
			pstmt.setString(4, movieName);

			int result = pstmt.executeUpdate();

			return result == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean hasMovieRated(String username, String movieName, String rating) {
		try {
			pstmt = conn.prepareStatement(" update userhistory set rating = ? where userid = "
					+ "(select userid from user where username = ?) and "
					+ "movieid =(select movieid from movies where moviename = ?);");

			pstmt.setString(1, rating);
			pstmt.setString(2, username);
			pstmt.setString(3, movieName);

			int result = pstmt.executeUpdate();

			return result == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Map<List<String>, String> getUserHistory(String username) {
		Map<List<String>, String> userHistory = new LinkedHashMap<>();
		try {
			pstmt = conn.prepareStatement(
					" select m.moviename,date(h.watchedtime),h.rating from movies m join userhistory h on "
							+ "h.movieid= m.movieid where h.userid = (select userid from user where username = ?);");
			pstmt.setString(1, username);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				List<String> watchTime = new ArrayList<String>();
				watchTime.add(rs.getString(3));
				watchTime.add(rs.getString(1));

				userHistory.put(watchTime, rs.getString(2));

				// System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));
			}
			return userHistory;
		} catch (SQLException e) {
			e.printStackTrace();
			return userHistory;
		}
	}

	public boolean isDeleteDataHelper(String query, String username, String movieName) {
		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, username);
			pstmt.setString(2, movieName);

			int result = pstmt.executeUpdate();

			return result >= 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isDeleteAllDataHelper(String query, String username) {
		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, username);
			int result = pstmt.executeUpdate();

			return result >= 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isDeleteHistory(String username, String movieName) {
		String query = "delete from userhistory where userid = (select userid from user where username = ?) "
				+ "and movieid = (select movieid from movies where moviename = ?);";

		return isDeleteDataHelper(query, username, movieName);
	}

	@Override
	public boolean isDeleteAllHistory(String username) {
		String query = "delete from userhistory where userid = (select userid from user where username = ?);";

		return this.isDeleteAllDataHelper(query, username);
	}

	@Override
	public List<String> getUserWatchlist(String username) {
		List<String> watchList = new ArrayList<>();
		try {
			pstmt = conn
					.prepareStatement(" select m.moviename from user u join watchlist l on l.userid = u.userid join "
							+ "movies m on l.movieid = m.movieid where u.userid=(select userid from user where username = ?)");
			pstmt.setString(1, username);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				watchList.add(rs.getString(1));
			}
			return watchList;
		} catch (SQLException e) {
			e.printStackTrace();
			return watchList;
		}
	}

	@Override
	public boolean isDeleteWatchlist(String username, String movieName) {
		String query = "  delete from watchlist where userid = (select userid from user where username = ?) "
				+ "and movieid = (select movieid from movies where moviename = ?);";
		return isDeleteDataHelper(query, username, movieName);
	}

	@Override
	public boolean isDeleteAllWatchlist(String username) {
		String query = "delete from watchlist where userid = (select userid from user where username = ?);";
		return this.isDeleteAllDataHelper(query, username);
	}

	@Override
	public UserModel getProfile(String userName) {
		UserModel profile = null;
		try {

			pstmt = conn.prepareStatement("select * from user where username = ?");
			pstmt.setString(1, userName);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				profile = new UserModel(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6),"", rs.getString(9));
			}
			return profile;
		} catch (SQLException e) {
			e.printStackTrace();
			return profile;
		}
	}

	public boolean isUpdateProfile(String username, String Query, String newData) {
		try {
			pstmt = conn.prepareStatement(Query);
			pstmt.setString(1, newData);
			pstmt.setString(2, username);

			int result = pstmt.executeUpdate();
			return result == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isUpdateProfileByName(String newName, String userName) {

		String nameQuery = " update user set name = ? where username = ?";

		if (isUpdateProfile(userName, nameQuery, newName)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isUpadateProfileEmail(String newEmail, String userName) {
		String emailQuery = " update user set email = ? where username = ?";
		if (isUpdateProfile(userName, emailQuery, newEmail)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isUpadateProfileContact(String newContact, String userName) {
		String contactQuery = " update user set contact = ? where username = ?";
		if (isUpdateProfile(userName, contactQuery, newContact)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isUpadatePassword(String newPass, String userName) {
		String contactQuery = " update user set password = ? where username = ?";
		if (isUpdateProfile(userName, contactQuery, newPass)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String,Integer> getAllGenres() {
		Map<String,Integer> genres = new LinkedHashMap<>();
		try {
			
			pstmt = conn.prepareStatement("Select genrename from genres");
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				genres.put(rs.getString(1),0);
			}
			
			return genres;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return genres;
		}
		
	}

	@Override
	public List<String> getMovieGenreByUserHistory(String username) {
		 List<String> userHistoryGenres = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(" select g.genrename from genres g join moviegenrejoin mgj on g.genreid = mgj.genreid "
					+ "join movies m on m.movieid = mgj.movieid join userhistory h on h.movieid = m.movieid "
					+ "join user u on u.userid = h.userid where u.username = ?;");
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				userHistoryGenres.add(rs.getString(1));
			}
			return userHistoryGenres;
		}catch(SQLException e) {
			e.printStackTrace();
			return userHistoryGenres;
		}
	}
}
