import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import FormInput from '../components/FormInput';
import ErrorMessage from '../components/ErrorMessage';

function SignUpPage() {
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const handleSubmit = (event) => {
        event.preventDefault();
        setError(''); // Clear previous errors on new submission
        if (password !== confirmPassword) {
          setError('Passwords do not match');
          return;
        }

        const formData = {
          email,
          username,
          password,
        };
        console.log('Form submitted:', formData);

        //send the data to the backend 
    };

  return (
    <div className="form-container">
      <form onSubmit={handleSubmit}>
        <h2>Create Account</h2>

        <ErrorMessage message={error} />

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

      <div className="options">
        <hr className="option-divider" />
        <Link to="/login" className="login-link">Already have an account? Login</Link>
      </div>
    </div>
  );
}

export default SignUpPage;
