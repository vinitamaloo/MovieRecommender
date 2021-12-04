package semantic_web;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@EnableAutoConfiguration
@SpringBootApplication
public class Main {
	public static void main(String[] args) throws Exception {
				SpringApplication.run(Main.class, args);

		Services services = new Services();
		System.out.println(services.getMovieDetails("100"));
		services.getPopularMovies();
		System.out.println(services.getMovieRecommendationsFromOtherUsers("4"));
		for(String as:services.getMovieRecommendationsFromOtherUsers("4"))
		    System.out.println(services.getMovieDetails(as));
		List<Movie> ans=services.getMovieRecommendationsFromOtherUsers(services.getMovieRecommendationsFromOtherUsers("2"));
		System.out.println(ans.size());
		services.getRecommendationByUsersRating("1");
    }

}
