import './App.css';
import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import { Routes } from 'react-router-dom';
import Login from "./login/login";

function App() {
 return (<Router>
     <div className="App">
       <div className="outer">
         <div className="inner">
           <Login/>
           <Routes>
             <Route exact path='/' component={Login} />
             <Route exact path='/home' component={Login} />
           </Routes>
         </div>
       </div>
     </div></Router>
   );
 }

 export default App;
