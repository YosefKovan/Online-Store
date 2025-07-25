/**
 * alertHandler module manages the display of success and failure alert boxes.
 * It uses the Revealing Module Pattern to encapsulate private variables and expose public methods.
 * @module alertHandler
 */
const alertHandler = (() => {

    /**
     * The container element for the success alert box.
     * @type {HTMLElement}
     * @private
     */
    const successAlertBox = document.getElementById("successAlert");

    /**
     * The container element for the failed alert box.
     * @type {HTMLElement}
     * @private
     */
    const failedAlertBox = document.getElementById("failedAlert");

    /**
     * Displays the success alert with a given title and message.
     * @param {string} title - The title text for the success alert.
     * @param {string} message - The message text for the success alert.
     */
    function successAlert(title, message) {

        /** @type {HTMLElement} */
        const alertTitle = document.getElementById("successTitle");
        /** @type {HTMLElement} */
        const alertMessage = document.getElementById("successMessage");

        alertTitle.textContent = title;
        alertMessage.textContent = message;

        successAlertBox.classList.remove("d-none");
        successAlertBox.classList.add('show');

        failedAlertBox.classList.add('d-none');
        failedAlertBox.classList.remove('show');

    }

    /**
     * Displays the failed alert with a given title and message.
     * @param {string} title - The title text for the failed alert.
     * @param {string} message - The message text for the failed alert.
     */
    function failedAlert(title, message) {

        /** @type {HTMLElement} */
        const alertTitle = document.getElementById("failedTitle");
        /** @type {HTMLElement} */
        const alertMessage = document.getElementById("failedMessage");

        alertTitle.textContent = title;
        alertMessage.textContent = message;

        failedAlertBox.classList.remove("d-none");
        failedAlertBox.classList.add('show');

        successAlertBox.classList.add('d-none');
        successAlertBox.classList.remove('show');
    }

    /**
     * Hides the success alert box.
     */
    function closeSuccessButton() {
        /** @type {HTMLElement} */
        const alertBox = document.getElementById("successAlert");
        alertBox.classList.remove("show");
        alertBox.classList.add("d-none");
    }

    /**
     * Hides the failed alert box.
     */
    function closeFailedButton() {

        /** @type {HTMLElement} */
        const alertBox = document.getElementById("failedAlert");
        alertBox.classList.remove("show");
        alertBox.classList.add("d-none");
    }

    /**
     * Sets up click event listeners for the alert close buttons.
     */
    function setUp() {

        document.getElementById("failedAlertCloseBtn").addEventListener("click", alertHandler.closeFailedButton);
        document.getElementById("successAlertCloseBtn").addEventListener("click", alertHandler.closeSuccessButton);
    }

    // Expose public methods
    return {
        /** @method */
        closeFailedButton,
        /** @method */
        closeSuccessButton,
        /** @method */
        successAlert,
        /** @method */
        failedAlert,
        /** @method */
        setUp
    };
})();
