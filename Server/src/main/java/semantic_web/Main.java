package semantic_web;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@EnableAutoConfiguration
@SpringBootApplication
public class Main {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);

		Services services = new Services();
//		services.getMovieDetails("100");
//		services.getPopularMovies();
		//System.out.println(services.getMovieRecommendationsFromOtherUsers(2));
//		List<Movie> result=services.getMovieRecommendationsFromOtherUsers(services.getMovieRecommendationsFromOtherUsers("2"));
//		for(Movie omovi:result){
//			System.out.println(omovi);
//		}
		
		services.getRecommendationByUsersRating("1");
    }

}
