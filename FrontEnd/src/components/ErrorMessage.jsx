import React from 'react';

// Props:
// - message: The error string to display. Can be null or empty.

function ErrorMessage({ message }) {
  if (!message) {
    return null;
  }
  return (
    <p className="form-error">
      {message}
    </p>
  );
}

export default ErrorMessage;
