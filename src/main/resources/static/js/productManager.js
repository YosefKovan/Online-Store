/**
 * Module for handling edit and delete actions for products in the admin UI.
 * Provides functionality to attach event listeners to edit and delete buttons,
 * navigate to the edit page, and delete products via API calls with UI updates.
 * @module productManagerHandler
 */
(function(){

    /**
     * Namespace for handling edit button events.
     * @namespace editHandler
     */
    const editHandler = (() => {
        /**
         * Attaches click event listeners to all edit buttons on the page.
         * @function setUpEdit
         * @memberof editHandler
         */
        function setUpEdit() {
            const editButtons = document.querySelectorAll(".edit-btn");
            editButtons.forEach(btn => {
                btn.addEventListener("click", handleEdit);
            });
        }

        /**
         * Handles edit button click by navigating to the edit page for the specified item.
         * @function handleEdit
         * @memberof editHandler
         * @param {Event} event - The click event from the edit button.
         */
        function handleEdit(event) {
            const clickedButton = event.target;
            const tr = clickedButton.closest("tr");
            const id = tr.id;
            window.location.href = `/admin/add-product-page/edit/${id}`;
        }

        return { setUpEdit, handleEdit };
    })();

    /**
     * Namespace for handling delete actions.
     * @namespace deleteHandler
     */
    const deleteHandler = (() => {

        /**
         * Message displayed when delete operation fails due to database linkage.
         * @type {string}
         */
        const failedDeleteMessage = "Unable to delete since it is linked to a different item in the Data Base";

        /**
         * API endpoint for product deletion.
         * @type {string}
         */
        const url = "/api/admin/product-manager";

        /**
         * Attaches click event listeners to all delete buttons on the page.
         * @function setUpDelete
         * @memberof deleteHandler
         */
        function setUpDelete() {
            const deleteButtons = document.querySelectorAll(".delete-btn");
            deleteButtons.forEach(btn => {
                btn.addEventListener("click", handleDelete);
            });
        }

        /**
         * Removes the DOM element with the given id.
         * @function deleteElementById
         * @memberof deleteHandler
         * @param {string} id - The id of the element to remove.
         * @throws Will throw an error if the element does not exist.
         */
        function deleteElementById(id) {
            const element = document.getElementById(id);
            if (element) {
                element.remove();
            } else {
                throw new Error("Error: Product does not exist!");
            }
        }

        /**
         * Sends a DELETE request to the server and processes the response.
         * Updates the UI based on success or failure.
         * @async
         * @function sendDelete
         * @memberof deleteHandler
         * @param {string} id - The id of the product to delete.
         */
        async function sendDelete(id) {
            const options = {
                method: "DELETE",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ id })
            };

            try {
                const result = await fetch(url, options);
                const jsonData = await result.json();

                if (!result.ok) {
                    alertHandler.failedAlert(
                        `${jsonData.statusCode} - Delete Failed:`,
                        jsonData.error || failedDeleteMessage
                    );
                    throw new Error(`${jsonData.statusCode} - ${jsonData.error || "Unknown error"}`);
                }

                deleteElementById(jsonData.id);
                alertHandler.successAlert("Removed Successfully");
            } catch (e) {
                console.error(e);
            }
        }

        /**
         * Handles delete button click events by preventing default action,
         * retrieving the item id, and invoking sendDelete.
         * @function handleDelete
         * @memberof deleteHandler
         * @param {Event} event - The click event from a delete button.
         */
        function handleDelete(event) {
            event.preventDefault();
            const clickedButton = event.target;
            const tr = clickedButton.closest("tr");
            const id = tr.id;
            sendDelete(id);
        }

        return { setUpDelete, handleDelete };

    })();

    /**
     * Initializes edit and delete handlers and alert setup when DOM is fully loaded.
     */
    document.addEventListener("DOMContentLoaded", () => {
        editHandler.setUpEdit();
        deleteHandler.setUpDelete();
        alertHandler.setUp();
    });

})();
