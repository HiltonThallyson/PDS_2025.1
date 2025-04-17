// src/components/FormInput.jsx
import React from 'react';

// Props:
// - id: Used for both input id and label htmlFor
// - name: Input name attribute
// - type: Input type (text, email, password)
// - placeholder: Input placeholder text
// - value: Controlled input value from parent state
// - onChange: Function to update parent state
// - label: Text for the label (will be visually hidden by sr-only)
// - required: Boolean, defaults to true if not specified
// - srOnlyLabel: Boolean, defaults to true to apply sr-only class

function FormInput({
  id,
  name,
  type = "text", // Default type to text
  placeholder,
  value,
  onChange,
  label,
  required = true, // Default required to true
  srOnlyLabel = true // Default to using sr-only class
}) {
  return (
    <div className="form-input">
      <label htmlFor={id} className={srOnlyLabel ? "sr-only" : ""}>
        {label}
      </label>
      <input
        type={type}
        id={id}
        name={name}
        placeholder={placeholder}
        required={required}
        value={value}
        onChange={onChange}
      />
    </div>
  );
}

export default FormInput;
