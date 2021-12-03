import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@EnableAutoConfiguration
public class Main {
	public static String serviceEndPoint = "http://localhost:3030/cast/query"; 
	public static String user= "2";

	public static void main(String[] args) throws IOException {
//		System.setProperty("java.net.preferIPv4Stack", "true");
//		SpringApplication.run(Main.class, args);
		Services services = new Services();
		System.out.println("Movie details");
		services.getMovieDetails("100");
		System.out.println("###");
		services.getPopularMovies();
		services.getMovieRecommendationsFromOtherUsers(services.getMovieRecommendationsFromOtherUsers(2));
    	ContentBasedFiltering.contentBasedFiltering();
    }

	public static void loadTest(String query) {
		
		 QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceEndPoint,query);	
		 
		 ResultSet results = qexec.execSelect();
		 ResultSetFormatter.out(System.out,results);
		 
		 while(results.hasNext()) {
			 System.out.println(results.next().toString());
		 }
	}
}
