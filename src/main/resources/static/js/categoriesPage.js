/**
 * Module for handling addition and deletion of categories in the admin UI.
 * It allows adding new categories via API and removing existing ones,
 * updating the UI and displaying success or error alerts accordingly.
 * @module categoryHandler
 */
(function(){

    /**
     * Encapsulates logic for adding a category.
     * @namespace addCategoryHandler
     */
    const addCategoryHandler = (()=>{

        /** @type {HTMLInputElement} */
        const input = document.getElementById('category-input');
        /** @type {HTMLElement} */
        const list = document.getElementById('category-list');
        /** @type {string} */
        const errorMessage = "Unable to add category retry adding or try again later";

        /**
         * Appends a new category item to the list in the DOM.
         * @function addCategoryToList
         * @memberof addCategoryHandler
         * @param {{id: string, categoryName: string}} category - The category object returned by the API.
         */
        function addCategoryToList(category){

            console.log(category)

            const li = document.createElement('li');
            li.className = "list-group-item d-flex align-items-center";
            li.id = category.id;

            const span = document.createElement('span');
            span.innerHTML = `<strong>Category:</strong> ` + category.categoryName;

            const button = document.createElement('button');
            button.className = "btn ms-auto border-0";
            button.innerHTML = `
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                  <path fill="none" stroke="#e91818" stroke-linecap="round" stroke-linejoin="round" stroke-width="2.5"
                        d="M14 11v6m-4-6v6M6 7v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V7M4 7h16M7 7l2-4h6l2 4"/>
                </svg>
            `;

            button.addEventListener("click", deleteHandler.deleteCategory);

            li.appendChild(span);
            li.appendChild(button);
            list.appendChild(li);

        }

        /**
         * Sends a POST request to add a category and updates the UI.
         * @async
         * @function addCategory
         * @memberof addCategoryHandler
         * @param {Event} event - The click event from the submit button.
         */
        async function addCategory(event){

            event.preventDefault();

            const url = "/api/admin/category";

            try{

                const data = {categoryName : input.value.trim()};
                const body = {method : "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(data)};

                const result = await fetch(url, body);
                const jsonData = await result.json();

                if(!result.ok){
                    alertHandler.failedAlert("Error: " + jsonData.statusCode, errorMessage)
                    throw new Error("Error" + (jsonData.error || "Unknown error"));
                }

                input.value = "";
                addCategoryToList(jsonData.category);
                alertHandler.successAlert("Category Added Successfully!")

            }catch(e){
                console.error(e);
            }

        }

        return {addCategory}

    })()

    /**
     * Encapsulates logic for deleting a category.
     * @namespace deleteHandler
     */
    const deleteHandler = (()=>{

        /** @type {string} */
        const URL = "/api/admin/category";

        /**
         * Sends a DELETE request to remove a category and updates the UI.
         * @async
         * @function deleteCategory
         * @memberof deleteHandler
         * @param {Event} event - The click event from a delete button.
         */
        async function deleteCategory(event){

            event.preventDefault();

            const btn = event.target;
            const li = btn.closest('li');
            const id = li.id;

            try{

                const sendObject = {method : 'DELETE',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({ id: id })
                };

                const result = await fetch(URL, sendObject);
                const json = await result.json();

                if(!result.ok){
                    alertHandler.failedAlert(json.statusCode + " - " + json.error, "Unable to delete the category - already linked");
                    throw new Error(json.statusCode + " - " + json.error + " - " + json.message);
                }

                li.remove();
                alertHandler.successAlert("Removed Successfully!");

            }catch(e){
                console.error("Error: " + e);
            }

        }

        return {deleteCategory}
    })()

    // Attach event listeners after DOM has loaded
    document.addEventListener('DOMContentLoaded', ()=>{

        const buttons = document.querySelectorAll('.delete-btn')
        buttons.forEach((btn)=>{btn.addEventListener("click", deleteHandler.deleteCategory)})

        const submitBtn = document.getElementById("submit");
        submitBtn.addEventListener("click", addCategoryHandler.addCategory);

        document.getElementById("failedAlertCloseBtn").addEventListener("click", alertHandler.closeFailedButton);
        document.getElementById("successAlertCloseBtn").addEventListener("click", alertHandler.closeSuccessButton);

    })

})()
