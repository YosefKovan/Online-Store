/**
 * Module for handling user registration form validation and navigation between steps.
 * Provides field-level error display, input checks against the server, and password validation.
 * @module registrationHandler
 */
(function(){

    /**
     * Namespace for showing and removing field error messages.
     * @namespace errorHandler
     */
    const errorHandler = (() => {
        /**
         * Displays an error message for a specific field.
         * @function showFieldError
         * @memberof errorHandler
         * @param {string} fieldId - The id of the field to show the error for.
         * @param {string} message - The error message to display.
         */
        function showFieldError(fieldId, message) {
            const errorSpan = document.getElementById(`${fieldId}-error`);
            if (errorSpan) {
                errorSpan.textContent = message;
            }
        }

        /**
         * Clears the error message for a specific field.
         * @function removeError
         * @memberof errorHandler
         * @param {string} fieldId - The id of the field to clear the error for.
         */
        function removeError(fieldId) {
            const errorSpan = document.getElementById(`${fieldId}-error`);
            if (errorSpan && errorSpan.textContent !== "") {
                errorSpan.textContent = "";
            }
        }

        return { showFieldError, removeError };
    })();

    /**
     * Namespace for the registration page logic (step 1: username/email).
     * @namespace registerPage
     */
    const registerPage = (() => {
        /** @type {HTMLElement} */
        const registerPage = document.getElementById("register-page");
        /** @type {HTMLElement} */
        const passwordPage = document.getElementById("password-page");
        /** @type {HTMLInputElement} */
        const username = document.getElementById("username");
        /** @type {HTMLInputElement} */
        const email = document.getElementById("email");
        /** @type {string} */
        const api = "/api/user-exists";

        /**
         * Sets field error messages based on server response.
         * @function setErrors
         * @memberof registerPage
         * @param {{errors: Object, errorMessage: Object}} errors - Object containing boolean flags and messages.
         */
        function setErrors(errors) {
            if (errors.errors.username) {
                errorHandler.showFieldError("username", errors.errorMessage.usernameError);
            } else {
                errorHandler.removeError("username");
            }

            if (errors.errors.email) {
                errorHandler.showFieldError("email", errors.errorMessage.emailError);
            } else {
                errorHandler.removeError("email");
            }
        }

        /**
         * Sends input values to server to check for existing user/email.
         * @async
         * @function checkInput
         * @memberof registerPage
         * @throws Will throw an error if the server response indicates a conflict or failure.
         */
        async function checkInput() {
            const data = { username: username.value.trim(), email: email.value.trim() };
            const response = await fetch(api, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            if (response.status === 409) {
                const result = await response.json();
                setErrors(result);
                throw new Error("the fields did not pass the api check");
            } else if (!response.ok) {
                throw new Error("some error occurred!");
            }
        }

        /**
         * Checks for empty username or email and shows errors accordingly.
         * @function isEmpty
         * @memberof registerPage
         * @returns {boolean} True if there was an empty field, false otherwise.
         */
        function isEmpty() {
            let error = false;
            if (username.value.trim() === "") {
                errorHandler.showFieldError("username", "username field must be filled");
                error = true;
            } else {
                errorHandler.removeError("username");
            }

            if (email.value.trim() === "") {
                errorHandler.showFieldError("email", "email must be entered");
                error = true;
            } else {
                errorHandler.removeError("email");
            }

            return error;
        }

        /**
         * Handles click on "Next" button: validates inputs and moves to password step.
         * @async
         * @function handleNextBtnClicked
         * @memberof registerPage
         */
        async function handleNextBtnClicked() {
            if (isEmpty()) return;
            try {
                await checkInput();
                passwordPage.classList.remove('d-none');
                registerPage.classList.add('d-none');
            } catch (error) {
                console.error(error);
            }
        }

        return { handleNextBtnClicked };
    })();

    /**
     * Namespace for the password page logic (step 2: set password).
     * @namespace passwordPage
     */
    const passwordPage = (() => {
        /** @type {HTMLInputElement} */
        const password = document.getElementById("password");
        /** @type {HTMLInputElement} */
        const confirmPassword = document.getElementById("confirmPassword");
        /** @type {string} */
        const notEqualErrorMsg = "Confirming password failed, there is no match!";
        /** @type {string} */
        const strengthErrorMsg = "Password must be at least 8 characters, include an uppercase letter, and a digit or special character.";
        /** @type {RegExp} */
        const strengthRegex = /^(?=.{8,})(?=.*[A-Z])(?=.*[^A-Za-z]).*$/;

        /**
         * Validates that password and confirmation match and meet strength requirements.
         * @function checkPassword
         * @memberof passwordPage
         * @returns {boolean} True if validation passes, false otherwise.
         */
        function checkPassword() {
            if (password.value.trim() !== confirmPassword.value.trim()) {
                errorHandler.showFieldError("password", notEqualErrorMsg);
                return false;
            }
            if (!strengthRegex.test(password.value.trim())) {
                errorHandler.showFieldError("password", strengthErrorMsg);
                return false;
            }
            errorHandler.removeError("password");
            return true;
        }

        /**
         * Handles form submission on password page, preventing default if validation fails.
         * @function handleSubmit
         * @memberof passwordPage
         * @param {Event} e - The form submit event.
         */
        function handleSubmit(e) {
            e.preventDefault();
            if (checkPassword()) {
                e.target.submit();
            }
        }

        /**
         * Handles clicking "Back" to return to the registration step.
         * @function handleBack
         * @memberof passwordPage
         */
        function handleBack() {
            document.getElementById("password-page").classList.add('d-none');
            document.getElementById("register-page").classList.remove('d-none');
        }

        return { handleBack, handleSubmit };
    })()

    /**
     * Attaches event listeners to buttons and form on DOMContentLoaded.
     */
    document.addEventListener("DOMContentLoaded", () => {
        document.getElementById("next-btn").addEventListener("click", registerPage.handleNextBtnClicked);
        document.getElementById("back-password").addEventListener("click", passwordPage.handleBack);
        document.getElementById("register-form").addEventListener("submit", passwordPage.handleSubmit);
    })

})()
