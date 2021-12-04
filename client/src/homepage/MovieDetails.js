import React, { useState,useEffect } from "react";
import { useLocation } from "react-router-dom";
import {get_movie_details} from "../api/services";
import img from '../Images/movies.jpeg';
import "./Form.css";

export default function MovieDetails() {
  const location = useLocation();
  const category = location.state?.movie_id;
  const [getmovieDetails, setMovieDetails] = useState([{}])

  useEffect(() => {
    let mounted = true;
       console.log(category)
      get_movie_details(category)
      .then(items => {
        if(mounted) {
          setMovieDetails(items.data)
        }
      })
    return () => mounted = false;
  }, [])

  return (
      <div className="form-container">
        <span className="close-btn">×</span>
        <div className="form-content-left">
          <img
            className="form-img"
            src={img}
          />
        </div>
        <div className="form-content-right">
      <form 
      className="form" noValidate>
        <div className="form-inputs">
          <label className="form-label">Name</label>
          <input
            className="form-input"
            type="text"
            name="original_title"
            
            value="Star Wars"
            
          />
        
        </div>
        <div className="form-inputs">
          <label className="form-label">Cast</label>
          <input
            className="form-input"
            type="text"
            name="cast"
           
            value="Mark Hamill ,Luke Skywalker, Harrison Ford, Han Solo"
          
          />
         
        </div>
        <div className="form-inputs">
          <label className="form-label">Overview</label>
          <input
            className="form-input"
            type="text"
            name="overview"
            
            value="Princess Leia is captured and held hostage by the evil Imperial 
            forces in their effort to take over the galactic Empire. 
            Venturesome Luke Skywalker and dashing captain Han Solo team together with
             the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and 
             restore peace and justice in the Empire."
         
          />
         
        </div>
        <div className="form-inputs">
          <label className="form-label">Release Date</label>
          <input
            className="form-input"
            type="text"
            name="release_date"
            
            value="10/11/1977"
          
          />
          
        </div>
        <div className="form-inputs">
          <label className="form-label">Genre</label>
          <input
            className="form-input"
            type="text"
            name="genre"
           
            value=" Adventure, Action,Science Fiction"
          
          />
          
        </div>
        <div className="form-inputs">
          <label className="form-label">Language</label>
          <input
            className="form-input"
            type="text"
            name="original_language"
            
            value=""
          
          />
          
        </div>
        <div className="form-inputs">
          <label className="form-label">Vote Average</label>
          <input
            className="form-input"
            type="text"
            name="d.vote_average"
           
            value=""
          
          />
          
        </div>
      </form>
    </div>
      </div>
    
  );
};


