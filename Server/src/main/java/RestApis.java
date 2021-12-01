import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController("restApis")
@RequestMapping("/server")
public class RestApis {

    @PostMapping("/get_popular_movies")
    public List<Movie> getPopularMovies(@RequestBody Filter filter) {
        return new ArrayList<>();
    }

    @PostMapping("/get_recommendation_by_user")
    public List<Movie> getRecommendationByUser(@RequestBody Filter filter) {
        return new ArrayList<>();
    }

}