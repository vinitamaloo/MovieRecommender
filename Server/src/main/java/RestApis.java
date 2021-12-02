import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController("restApis")
@RequestMapping("/server")
public class RestApis {

    @Autowired
    private Services services;

    @PostMapping("/get_popular_movies")
    public List<Movie> getPopularMovies(@RequestBody Filter filter) {
        List<Movie> movies = services.getPopularMovies();
        return movies;
    }

    @PostMapping("/get_recommendation_by_user")
    public List<Movie> getRecommendationByUser(@RequestBody Filter filter) {
        return new ArrayList<>();
    }

    @PostMapping("/get_movie_details/{movie_id}")
    public List<Movie> getMovieDetails(@PathVariable String movie_id) {
        List<Movie> movies = services.getMovieDetails(movie_id);
        return movies;
    }

}