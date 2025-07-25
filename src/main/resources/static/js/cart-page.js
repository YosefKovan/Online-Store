/**
 * Module for handling deletion of cart items in the shopping cart UI.
 * Adds event listeners to delete buttons and updates the cart display upon item removal.
 * @module handleDeleteElements
 */
(function(){

    /**
     * Initializes delete functionality for cart items.
     * @returns {{deleteElements: function(Event): Promise<void>}} Object containing the deleteElements handler.
     */
    const handleDeleteElements = () => {

        /** @type {HTMLElement} */
        const cartItems = document.getElementById('cart-items');
        /** @type {HTMLElement} */
        const cartSizeElement = document.getElementById('cart-size');
        /** @type {HTMLElement} */
        const payment = document.getElementById('payment');
        const cartSizeElement = document.getElementById('cart-size-text');
        const payment = document.getElementById('payment');
        const cartTotalNumber = document.getElementById('cart-size')

        /** @type {string} */
        const apiUrl = "/api/delete/cart-item/";


        /**
         * Displays the result of a delete operation.
         * @param {boolean} result - True if deletion was successful, false otherwise.
         */
        function displaySuccessResult(result){
            if(result){
                // display success
            } else {
                // display failure
            }

        function changeCartQuantity(element){


            const itemContainer = element.closest('.cart-item-container');

            const qtyText = itemContainer.querySelector('.quantity').textContent;
            const quantity = parseInt(qtyText, 10);

            const productsInCart = parseInt(cartTotalNumber.textContent, 10);
            cartTotalNumber.textContent = `${productsInCart - quantity}`;

        }

        /**
         * Hides the payment section when cart is empty.
         */
        function blockPayment(){
            payment.classList.add('d-none');
        }

        /**
         * Removes the DOM element of a deleted cart item and updates cart count.
         * @param {string} id - The DOM id of the cart item to remove.
         */
        function removeElement(id){
            const element = document.getElementById(id);
            if(element){
                changeCartQuantity(element);
                element.remove();
            }

            if(cartItems.children.length < 1){
                blockPayment();
                cartSizeElement.innerHTML = "No items in cart";
            } else {
                cartSizeElement.innerHTML = `Cart items - ${cartItems.children.length}`;
            }
        }

        /**
         * Processes the fetch result and updates UI accordingly.
         * @async
         * @param {Response} result - The fetch API response object.
         * @param {string} id - The id of the cart item to remove on success.
         * @throws Will throw an error if the result is not ok or parsing fails.
         */
        async function handleResult(result, id){
            try{
                if(!result.ok){
                    displaySuccessResult(false);
                    throw new Error("Result value is not OK");
                }

                removeElement(id); // and change price
                displaySuccessResult(true);

            } catch(e) {
                throw new Error("Error caught when parsing the data: " + e);
            }
        }

        /**
         * Event handler for delete button clicks. Sends DELETE request and handles response.
         * @async
         * @param {Event} event - The click event on the delete button.
         */
        async function deleteElements(event){
            event.preventDefault();
            const deleteButton = event.currentTarget;
            const container = deleteButton.closest('.cart-item-container');
            const id = container.id;

            try{
                const result = await fetch(apiUrl + id, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                });

                await handleResult(result, id);

            } catch(e) {
                console.error("Error " + e);
            }
        }

        return { deleteElements };
    };

    // Initialize event listeners on DOMContentLoaded
    document.addEventListener("DOMContentLoaded", () => {
        const deleteButtons = document.querySelectorAll('.delete-btn');
        const { deleteElements } = handleDeleteElements();
        Array.from(deleteButtons).forEach((btn) => {
            btn.addEventListener('click', deleteElements);
        });
    });

})();
