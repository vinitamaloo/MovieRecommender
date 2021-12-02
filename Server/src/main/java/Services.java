import org.apache.jena.query.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Services {

    public static String serviceEndPoint = "http://localhost:3030/rating/query";

    public List<Movie> getMovieDetails(String movieId)
    {
        String queryString = "\n PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                +"\n PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                +"\n PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                +"\n PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                +"\n PREFIX ds1: <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-52#>"
                +"\n PREFIX ds2: <http://www.semanticweb.org/iti/ontologies/2021/10/untitled-ontology-17#>"
                +"\n SELECT ?movie_id ?cast_name ?cast_character ?original_title ?overview ?release_date ?genre"
                +"\n WHERE {"
                +"\n ?movies rdf:type ds2:Movies."
                +"\n ?movies ds2:movie_id ?movie_id."
                +"\n ?movies ds2:original_title ?original_title."
                +"\n ?movies ds2:genres ?genre."
                +"\n ?movies ds2:overview ?overview."
                +"\n ?movies ds2:release_date ?release_date."
                    +"\n {"
                        +"\n SELECT ?movie_id ?cast_name ?cast_character"
                        + "\n WHERE {"
                        +"\n ?movie rdf:type ds1:Movie."
                        +"\n ?movie ds1:movieid ?movie_id."
                        +"\n ?movie ds1:has_cast ?cast."
                        +"\n ?cast ds1:cast_name ?cast_name."
                        +"\n ?cast ds1:cast_character ?cast_character."
                        + "\n FILTER(?movie_id = "+movieId+")."
                        +"\n }"
                    +"\n }"
                + "\n FILTER(?movie_id = "+movieId+")."
                +"\n }";

        QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceEndPoint, queryString);

        ResultSet results = qexec.execSelect();
       // ResultSetFormatter.out(System.out, results);
        //ResultSetFormatter.outputAsJSON();
        List<QuerySolution> solutions = ResultSetFormatter.toList(results);
        for(QuerySolution sol : solutions) {
            System.out.println(sol.toString());
        }

        return null;
    }
}
