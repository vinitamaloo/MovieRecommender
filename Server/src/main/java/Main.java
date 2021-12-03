import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Main {
	public static String serviceEndPoint = "http://localhost:3030/cast/query"; 
	public static String user= "2";

	public static void main(String[] args) {
//		System.setProperty("java.net.preferIPv4Stack", "true");
//		SpringApplication.run(Main.class, args);
		Services services = new Services();
		services.getMovieDetails("100");
		services.getPopularMovies();
    	ContentBasedFiltering.contentBasedFiltering();;
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
