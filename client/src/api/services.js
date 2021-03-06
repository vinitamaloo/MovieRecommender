import axios from 'axios';
import { useParams } from 'react-router';

const serverUrl = 'http://localhost:8080/server/'

export const get_popular_movies = () => { return axios.get(serverUrl + 'get_popular_movies') };
export const get_movie_details = (movie_id) => { return axios.get(serverUrl + 'get_movie_details/'+movie_id) };
export const get_recommendation_by_user = (userId) => { return axios.get(serverUrl + 'get_recommendation_by_Users_rating/'+userId)};
export const get_recommendation = (userId) => {
                return axios.get(serverUrl +'get_recommendation_based_on_other_users/'+userId)};
