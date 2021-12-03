import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		System.out.println(services.getMovieRecommendationsFromOtherUsers(2));
    	contentBasedFiltering();
		System.out.println("\n\n\n\nCCCCOOOOOOLLLLLLABBBBBBBBBBBBBBBBB\n\n\n");
    }

    public static void contentBasedFiltering() 
    {
    	String queryString = "\n PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
       		 +"\n PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
       			+"\n PREFIX owl: <http://www.w3.org/2002/07/owl#>"
       			+"\n PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
       			+"\n SELECT  ?movieId"
       			+ "\n WHERE {"
       			  +"\n ?subject <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-53#userid> " + user + "."
       			  + "\n ?subject <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-53#has_rated> ?obj . "
       			  + "\n ?obj <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-53#rating> ?rating."
       			  + "\n FILTER(?rating > 3.5)"
       			  + "\n ?obj <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-53#movieid> ?movieId ."
       			+"\n }";
       	
       	loadTest(queryString);  
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
