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
	public static String serviceEndPoint = "http://localhost:3030/rating/query"; 
	public static String user= "2";

    public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		SpringApplication.run(Main.class, args);

    	contentBasedFiltering();
		System.out.println("\n\n\n\nCCCCOOOOOOLLLLLLABBBBBBBBBBBBBBBBB\n\n\n");
		collborativeFiltering();
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

	public static void collborativeFiltering() 
    {
    	String query = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
		+"\nPREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
		+"\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
		+"\nPREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
		+"\nPREFIX use: <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-53#>"
		+"\nSelect DISTINCT ?movieid3 ?userid2"
		+"\n	WHERE{"
		+"\n	  ?user rdf:type use:User."
		+"\n	  ?user use:userid ?userid2."
		+"\n	  ?user use:has_rated ?rated."
		+"\n	  ?rated use:movieid ?movieid3."
		+"\n	  ?rated use:rating ?rating"
		+"\n	  {Select ?userid ?movieid ?userid3"
			+"\n		  WHERE {"
				+"\n	  ?user rdf:type use:User."
				+"\n  ?user use:userid ?userid."
				+"\n  ?user use:has_rated ?rated."
				+"\n  ?rated use:movieid ?movieid2."
				+"\n  ?rated use:rating ?rating"
				+"\n  {SELECT ?movieid ?movieid ?userid3"
					+"\n	WHERE {"
						+"\n	?user rdf:type use:User."
						+"\n	?user use:userid ?userid3."
						+"\n	?user use:has_rated ?rated."
						+"\n	?rated use:movieid ?movieid."
						+"\n	?rated use:rating ?rating"
						+"\n	FILTER(?userid3 = 2 && ?rating = 5 )."
						+"\n	}"
						+"\n	LIMIT 3"
						+"\n	  }"
						+"\n	FILTER (?movieid2 IN (?movieid) && ?userid != 2 && ?rating = 5)."
						+"\n  }"
						+"\n  LIMIT 5"
						+"\n  }"
		  
						+"\nFILTER (?userid2 IN (?userid)  && ?movieid3 NOT IN (?movieid) && ?rating = 5)."
		  
						+"\n}"
						+"\nLIMIT 100";
       	collborativeFilteringSupport(query);
       	 
    }

	public static List<Integer> collborativeFilteringSupport(String query){
		QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceEndPoint,query);	
		 ResultSet results = qexec.execSelect();
		String s= ResultSetFormatter.asText(results);
//		System.out.println(s.substring(69,92));
//		System.out.println(s.substring(71,74));
//		System.out.println(s.substring(82,85));
//		System.out.println(s);
		int left=71;
		int leftOfId=82;
		String key=s.substring(82,85);
		HashMap<String,List<Integer>> eachUserWithTheir3Recommendations=new HashMap<>();
		while(left<s.length()-50)
		{
			int right=0;
	            right=leftOfId+getNumberOfDigits(leftOfId,s);
	           // System.out.print(s.substring(leftOfId,right));
				key=s.substring(leftOfId,right);
				leftOfId+=23;
				right=left+getNumberOfDigits(left,s);
				//System.out.println(" <--------    "+s.substring(left, right));
				List<Integer> temp=eachUserWithTheir3Recommendations.getOrDefault(key, new ArrayList<>());
				temp.add(Integer.valueOf(s.substring(left, right)));
				eachUserWithTheir3Recommendations.put(key,temp);
				left+=23;
		}
		System.out.println(eachUserWithTheir3Recommendations.keySet());
		List<Integer> finalRecommendations=new ArrayList<>();
		for(String keyIter:eachUserWithTheir3Recommendations.keySet()) {
			List<Integer> movieRecommendations=eachUserWithTheir3Recommendations.get(keyIter);
			for(int i=0;i<movieRecommendations.size()||i<3;i++) {
				finalRecommendations.add(movieRecommendations.get(i));
			}
		}
		return finalRecommendations;
}
	
    
	private static int getNumberOfDigits(int left, String s) {
		int count=0;
		while(Character.isDigit(s.charAt(left))) {
			++count;
			++left;
		}
		return count;
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
