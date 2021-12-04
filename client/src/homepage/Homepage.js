import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";
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
    const [data, setTable] = useState([{}]);
    const [flag, setFlag] = useState(true);
    const [popularMovies, setPopularMovies] = useState([{}])
    const [otherUserMovies, setOtherUserMovies] = useState([{}])
    const [usersMovie, setUsersMovie] = useState([{}])

    useEffect(() => {
       let mounted = true;
       get_popular_movies()
         .then(items => {
           if(mounted) {
             setPopularMovies(items.data)
           }
         })

//         get_recommendation_based_on_other_users(userId)
//         .then(items => {
//           if(mounted) {
//             setOtherUserMovies(items.data)
//           }
//         })

         get_recommendation_by_user(userId)
          .then(items => {
            if(mounted) {
              setUsersMovie(items.data)
            }
          })

       return () => mounted = false;
     }, [])

    return(
        <div style={{ marginTop: 60 }}>
            <Container>
                <div className='heading'>
                    <h3>Based on popularity</h3>
                </div>

                <Row>
                {popularMovies.map((movie) => (
                    <Col>
                        <MatchCard title={movie.original_title} language={movie.original_language}
                         picture={img}
                         subtitle={movie.overview}
                         rating={movie.vote_average}
                         movieId = {movie.movie_id} />
                    </Col>
                ))}
                </Row>

                 <div className='heading'>
                    <h3>Based on your likes</h3>
                 </div>

                  <Row>
                     {otherUserMovies.map((movie) => (
                         <Col>
                             <MatchCard title={movie.original_title} language={movie.original_language}
                              picture={img3}
                              subtitle={movie.overview}
                              rating={movie.vote_average}
                              movieId = {movie.movie_id} />
                         </Col>
                     ))}
                  </Row>

                <div className='heading'>
                    <h3>Based on people like you have watched</h3>
                </div>

                <Row>
                     {usersMovie.map((movie) => (
                         <Col>
                             <MatchCard title={movie.original_title} language={movie.original_language}
                              picture={img2}
                              subtitle={movie.overview}
                              rating={movie.vote_average}
                              movieId = {movie.movie_id} />
                         </Col>
                     ))}
                 </Row>
            </Container>
        </div>
    )
}