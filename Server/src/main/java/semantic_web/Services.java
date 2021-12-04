package semantic_web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.query.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class Services {

    public static String serviceEndPoint = "http://ec2-18-205-117-22.compute-1.amazonaws.com:3030/movies";
    public static String temp_serviceEndPoint = "http://ec2-52-205-254-172.compute-1.amazonaws.com:3030/rating";

    public List<Movie> getMovieRecommendationsFromOtherUsers(List<String> movieId) throws IOException {
        if(movieId.size()==0)
        return null;
        String movieIdString=createMyCustomQuery(movieId);
		List<Movie> result=new ArrayList<>();
		for(String iter:movieId){
			result.add(getMovieDetails(iter));
		}
        return result;
    }
    private String createMyCustomQuery(List<String> movieId) {
        String queryAddition=movieId.get(0);
        if(movieId.size()==1)
        return queryAddition;
        int i=1;
        while(i<movieId.size()){
            queryAddition+=" || ?movie_id = "+movieId.get(i);
        }
        return queryAddition;
    }
    public List<String> getMovieRecommendationsFromOtherUsers(String userid){
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
						+"\n	FILTER(?userid3 = "+userid+" && ?rating = 5 )."
						+"\n	}"
						+"\n	LIMIT 3"
						+"\n	  }"
						+"\n	FILTER (?movieid2 IN (?movieid) && ?userid != 2 && ?rating = 5)."
						+"\n  }"
						+"\n  LIMIT 3"
						+"\n  }"
		  
						+"\nFILTER (?userid2 IN (?userid)  && ?movieid3 NOT IN (?movieid) && ?rating = 5)."
		  
						+"\n}"
						+"\nLIMIT 100";
       return getMovieRecommendationsFromOtherUsersSupport(query);
       	 
    }

	public List<String> getMovieRecommendationsFromOtherUsersSupport(String query){
		QueryExecution qexec = QueryExecutionFactory.sparqlService(temp_serviceEndPoint,query);	
		 ResultSet results = qexec.execSelect();
		String s= ResultSetFormatter.asText(results);
	//	System.out.println(s.substring(0,24));
	//System.out.println(s.substring(74,77));
	//	System.out.println(s.substring(85,88));
//		System.out.println(s);
		int left=74;
		int leftOfId=85;
		String key=s.substring(82,85);
		HashMap<String,List<String>> eachUserWithTheir3Recommendations=new HashMap<>();
		while(left<s.length()-50)
		{
			int right=0;
	            right=leftOfId+getNumberOfDigits(leftOfId,s);
	            //System.out.print(s.substring(leftOfId,right));
				key=s.substring(leftOfId,right);
				leftOfId+=24;
				right=left+getNumberOfDigits(left,s);
				//System.out.println(" <--------    "+s.substring(left, right));
				List<String> temp=eachUserWithTheir3Recommendations.getOrDefault(key, new ArrayList<>());
                temp.add(s.substring(left, right));
				eachUserWithTheir3Recommendations.put(key,temp);
				left+=24;
		}
		//System.out.println(eachUserWithTheir3Recommendations.keySet());
		List<String> finalRecommendations=new ArrayList<>();
		for(String keyIter:eachUserWithTheir3Recommendations.keySet()) {
			List<String> movieRecommendations=eachUserWithTheir3Recommendations.get(keyIter);
			for(int i=0;i<movieRecommendations.size()&&i<1;i++) {
				finalRecommendations.add(movieRecommendations.get(i));
			}
		}
		return finalRecommendations;
}
	
    
	private int getNumberOfDigits(int left, String s) {
		int count=0;
		while(Character.isDigit(s.charAt(left))) {
			++count;
			++left;
		}
		return count;
	}


    public Movie getMovieDetails(String movieId) throws IOException {
        String queryString = "\n PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                +"\n PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                +"\n PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                +"\n PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                +"\n PREFIX ds1: <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-52#>"
                +"\n PREFIX ds2: <http://www.semanticweb.org/iti/ontologies/2021/10/untitled-ontology-17#>"
                +"\n PREFIX movie: <http://ec2-18-205-117-22.compute-1.amazonaws.com:3030/movies/>"
                +"\n PREFIX cast: <http://ec2-34-207-70-20.compute-1.amazonaws.com:3030/cast/>"
                +"\n SELECT ?movie_id ?cast_name ?cast_character ?original_title ?overview ?release_date ?genre"
				+"\n ?vote_average ?original_language"
                +"\n WHERE {"    
                +"\n SERVICE movie:sparql {"
                +"\n ?movies rdf:type ds2:Movies."
                +"\n ?movies ds2:movie_id ?movie_id."
                +"\n ?movies ds2:original_title ?original_title."
                +"\n ?movies ds2:genres ?genre."
                +"\n ?movies ds2:overview ?overview."
                +"\n ?movies ds2:vote_average ?vote_average."
                +"\n ?movies ds2:release_date ?release_date."
                +"\n ?movies ds2:original_language ?original_language."
                + "\n FILTER(?movie_id = "+movieId+")."
                    +"\n }"            
                    +"\n SERVICE cast:sparql {"
                        +"\n ?movie rdf:type ds1:semantic_web.Movie."
                        +"\n ?movie ds1:movieid ?movie_id."
                        +"\n ?movie ds1:has_cast ?cast."
                        +"\n ?cast ds1:cast_name ?cast_name."
                        +"\n ?cast ds1:cast_character ?cast_character."
                        + "\n FILTER(?movie_id = "+movieId+")."
                    +"\n }"
                    
                +"\n }";


        QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceEndPoint, queryString);
        ResultSet results = qexec.execSelect();

		Movie movie = new Movie();
		List<Cast> casts = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
        List<QuerySolution> solutions = ResultSetFormatter.toList(results);
		boolean dataUnset = true;
        for(QuerySolution sol : solutions) {
			if (dataUnset) {
				movie.setMovie_id(sol.getLiteral("movie_id").getInt());

				String genre = sol.getLiteral("genre").toString();
				genre = genre.replace('\'', '\"');
				movie.setGenre(mapper.readValue(genre, new TypeReference<List<Genre>>(){}));
				movie.setOriginal_title(sol.getLiteral("original_title").toString());
				movie.setOverview(sol.getLiteral("overview").toString());
				movie.setRelease_date(sol.getLiteral("release_date").getInt());
				movie.setVote_average(sol.getLiteral("vote_average").getDouble());
				movie.setOriginal_language(sol.getLiteral("original_language").toString());
			}

			Cast cast = new Cast(sol.getLiteral("cast_name").toString(),
					          sol.getLiteral("cast_character").toString());
			casts.add(cast);

			dataUnset = false;
        }

		movie.setCast(casts);
		System.out.println(movie.toString());
        return movie;
    }

    public List<Movie> getPopularMovies()
    {
        String queryString = "\n PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                +"\n PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                +"\n PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                +"\n PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                +"\n PREFIX movie: <http://www.semanticweb.org/iti/ontologies/2021/10/untitled-ontology-17#>"
                +"\n PREFIX mo: <http://purl.org/ontology/mo/>"
                +"\n PREFIX ds1: <http://ec2-18-205-117-22.compute-1.amazonaws.com:3030/movies/>"
                +"\n SELECT ?title ?id ?overview ?language ?popularity"
                +"\n WHERE {"
                +"\n SERVICE ds1:sparql {"
                +"\n ?movie rdf:type movie:Movies."
                +"\n ?movie movie:movie_id ?id."
                +"\n ?movie movie:original_title ?title."
                +"\n ?movie movie:overview ?overview."
                +"\n ?movie movie:original_language ?language."
                +"\n ?movie movie:release_date ?release_date."
                +"\n ?movie movie:popularity ?popularity."
                    +"\n }"
                    +"\n }"
                        +"\n ORDER BY DESC (?release_date) (?popularity)"
                        + "\n LIMIT 3";

        QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceEndPoint, queryString);

        ResultSet results = qexec.execSelect();
        List<QuerySolution> solutions = ResultSetFormatter.toList(results);
        for(QuerySolution sol : solutions) {
            System.out.println(sol.toString());
        }
        return null;
    }
}
