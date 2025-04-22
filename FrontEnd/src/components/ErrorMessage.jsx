import React from 'react';
import styles from './styles/ErrorMessage.module.css';

/**
 * @param {string} message - The error message to display. 
 * @returns {JSX.Element} - The rendered form input component.
*/

function ErrorMessage({ message }) {
  if (!message) {
    return null;
  }
  
  return (
    <p className={styles.formError}>
      {message}
    </p>
  );
}

export default ErrorMessage;