import React, { useState } from 'react';
import { Link } from 'react-router-dom'
import FormInput from '../components/FormInput';
import ErrorMessage from '../components/ErrorMessage';

function ConfirmationMessage({message}){
    return (
        <p style={{ color: 'green', marginBottom: '1rem' }}>
            {message}
        </p>
    );
}

function Instructions() {
  return (
    <p className="instructions">
    Enter the email address associated with your account, and we'll send you a link to reset your password.
    </p>
  )
}

function ForgotPasswordPage() {
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    setMessage('');
    setError('');

    const formData = {
      email: email,
    };

    console.log('Forgot Password Request Submitted:', formData);
    // TBC: send the data to the backend
    // if there is an email registered:
    // setMessage(`An email has been sent to  ${email} with a link to reset your password.`);
  };

  return (
    <div className="form-container">
      <form onSubmit={handleSubmit}>
        <h2>Forgot Password?</h2>

        {message && (
          <ConfirmationMessage message={message} />
        )}
        
        {error && (
          <ErrorMessage message={error} />
        )}

        {!message && !error && (
          <Instructions />
        )}
        
        <FormInput 
          id = "email"
          name = "email"
          type = "email"
          placeholder = "Enter your Email Address"
          value = {email}
          onChange = {(e) => setEmail(e.target.value)}
          label = "Email Address"
          required = {true}
          srOnlyLabel = {true}
        />

        <button type="submit">Send Reset Link</button>
      </form>

      <div className="options">
        <hr className="option-divider" />
        <Link to="/login" className="login-link">Back to Login</Link>
      </div>
    </div>
  );
}

export default ForgotPasswordPage;
