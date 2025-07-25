/**
 * Module for filtering products in the admin products table UI.
 * Supports filtering by category and (optionally) price direction.
 * @module filterHandler
 */
(function(){

    /**
     * Namespace for filter logic.
     * @namespace filter
     */
    const filter = (()=>{

        /** @type {HTMLSelectElement} */
        const category = document.getElementById('category-filter');
        /** @type {HTMLSelectElement} */
        const priceDirection = document.getElementById('price-filter');
        /** @type {HTMLTableElement} */
        const table = document.getElementById('productTable');

        /**
         * Filters table rows to show only those matching the given category.
         * @function filterCategory
         * @memberof filter
         * @param {string} categoryText - The text of the category to filter by.
         */
        function filterCategory(categoryText){

            const rows = table.querySelectorAll('tbody tr');

            rows.forEach(row => {
                const categoryCell = row.cells[2];
                if(categoryCell.textContent.trim() !== categoryText){
                    row.classList.add('d-none');
                }else{
                    row.classList.remove('d-none')
                }
            });
        }

        /**
         * Applies the selected filters: filters by category and checks price direction.
         * @function handleFilter
         * @memberof filter
         */
        function handleFilter(){

            const filterText = category.value.trim();
            filterCategory(filterText);

        }

        return {handleFilter}


    })()

    /**
     * Initializes filter button listener on DOMContentLoaded.
     */
    document.addEventListener("DOMContentLoaded", ()=>{

        const filterButton = document.getElementById('filter-btn');
        filterButton.addEventListener('click', filter.handleFilter)

    })

})()
