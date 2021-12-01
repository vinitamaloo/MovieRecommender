import axios from 'axios';
import { useParams } from 'react-router';

const serverUrl = 'http://localhost:8080/'

export const get_popular_movies = (user) => { return axios.post(serverUrl + 'get_popular_movies', user) };
export const get_recommendation_by_user = (filter) => { return axios.post(serverUrl + 'get_recommendation_by_user', filter)};
