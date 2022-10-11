package de.egosanto.ms.moviecatalog.resources;


import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import de.egosanto.ms.moviecatalog.models.CatalogItem;
import de.egosanto.ms.moviecatalog.models.Movie;
import de.egosanto.ms.moviecatalog.models.Rating;
import de.egosanto.ms.moviecatalog.models.UserRating;

/**
 * 
 * @author Toni Zeidler
 *
 */
@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	// like json in postman do Rest call
	@Autowired
	private RestTemplate restTemplate; 
	
	@Autowired
	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog (@PathVariable("userId") String userId) {

		// get all rated movie IDs
		// old method
		/*
		List<Rating> ratings = Arrays.asList(
			new Rating ("1234", 4),
			new Rating ("5678", 3)
		);
		*/
		UserRating ratings = restTemplate.getForObject(
				"http://localhost:8083/rating/users/"+ userId, 
				UserRating.class);
		
		// iterating Array
		return ratings.getUserRating().stream().map( rating -> { 
			
			// ! deprecated method to call REST call
			// for each movie ID, call movie info service and get details
			// calling MovieInfo MService
			Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);
		
			// put them all together
			return new CatalogItem(movie.getName(), "Movie Description", rating.getRating());
		})
		.collect(Collectors.toList());
		
		
	}
}

	//get an Instance of Movie (async Object ) Method of promise 
	/*Movie movie = webClientBuilder.build()
		.get()
		.uri("http://localhost:8081/movies/" + rating.getMovieId())
		.retrieve()
		.bodyToMono(Movie.class)
		.block();
	*/
