import { Link } from "react-router-dom";
import React, { useState } from "react";
import { Row, Col, Card, Container} from "react-bootstrap";
import {get_popular_movies, get_recommendation_by_user, get_recommendation_based_on_other_users } from "../api/services";
import "./homepage.css";
import MatchCard from "./match-card";
import img from '../Images/780-87-101P.jpg';
import img1 from '../Images/780-169-101P.jpg';
import img2 from '../Images/804cf78487857.560be6ea0a71c.png';
import img3 from '../Images/2469c8298ee384d2e24bdf8199dd3fa9.jpg';
import img4 from '../Images/Geometric-Illusion-JUNIQE-Poster.jpg';
import img5 from '../Images/HQ_1998090.jpg';
import img6 from '../Images/lionking-minimalist-movie-poster-prints.jpg';

export default function Homepage() {
    const [userId, setUserId] = useState(localStorage.getItem('userId'));

    async function getDataFromServer() {
        const user = {
          "user_id": userId
        }

        const x = await get_popular_movies();
        const y = await get_recommendation_by_user(user);
        const z = await get_recommendation_based_on_other_users(user);
    }

    return(
        <div style={{ marginTop: 60 }}>
            <Container>
                <div className='heading'>
                    <h3>Based on popularity</h3>
                </div>
                <Row>
                    <Col>
                        <MatchCard title="Movie 1" language="English"
                         picture={img}
                         subtitle="Movie is outstanding"
                         rating='4.5'/>
                    </Col>
                    <Col>
                        <MatchCard title="Movie 2" language="English"
                         picture={img1}
                         subtitle="Movie is outstanding"
                         rating='4.5'/>
                    </Col>
                    <Col>
                       <MatchCard title="Movie 3" language="English"
                         picture={img2}
                         subtitle="Movie is outstanding"
                         rating='4.5'/>
                    </Col>
                </Row>

                 <div className='heading'>
                    <h3>Based on your likes</h3>
                 </div>

                 <Row>
                    <Col>
                        <MatchCard title="Movie 1" language="English"
                         picture={img3}
                         subtitle="Movie is outstanding"
                         rating='4.5'/>
                    </Col>
                    <Col>
                        <MatchCard title="Movie 3" language="English"
                         picture={img4}
                         subtitle="Movie is outstanding"
                         rating='4.5'/>
                    </Col>
                    <Col>
                        <MatchCard title="Movie 2" language="English"
                         picture={img5}
                         subtitle="Movie is outstanding"
                         rating='4.5'/>
                    </Col>
                </Row>

                <div className='heading'>
                    <h3>Based on people like you have watched</h3>
                </div>

                <Row>
                    <Col>
                        <MatchCard title="Movie 1" language="English"
                         picture={img6}
                         subtitle="Movie is outstanding"
                         rating='4.5'/>
                    </Col>
                    <Col>
                        <MatchCard title="Movie 2" language="English"
                         picture={img}
                         subtitle="Movie is outstanding"
                         rating='4.5'/>
                    </Col>
                    <Col>
                        <MatchCard title="ovie 4" language="English"
                         picture={img}
                         subtitle="Movie is outstanding"
                         rating='4.5'/>
                    </Col>
                </Row>
            </Container>
        </div>
    )
}