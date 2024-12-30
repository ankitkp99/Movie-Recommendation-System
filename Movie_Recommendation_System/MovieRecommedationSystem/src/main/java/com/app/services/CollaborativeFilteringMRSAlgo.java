package com.app.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.clientapp.MovieRecommendationSystem;
import com.app.model.MovieModel;
import com.app.repository.AdminRepository;
import com.app.repository.AdminRepositoryImpl;
import com.app.repository.UserRepository;
import com.app.repository.UserRepositoryImpl;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

public class CollaborativeFilteringMRSAlgo {

	static Map<String, Double> mvoieRecommendationAlgo(String username) {
		username = MovieRecommendationSystem.getUsername();

		UserRepository userRepo = new UserRepositoryImpl();
		AdminRepository adminRepo = new AdminRepositoryImpl();
		
		Map<String, Integer> genres = new LinkedHashMap<>(userRepo.getAllGenres());
		Map<String, Integer> genresDuplicate = new LinkedHashMap<>(genres);
		Map<String, Double> movieRecommended = new LinkedHashMap<>();// final probability value with moviename

		List<String> userHistoryGenres = new ArrayList<>(userRepo.getMovieGenreByUserHistory(username));

		for (String genreName : userHistoryGenres) {
			if (genres.containsKey(genreName)) {
				genres.put(genreName, genres.get(genreName) + 1);
			}
		}

		List<Integer> userPreference = new ArrayList<>(genres.values());

		double userVector = 0;
		for (Integer i : userPreference) {
			userVector += i * i;
		}

		userVector = Math.sqrt(userVector);

		List<MovieModel> movieModel = adminRepo.getAllMovies();

		for (MovieModel model : movieModel) {
			Set<String> movieGenre = new LinkedHashSet<String>();
			for (String movieGen : model.getGenres()) {
				movieGenre.add(movieGen);
			}

			Map<String, Integer> allMovieGenres = new LinkedHashMap<>(genresDuplicate);

			for (String genreName : movieGenre) {
				if (allMovieGenres.containsKey(genreName)) {
					allMovieGenres.put(genreName, allMovieGenres.get(genreName) + 1);
				}
			}

			List<Integer> moviePreference = new ArrayList<>(allMovieGenres.values());

			double movieVector = 0;
			for (Integer i : moviePreference) {
				movieVector += i * i;
			}

			movieVector = Math.sqrt(movieVector);

			double dotProduct = 0;

			for (int i = 0; i < moviePreference.size(); i++) {
				dotProduct += userPreference.get(i) * moviePreference.get(i);
			}

			double movieProbability = dotProduct / (userVector * movieVector);

			movieRecommended.put(model.getMovieName(), movieProbability);
		}

		List<Map.Entry<String, Double>> movieNameSort = new ArrayList<>(movieRecommended.entrySet());// map object
																										// stored in
																										// list

		movieNameSort.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // sorting by descending
																								// map object by value
																								// in list
		//Map<String, Double> sortedRecommendedMovie = new LinkedHashMap<>();
		movieRecommended.clear();
		for (Map.Entry<String, Double> map : movieNameSort) {

			movieRecommended.put(map.getKey(), map.getValue());
		}
		
		return movieRecommended;
	}
}
