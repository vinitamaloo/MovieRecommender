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
        List<Integer> movieIds = services.getMovieRecommendationsFromOtherUsers(user_id);
        System.out.println(movieIds);
        if (movieIds == null) {
            System.out.println("movie ids null");
            return null;
        }

        List<Movie> movies = services.getMovieRecommendationsFromOtherUsers2(movieIds);

        if (movies == null) {
            System.out.println("movies null");
            return null;
        }

        for (Movie mov : movies) {
            System.out.println(mov);
        }
        return movies;
    }

    @GetMapping("/get_movie_details/{movie_id}")
    public Movie getMovieDetails(@PathVariable String movie_id) throws Exception {
        return services.getMovieDetails(movie_id);
    }
    
    //It will return all the movies rated by user greater than 3.5 and among these movies give top 25 movies 
    //of most watched genres
    @GetMapping("/get_recommendation_by_Users_rating/{user_id}")
    public List<Movie> getRecommendationByUsersRating(@PathVariable String user_id) throws Exception {
        System.out.println("****getRecommendationByUsersRating****");
        return services.getRecommendationByUsersRating(user_id);
    }
}