package com.app.model;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MovieModel {
	
	private String movieName;
	private String duration;
	private Set<String> genres;
	private Set<String> language;
	private String year;
	private String director;
	private String actor;
	private String actress;
	private String description;
	private String rating;
}
