import { Link } from "react-router-dom";
import { Card} from "react-bootstrap";
import "./match-card.css";

export default function MatchCard( {title, language, picture, subtitle, rating, movieId}) {
    return(
        <Card className="card">
            <Card.Body>
                <Card.Title className="title">{title}</Card.Title>
                <img src={picture} className="image"/>
                <h6> Rating: {rating} </h6>
                <h6> Language: {language} </h6>
                <Card.Subtitle className="mb-2 text-muted">{subtitle}</Card.Subtitle>
                <div className="cardLink">
                    <Card.Link>
                        <Link to={
                            {
                                pathname: "/movie-details",
                                state: {title:title},
                            }}>View More</Link>
                    </Card.Link>
                </div>
            </Card.Body>
        </Card>
    )
}