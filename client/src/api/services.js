import axios from 'axios';
import { useParams } from 'react-router';

const serverUrl = 'http://localhost:8080/server/'

export const get_popular_movies = () => { return axios.get(serverUrl + 'get_popular_movies') };
export const get_recommendation_by_user = (userId) => { return axios.get(serverUrl + 'get_recommendation_by_user/'+userId)};
export const get_recommendation_based_on_other_users = (userId) => {
                return axios.get(serverUrl +'get_recommendation_based_on_other_users/'+userId)};
