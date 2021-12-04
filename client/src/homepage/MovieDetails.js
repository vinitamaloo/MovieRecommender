import React, { useState,useEffect } from "react";
import { useLocation, useParams } from "react-router-dom";
import {get_movie_details} from "../api/services";
import img from '../Images/movies.jpeg';
import "./Form.css";

export default function MovieDetails() {
  let params = useParams();
  const category = params.movieId
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

    function get_cast(cast) {
        var str;

        if (!cast)
            return " "

        cast.map((d)=> (
            str += d.cast_name+" : "+d.cast_character
        ))

        str = str.substr(9, str.length)
        return str
    }

    function get_genre(genre) {
        var str;

        if (!genre)
            return " "

        genre.map((d)=> (
            str += d.name+" "
        ))

        str = str.substr(9, str.length)
        return str
    }

  return (
      <div className="form-container">
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
            value={getmovieDetails.original_title}/>
        
        </div>
        <div className="form-inputs">
          <label className="form-label">Cast</label>
          <input
            className="form-input"
            type="text"
            name="cast"
            value={get_cast(getmovieDetails.cast)} />
        </div>
        <div className="form-inputs">
          <label className="form-label">Overview</label>
          <input
            className="form-input"
            type="text"
            name="overview"
            value={getmovieDetails.overview} />
         
        </div>
        <div className="form-inputs">
          <label className="form-label">Release Date</label>
          <input
            className="form-input"
            type="text"
            name="release_date"
            value={new Date(getmovieDetails.release)} />
          
        </div>
        <div className="form-inputs">
          <label className="form-label">Genre</label>
          <input
            className="form-input"
            type="text"
            name="genre"
            value={get_genre(getmovieDetails.genre)} />
          
        </div>
        <div className="form-inputs">
          <label className="form-label">Language</label>
          <input
            className="form-input"
            type="text"
            name="original_language"
            
            value={getmovieDetails.original_language}
          
          />
          
        </div>
        <div className="form-inputs">
          <label className="form-label">Vote Average</label>
          <input
            className="form-input"
            type="text"
            name="votes"
           
            value={getmovieDetails.vote_average}
          
          />
          
        </div>
      </form>
    </div>
      </div>
    
  );
};


