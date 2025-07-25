/**
 * Module for handling live search functionality across multiple search bars.
 * Debounces user input and displays results for both top-bar and secondary search inputs.
 * @module searchHandler
 */
(function(){

    /**
     * Namespace encapsulating search logic.
     * @namespace searchHandler
     */
    const searchHandler = (() => {
        /** @type {HTMLInputElement} */
        const searchInput = document.getElementById('search-input-1');
        /** @type {HTMLElement} */
        const firstSearchResults = document.getElementById('search-results-top-bar');
        /** @type {HTMLElement} */
        const secondSearchResults = document.getElementById('second-search-bar-results');
        /** @type {number|null} */
        let timeout = null;

        /**
         * Renders search result links into a given container.
         * @function enterResults
         * @memberof searchHandler
         * @param {Array<Object>} results - Array of product objects with id and productName.
         * @param {HTMLElement} searchResults - Container element to populate with links.
         */
        function enterResults(results, searchResults) {
            searchResults.innerHTML = '';

            if (!results || results.length < 1) {
                searchResults.innerHTML =
                    `<a href="#" class="list-group-item list-group-item-action disabled">No Results...</a>`;
            } else {
                results.forEach(product => {
                    const a = document.createElement('a');
                    a.href = `/public/product/${product.id}`;
                    a.className = 'list-group-item list-group-item-action';
                    a.id = `product-${product.id}`;
                    a.textContent = product.productName;
                    searchResults.appendChild(a);
                });
            }
        }

        /**
         * Performs the fetch search request and handles response rendering.
         * @async
         * @function searchFunction
         * @memberof searchHandler
         * @param {Event} e - The input event from the search field.
         * @param {HTMLElement} resultsContainer - The container element for results.
         */
        async function searchFunction(e, resultsContainer) {
            const query = e.target.value;
            try {
                const response = await fetch(
                    `/api/products/search?query=${encodeURIComponent(query)}`
                );
                if (!response.ok) {
                    throw new Error('Search request failed');
                }
                const json = await response.json();
                enterResults(json, resultsContainer);
            } catch (err) {
                console.error(err);
            }
        }

        /**
         * Debounced handler for the first search input.
         * @function firstSearch
         * @memberof searchHandler
         * @param {Event} e - The input event.
         */
        function firstSearch(e) {
            clearTimeout(timeout);
            timeout = setTimeout(() => searchFunction(e, firstSearchResults), 1000);
        }

        /**
         * Debounced handler for the second search input.
         * @function secondSearch
         * @memberof searchHandler
         * @param {Event} e - The input event.
         */
        function secondSearch(e) {
            clearTimeout(timeout);
            timeout = setTimeout(() => searchFunction(e, secondSearchResults), 1000);
        }

        /**
         * Clears search results when clicking outside of search inputs or results.
         * @function clear
         * @memberof searchHandler
         * @param {Event} event - The click event on the document.
         */
        function clear(event) {
            const clickedInsideFirstInput = searchInput.contains(event.target);
            const clickedInsideFirstResults = firstSearchResults.contains(event.target);

            const secondSearchInput = document.getElementById('second-search-bar');
            const clickedInsideSecondInput = secondSearchInput.contains(event.target);
            const clickedInsideSecondResults = secondSearchResults.contains(event.target);

            if (!clickedInsideFirstInput && !clickedInsideFirstResults) {
                firstSearchResults.innerHTML = '';
            }
            if (!clickedInsideSecondInput && !clickedInsideSecondResults) {
                secondSearchResults.innerHTML = '';
            }
        }

        return { firstSearch, secondSearch, clear };
    })()

    /**
     * Attaches event listeners to search inputs and document clicks on DOMContentLoaded.
     */
    document.addEventListener('DOMContentLoaded', () => {
        const topSearch = document.getElementById('search-input-1');
        const secondarySearch = document.getElementById('second-search-bar');

        topSearch.addEventListener('input', searchHandler.firstSearch);
        secondarySearch.addEventListener('input', searchHandler.secondSearch);

        document.addEventListener('click', searchHandler.clear);
    })

})()
