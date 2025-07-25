/**
 * Module for handling sorting of product cards in the UI.
 * Allows sorting products by price in ascending or descending order.
 * @module sortHandler
 */
(function(){

    /**
     * Namespace for sort logic.
     * @namespace handleSort
     */
    const handleSort = (()=>{

        /** @type {HTMLSelectElement} */
        const select = document.getElementById('sort-select');
        /** @type {HTMLElement} */
        const container = document.getElementById('products-container');

        /**
         * Sorts product cards based on selected price direction.
         * @function sort
         * @memberof handleSort
         */
        function sort(){

            const direction = select.value;
            if (direction === 'none') return;

            // 2) Grab an array of all the card <div class="col"> elements
            const cards = Array.from(container.querySelectorAll('.product-container'));

            // 3) Sort the cards array by price
            cards.sort((a, b) => {

                const priceA = parseFloat(a.querySelector('.price').textContent.replace(/[^0-9.]/g, ''));
                const priceB = parseFloat(b.querySelector('.price').textContent.replace(/[^0-9.]/g, ''));

                return direction === 'low-high'
                    ? priceA - priceB
                    : priceB - priceA;
            });

            // 4) Remove all existing cards and re-append them in sorted order
            cards.forEach(card => container.appendChild(card));
        }

        return {sort}

    })()


    /**
     * Initializes sorting on DOMContentLoaded.
     */
    document.addEventListener('DOMContentLoaded', ()=>{

        const select = document.getElementById('sort-select');
        select.addEventListener('change', handleSort.sort);
    })


})()
