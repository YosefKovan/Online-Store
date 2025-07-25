/**
 * Module for handling quantity adjustments on product pages.
 * Provides functionality to increment and decrement item quantity within specified limits.
 * @module quantityHandler
 */
(function(){

    /**
     * Namespace for quantity handling logic.
     * @namespace handleQuantity
     */
    const handleQuantity = (() => {
        /** @type {HTMLInputElement} */
        const quantity = document.getElementById('quantity');

        /**
         * Increments the quantity value by one, respecting the max attribute.
         * @function increment
         * @memberof handleQuantity
         * @param {Event} e - The click event triggering the increment.
         */
        function increment(e) {
            e.preventDefault();
            const max = parseInt(quantity.max, 10);
            let val = parseInt(quantity.value, 10);
            if (val < max) quantity.value = val + 1;
        }

        /**
         * Decrements the quantity value by one, respecting the min attribute.
         * @function decrement
         * @memberof handleQuantity
         * @param {Event} e - The click event triggering the decrement.
         */
        function decrement(e) {
            e.preventDefault();
            const min = parseInt(quantity.min, 10);
            let val = parseInt(quantity.value, 10);
            if (val > min) quantity.value = val - 1;
        }

        return { increment, decrement }
    })()

    /**
     * Initializes event listeners for quantity adjustment buttons when DOM content is loaded.
     */
    document.addEventListener('DOMContentLoaded', () => {
        /** @type {HTMLButtonElement} */
        const addButton = document.getElementById('add');
        /** @type {HTMLButtonElement} */
        const reduceButton = document.getElementById('reduce');

        addButton.addEventListener('click', handleQuantity.increment);
        reduceButton.addEventListener('click', handleQuantity.decrement);
    });

})()
