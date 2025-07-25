/**
 * Module for handling star rating interactions in the UI.
 * Allows rendering and user interaction for a star-based rating input.
 * @module starHandler
 */
(function(){

    /**
     * Namespace for star rating logic.
     * @namespace starHandler
     */
    const starHandler = (() => {
        /** @type {NodeListOf<SVGElement>} */
        const stars = document.querySelectorAll('#star-container svg');
        /** @type {HTMLInputElement} */
        const ratingInput = document.getElementById('rating-value');

        /**
         * Renders the stars based on a given rating value.
         * Fills stars up to the rating and clears the rest.
         * @function render
         * @memberof starHandler
         * @param {number} rating - The numeric rating to display.
         */
        function render(rating) {
            stars.forEach(star => {
                const val = parseInt(star.dataset.value, 10);
                star.style.fill = val <= rating ? '#ffc107' : '#ddd';
            });
        }

        /**
         * Attaches event listeners for click, mouseover, and mouseout to each star.
         * Updates the input value and re-renders stars on interaction.
         * @function addListeners
         * @memberof starHandler
         */
        function addListeners() {
            stars.forEach(star => {
                const val = parseInt(star.dataset.value, 10);

                /**
                 * On click, set the rating input and render.
                 */
                star.addEventListener('click', () => {
                    ratingInput.value = String(val);
                    render(val);
                });

                /**
                 * On hover, render up to the hovered star.
                 */
                star.addEventListener('mouseover', () => render(val));

                /**
                 * On mouseout, revert to stored rating value.
                 */
                star.addEventListener('mouseout', () => {
                    const stored = parseInt(ratingInput.value, 10) || 0;
                    render(stored);
                });
            });
        }

        return { render, addListeners };
    })()

    /**
     * Initializes the star handler on DOMContentLoaded.
     */
    document.addEventListener('DOMContentLoaded', () => {
        // Initial render based on current input value
        const initial = parseInt(document.getElementById('rating-value').value, 10) || 0;
        starHandler.render(initial);
        // Wire up event listeners
        starHandler.addListeners();
    })

})()
