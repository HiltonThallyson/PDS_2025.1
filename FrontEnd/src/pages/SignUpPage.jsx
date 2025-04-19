import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import FormInput from '../components/FormInput';
import ErrorMessage from '../components/ErrorMessage';
import styles from '../styles/AuthFormLayout.module.css';

function SignUpPage() {
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [nickname, setNickname] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const handleSubmit = async (event) => {
        event.preventDefault();
        setError('');
        if (password !== confirmPassword) {
          setError('Passwords do not match');
          return;
        }

        const alphanumericRegex = /^[a-zA-Z0-9._]+$/;
        if (!alphanumericRegex.test(username)) {
            setError('Username must contain only alphanumeric characters, ".", and "_"');
            return;
        }
        
        if (password.length < 8) {
            setError('Password must be at least 8 characters long');
            return;
        }

        const formData = {
          email,
          username,
          nickname,
          password,
        };

        try{
          const respose = await fetch('http://localhost:8090/api/register',{
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
          console.log('User registered successfully!');
          navigate('/login');
        }
        catch (error) {
          console.error('Error:', error);
          setError('An error occurred. Please try again later.');
        }
    };

  return (
    <div className={styles.formContainer}>
      <h2>Create Account</h2>
      <ErrorMessage message={error} />
      
      <form onSubmit={handleSubmit}>

        <FormInput 
          id = "email"
          name = "email"
          type = "email"
          placeholder = "Email Address"
          value = {email}
          onChange = {(e) => setEmail(e.target.value)}
          label = "Email Address"
          required = {true}
          srOnlyLabel = {true}
        />

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
          id = "nickname"
          name = "nickname"
          type = "text"
          placeholder = "Nickname"
          value = {nickname}
          onChange = {(e) => setNickname(e.target.value)}
          label = "Nickname"
          required = {true}
          srOnlyLabel = {true}
        />
          
        <FormInput
          id = "password"
          name = "password"
          type = "password"
          placeholder = "Choose a Password"
          value = {password}
          onChange = {(e) => setPassword(e.target.value)}
          label = "Choose a Password"
          required = {true}
          srOnlyLabel = {true}
        />

        <FormInput
          id = "confirm-password"
          name = "confirm_password"
          type = "password"
          placeholder = "Confirm Password"
          value = {confirmPassword}
          onChange = {(e) => setConfirmPassword(e.target.value)}
          label = "Confirm Password"
          required = {true}
          srOnlyLabel = {true}
        />

        <button type="submit">Sign Up</button>
      </form>

      <div className={styles.options}>
        <hr className={styles.optionDivider} />
        <Link to="/login">Already have an account? Login</Link>
      </div>
    </div>
  );
}

export default SignUpPage;
