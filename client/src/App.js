import './App.css';
import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import { Routes } from 'react-router-dom';
import Login from "./login/Login";
import Homepage from "./homepage/Homepage";

function App() {
 return (
    <Router>
     <div className="App">
           <Routes>
             <Route exact path='/' element={<Login/>} />
             <Route exact path='/homepage' element={<Homepage/>} />
             <Route exact path='/movie-details' element={<Homepage/>} />
           </Routes>
     </div></Router>
   );
 }

export default App;
