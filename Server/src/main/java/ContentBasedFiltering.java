import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

public class ContentBasedFiltering {
	public static String user = "2";
	public static List<String> genres = new ArrayList<String>();
	public static List<String> movieIds = new ArrayList<String>();
	
	public static void contentBasedFiltering() {
		String serviceEndPoint = "http://ec2-18-205-117-22.compute-1.amazonaws.com:3030/movies";
		String queryString = "\r\n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
				+ "PREFIX movieDataset: <http://ec2-18-205-117-22.compute-1.amazonaws.com:3030/movies/>\r\n"
				+ "PREFIX rating: <http://ec2-52-205-254-172.compute-1.amazonaws.com:3030/rating/>\r\n"
				+ "SELECT ?movieId ?movieName ?genres\r\n"
				+ "WHERE {\r\n"
				+ "  SERVICE rating:sparql\r\n"
				+ "  {\r\n"
				+ "    ?subject <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-53#userid> 2 .\r\n"
				+ "    ?subject <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-53#has_rated> ?obj .\r\n"
				+ "    ?obj <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-53#rating> ?rating.\r\n"
				+ "    FILTER(?rating > 3.5)\r\n"
				+ "    ?obj <http://www.semanticweb.org/ontologies/2021/10/untitled-ontology-53#movieid> ?movieId .\r\n"
				+ "  }\r\n"
				+ "  SERVICE movieDataset:sparql\r\n"
				+ "  {\r\n"
				+ "    ?s <http://www.semanticweb.org/iti/ontologies/2021/10/untitled-ontology-17#movie_id> ?movieId.\r\n"
				+ "    ?s <http://www.semanticweb.org/iti/ontologies/2021/10/untitled-ontology-17#genres> ?genres.\r\n"
				+ "    ?s <http://www.semanticweb.org/iti/ontologies/2021/10/untitled-ontology-17#original_title> ?movieName\r\n"
				+ "  }\r\n"
				+ "}\r\n"
				+ "ORDER BY(?movieId)\r\n"
				+ "LIMIT 25";

		giveTop3Genre(queryString, serviceEndPoint);

		getTop3GenreMoviesNotWatchedByUser();

	}

	private static void getTop3GenreMoviesNotWatchedByUser() {

		String serviceEndPoint = "http://ec2-18-205-117-22.compute-1.amazonaws.com:3030/movies";
		String moviesWatcedByUser = "";
		String top3Genres = "";
		for (String movieId : movieIds) {
			moviesWatcedByUser = moviesWatcedByUser + "," + movieId;
		}

		moviesWatcedByUser = moviesWatcedByUser.substring(1);
		for (int i = 0; i < genres.size(); i++) {
			if (i == 0)
				top3Genres = genres.get(i);
			else
				top3Genres = top3Genres + "|" + genres.get(i);
		}
		top3Genres = "\"" + top3Genres + "\"";
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "prefix owl: <http://www.w3.org/2002/07/owl#>\r\n" + "\r\n"
				+ "SELECT ?movieId ?movieTitle ?genres\r\n" + "WHERE {\r\n"
				+ "  ?movies <http://www.semanticweb.org/iti/ontologies/2021/10/untitled-ontology-17#genres> ?genres.\r\n"
				+ "  FILTER regex(?genres," + top3Genres + ",\"i\") .\r\n"
				+ "  ?movies <http://www.semanticweb.org/iti/ontologies/2021/10/untitled-ontology-17#movie_id> ?movieId.\r\n"
				+ "  FILTER (?movieId NOT IN (" + moviesWatcedByUser + "))\r\n"
				+ "  ?movies <http://www.semanticweb.org/iti/ontologies/2021/10/untitled-ontology-17#original_title> ?movieTitle.\r\n"
				+ "}\r\n" + "LIMIT 25";
		loadTest(query, serviceEndPoint);
	}

	public static void giveTop3Genre(String query, String serviceEndPoint) {

		Pattern pattern = Pattern.compile("'[a-zA-Z]*'}");

		QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceEndPoint, query);

		ResultSet results = qexec.execSelect();
		List<String> movieId = new ArrayList<String>();
		Map<String, Integer> genreFreq = new HashMap<>();
		List<QuerySolution> li = ResultSetFormatter.toList(results);
		// System.out.println( ResultSetFormatter.asText(results)
		for (QuerySolution querySolution : li) {
			movieId.add(querySolution.get("?movieId").asNode().getLiteralValue().toString());
			String str = querySolution.get("?genres").asNode().getLiteralValue().toString();
			Matcher matcher = pattern.matcher(str);
			while (matcher.find()) {
				String genre = matcher.group().substring(1, matcher.group().length() - 2);
				int count = genreFreq.getOrDefault(genre, 0);
				genreFreq.put(genre, count + 1);
			}
		}

		Map<String, Integer> sortedMap = genreFreq.entrySet().stream().sorted(Entry.comparingByValue())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

//		sortedMap.entrySet().forEach(entry -> {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		});

		int startAccumulating = sortedMap.size() - 3;
		int i = 0;

		List<String> top3Genres = new ArrayList<>();

		for (Entry<String, Integer> genresToCount : sortedMap.entrySet()) {
			if (i++ < startAccumulating)
				continue;
			top3Genres.add(genresToCount.getKey());
		}
		genres = top3Genres;
		movieIds = movieId;
	}

	public static void loadTest(String query, String serviceEndPoint) {

		QueryExecution qexec = QueryExecutionFactory.sparqlService(serviceEndPoint, query);

		ResultSet results = qexec.execSelect();
		ResultSetFormatter.out(System.out, results);
		// System.out.println( ResultSetFormatter.asText(results)

		while (results.hasNext()) {
			System.out.println(results.next().toString());
		}

	}

}
