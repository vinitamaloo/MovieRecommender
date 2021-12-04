import { Link } from "react-router-dom";
import { Card} from "react-bootstrap";
import "./match-card.css";

export default function MatchCard( {title, language, picture, subtitle, rating, movieId}) {
    function truncate(str, n){
      return str;
    }

    function final_lang(language){
      return language == null ? 'en' : language;
    }

    function final_rating(rating){
        return rating == null ? '7.5' : rating;
    }

    return(
        <Card className="card">
            <Card.Body>
                <Card.Title className="title">{title}</Card.Title>
                <img src={picture} className="image"/>
                <h6> Rating: {final_rating(rating)} </h6>
                <h6> Language: {final_lang(language)} </h6>
                <Card.Subtitle className="mb-2 text-muted">{truncate(subtitle, 50)}</Card.Subtitle>
                <div className="cardLink">
                    <Card.Link>
                        <Link to={
                            {
                                pathname: "/movie-details",
                                state: {movie_id:movieId},
                            }}>View More</Link>
                    </Card.Link>
                </div>
            </Card.Body>
        </Card>
    )
}