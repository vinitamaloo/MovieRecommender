import { Link } from "react-router-dom";
import { Card} from "react-bootstrap";
import "./match-card.css";

export default function MatchCard( {title, language, picture, subtitle, rating}) {
    return(
        <Card className="card">
            <Card.Body>
                <Card.Title className="title">{title}</Card.Title>
                <img src={picture} className="image"/>
                <Card.Text> {rating} stars </Card.Text>
                <Card.Text> {language} </Card.Text>
                <Card.Subtitle className="mb-2 text-muted">{subtitle}</Card.Subtitle>
                <div className="cardLink">
                    <Card.Link><Link to={"/movie-details"}>View More</Link></Card.Link>
                </div>
            </Card.Body>
        </Card>
    )
}