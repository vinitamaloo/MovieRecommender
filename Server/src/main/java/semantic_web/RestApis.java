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

    @PostMapping("/get_popular_movies")
    public List<Movie> getPopularMovies(@RequestBody Filter filter) throws IOException {
        return services.getPopularMovies();
    }

    @PostMapping("/get_recommendation_by_user/{user_id}")
    public List<Movie> getRecommendationByUser(@PathVariable String user_id) throws IOException {
        return services.getMovieRecommendationsFromOtherUsers(services.getMovieRecommendationsFromOtherUsers(user_id));
    }

    @PostMapping("/get_movie_details/{movie_id}")
    public Movie getMovieDetails(@PathVariable String movie_id) throws IOException {
        return services.getMovieDetails(movie_id);
    }

    

}