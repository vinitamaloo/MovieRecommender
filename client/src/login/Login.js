import {React, useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { useNavigate } from 'react-router-dom';
import "./login.css";

export default function Login() {
  const [userId, setUserId] = useState("");
  const navigate = useNavigate();

  async function handleSubmit(event) {
    event.preventDefault()
    const user = {
      "user_id": userId
    }

    localStorage.setItem('userId', userId)
    navigate('/homepage');
  }

   return (
      <div className="auth-wrapper loginBody">
        <div className="auth-inner">
          <div className="login">
            <Form onSubmit={handleSubmit}>
              <div className='central_heading'>
                <h6>Welcome to your movie Recommendation System</h6>
              </div>
              <Form.Group className="mb-3" controlId="UserId">
                <Form.Control
                  type="text"
                  placeholder="Enter Your User Id"
                  onChange={e => setUserId(e.target.value)}
                />
              </Form.Group>
              <div className='central_heading'>
                <Button variant="primary" type="submit">Lets Go</Button>
              </div>
            </Form>
          </div>
        </div>
      </div>
   )
}
