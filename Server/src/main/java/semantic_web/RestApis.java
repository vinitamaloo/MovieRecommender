package semantic_web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController("restApis")
@RequestMapping("/server")
public class RestApis {

    @Autowired
    private Services services;

    @GetMapping("/get_popular_movies")
    public List<Movie> getPopularMovies() throws Exception {
        return services.getPopularMovies();
    }

    @GetMapping("/get_recommendation_based_on_other_users/{user_id}")
    public List<Movie> getRecommendationByUser(@PathVariable String user_id) throws Exception {
        return services.getMovieRecommendationsFromOtherUsers(services.getMovieRecommendationsFromOtherUsers(user_id));
    }

    @GetMapping("/get_movie_details/{movie_id}")
    public Movie getMovieDetails(@PathVariable String movie_id) throws Exception {
        return services.getMovieDetails(movie_id);
    }
}