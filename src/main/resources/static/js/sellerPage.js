/**
 * Module for handling the Add Product page and product manager page toggling.
 * Provides functionality to collect product data, submit via API, and update the UI table,
 * as well as navigation between the add/edit form and the product manager list.
 * @module addProductPageHandler
 */
(function(){

    /**
     * Namespace for Add Product page logic.
     * @namespace addProductPage
     */
    const addProductPage = (() => {
        /** @type {HTMLElement} */
        const previousPrice = document.getElementById('previous-price');
        /** @type {HTMLInputElement} */
        const productName = document.getElementById('productName');
        /** @type {HTMLTextAreaElement} */
        const description = document.getElementById('Textarea');
        /** @type {HTMLInputElement} */
        const price = document.getElementById('price');
        /** @type {HTMLInputElement} */
        const inventory = document.getElementById('inventory');
        /** @type {HTMLInputElement} */
        const fileInput = document.getElementById('fileInput');

        /**
         * Handles the "Back" button click by toggling page views.
         * @function handleBack
         * @memberof addProductPage
         */
        function handleBack() {
            productManager.togglePage();
        }

        /**
         * Appends a new row to the product table with data from the created product.
         * @function addDataToPage
         * @memberof addProductPage
         * @param {Object} product - The product object returned by the API.
         * @param {string} product.imageUrl - The filename of the uploaded image.
         * @param {string} product.productName - Name of the product.
         * @param {string} product.productDescription - Description text.
         * @param {number|string} product.price - Price value.
         * @param {number|string} product.inventory - Inventory count.
         */
        function addDataToPage(product) {
            const tableBody = document.querySelector('#productTable tbody');
            const row = document.createElement('tr');
            const rowCount = tableBody.children.length;

            row.innerHTML = `
                <th scope="row">${rowCount + 1}</th>
                <td><img src="/uploads/${product.imageUrl}" alt="Product Image" width="35"/></td>
                <td>${product.productName}</td>
                <td>${product.productDescription}</td>
                <td>${product.price} $</td>
                <td>${product.inventory}</td>
            `;
            tableBody.appendChild(row);
        }

        /**
         * Sends product data including file upload to the server and updates the UI.
         * @async
         * @function sendData
         * @memberof addProductPage
         * @throws Will log an error if the API call fails.
         */
        async function sendData() {
            const api = '/api/admin/add-product';
            const formData = new FormData();
            formData.append('productName', productName.value.trim());
            formData.append('productDescription', description.value.trim());
            formData.append('price', price.value.trim());
            formData.append('inventory', inventory.value.trim());
            formData.append('file', fileInput.files[0]);

            try {
                const res = await fetch(api, { method: 'POST', body: formData });
                if (!res.ok) {
                    const errorText = await res.text();
                    throw new Error('API call failed: ' + errorText);
                }
                const data = await res.json();
                addDataToPage(data);
                productManager.togglePage();
            } catch (e) {
                console.error('Error : ' + e);
                // TODO: Notify user of the error
            }
        }

        /**
         * Handles form submission for adding a product.
         * @async
         * @function addProduct
         * @memberof addProductPage
         * @param {Event} e - The submit event from the form.
         */
        async function addProduct(e) {
            e.preventDefault();
            await sendData();
        }

        /**
         * Shows or hides the "previous price" input based on sale radio selection.
         * @function handleRadio
         * @memberof addProductPage
         * @param {HTMLInputElement} radio - The radio button that changed.
         */
        function handleRadio(radio) {
            if (radio.value.toLowerCase() === 'not on sale') {
                previousPrice.classList.add('d-none');
            } else {
                previousPrice.classList.remove('d-none');
            }
        }

        return { addProduct, handleRadio, handleBack };
    })();

    /**
     * Namespace for toggling between the Add Product form and the Product Manager list.
     * @namespace productManager
     */
    const productManager = (() => {
        /** @type {HTMLElement} */
        const addProductPageEl = document.getElementById('add-edit-page');
        /** @type {HTMLElement} */
        const productManagerPage = document.getElementById('product-manager-page');

        /**
         * Toggles visibility of the add/edit form and the product manager page.
         * @function togglePage
         * @memberof productManager
         */
        function togglePage() {
            addProductPageEl.classList.toggle('d-none');
            productManagerPage.classList.toggle('d-none');
        }

        return { togglePage };
    })();

    /**
     * Attaches event listeners for button clicks and form submission on DOMContentLoaded.
     */
    document.addEventListener('DOMContentLoaded', () => {
        const addBtn = document.getElementById('add-btn');
        const form = document.getElementById('add-product-form');
        const backBtn = document.getElementById('back');

        form.addEventListener('submit', addProductPage.addProduct);
        backBtn.addEventListener('click', addProductPage.handleBack);
        addBtn.addEventListener('click', productManager.togglePage);
    });

})();
