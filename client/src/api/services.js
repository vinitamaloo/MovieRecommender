import axios from 'axios';
import { useParams } from 'react-router';

const serverUrl = 'http://localhost:8080/server/'

export const get_popular_movies = () => { return axios.post(serverUrl + 'get_popular_movies') };
export const get_recommendation_by_user = (filter) => { return axios.post(serverUrl + 'get_recommendation_by_user', filter)};
export const get_recommendation_based_on_other_users = (filter) => {
                return axios.post(serverUrl +'get_recommendation_based_on_other_users', filter)};
