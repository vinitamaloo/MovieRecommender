import { Link } from "react-router-dom";
import { Card} from "react-bootstrap";
import "./match-card.css";

export default function MatchCard( {title, language, picture, subtitle, rating, movieId}) {
    function truncate(str, n){
      return (str.length > n) ? str.substr(0, n-1) : str;
    }

    return(
        <Card className="card">
            <Card.Body>
                <Card.Title className="title">{title}</Card.Title>
                <img src={picture} className="image"/>
                <h6> Rating: {rating} </h6>
                <h6> Language: {language} </h6>
                <Card.Subtitle className="mb-2 text-muted">{truncate(subtitle)}</Card.Subtitle>
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