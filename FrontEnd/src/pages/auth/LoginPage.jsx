import React, { useState } from 'react';
import FormInput from '../../components/FormInput';
import ErrorMessage from '../../components/ErrorMessage';
import {Link} from 'react-router-dom';
import styles from '../../styles/AuthFormLayout.module.css'
import { useNavigate } from "react-router-dom";

function LoginPage(){
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError('');
        const formData = {
            username,
            password,
        };
        try{
          const response = await fetch('http://localhost:8090/api/auth/login',{
            method: 'POST',
            headers:{
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
          });
          if (!response.ok){
            const errorData = await response.json();
            setError(errorData.message);
            return
          }
          console.log('User logged in successfully!');
          
          navigate('/home', {state: { username: username }});

        }
        catch (error) {
          console.error('Error:', error);
          setError('An error occurred. Please try again later.');
        }
    }
    return (
        <div className={styles.formContainer}>
          <h2>MyBookPlace</h2>
  
          {/* TBC: if the username/password is wrong*/}
          <ErrorMessage message={error} />
          
          <form onSubmit={handleSubmit}>
    
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
    
          <div className={styles.options}>
            <Link to="/forgot-password">Forgot Password?</Link>
            <hr className={styles.optionDivider} />
            <Link to="/signup">Create New Account</Link>
          </div>
        </div>
    );
}

export default LoginPage;