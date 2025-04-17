import React, { useState } from 'react';
import FormInput from '../components/FormInput';
import ErrorMessage from '../components/ErrorMessage';
import {Link} from 'react-router-dom';

function LoginPage(){
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        setError('');
        const formData = {
            username,
            password,
        };
        console.log('Form submitted:', formData);
        // TBC: send the data to the backend
    }
    return (
        <div className="form-container">
          <form onSubmit={handleSubmit}>
            <h2>MyBookPlace</h2>
    
            {/* TBC: if the username/password is wrong*/}
            <ErrorMessage message={error} />
    
            <FormInput 
              id = "username"
              name = "username"
              type = "text"
              placeholder = "Username"
              value = {username}
              onChange = {(e) => setUsername(e.target.value)}
              label = "Username"
              required = {true}
              srOnlyLabel = {true}
            />

            <FormInput 
              id = "password"
              name = "password"
              type = "password"
              placeholder = "Password"
              value = {password}
              onChange = {(e) => setPassword(e.target.value)}
              label = "Password"
              required = {true}
              srOnlyLabel = {true}
            />
            
            <button type="submit">Login</button>
          </form>
    
          <div className="options">
            <Link to="/forgot-password">Forgot Password?</Link>
            <hr className="option-divider" />
            <Link to="/signup">Create New Account</Link>
          </div>
        </div>
    );
}

export default LoginPage;