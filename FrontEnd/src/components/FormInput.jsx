import React from 'react';
import styles from './styles/FormInput.module.css';

/**
 * @param {string} id - Unique identifier used for both the input's `id` and the label's `htmlFor` attribute.
 * @param {string} name - The `name` attribute for the input element.
 * @param {string} [type="text"] - The type of the input element (e.g., 'text', 'email', 'password'). Defaults to 'text'.
 * @param {string} placeholder - Placeholder text for the input element.
 * @param {string} value - The current value of the input (controlled component pattern).
 * @param {function} onChange - Callback function triggered when the input value changes. Receives the event object.
 * @param {string} label - The text content for the input's associated label.
 * @param {boolean} [required=true] - Whether the input field is marked as required. Defaults to true.
 * @param {boolean} [srOnlyLabel=true] - If true, applies screen-reader-only styles to the label, making it visually hidden but accessible. Defaults to true.
 * @returns {JSX.Element} The rendered form input component.
 */

function FormInput({
  id,
  name,
  type = "text",
  placeholder,
  value,
  onChange,
  label,
  required = true,
  srOnlyLabel = true
}) {
  const labelClass = srOnlyLabel ? styles.srOnly : styles.label;

  return (
    <div className={styles.inputWrapper}>
      <label htmlFor={id} className={labelClass}>
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
        className={styles.inputElement}
      />
    </div>
  );
}

export default FormInput;
