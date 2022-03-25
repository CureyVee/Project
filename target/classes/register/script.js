
async function registerUser(event){
    //Prevents reloading 
    //event.preventDefault();

    console.log("form submitted");
    
    //Finds the elements from the dom 
    let usernameInputElem = document.getElementById("inputUsername");
    let passwordInputElem = document.getElementById("inputPassword");
    let firstnameInputElem = document.getElementById("inputFirstName");
    let lastnameInputElem = document.getElementById("inputLastName");
    let emailInputElem = document.getElementById("inputEmail");
    let roleInputElem = document.getElementById("inputRole");

    //Places elements as an object 
    let user = {
        username: usernameInputElem.value,
        password: passwordInputElem.value,
        firstname: firstnameInputElem.value,
        lastname: lastnameInputElem.value,
        email: emailInputElem.value,
        roleId: parseInt(roleInputElem.value)
    }
    console.log(user)

    //send the http request to java
    let response = await fetch("http://localhost:7777/register", {
        method: "POST",
        body: JSON.stringify(user)
    })

    //retrieve the response body
    let responseBody = await response.json();


    //logic after response body
    if(responseBody.success == false){
        let messageElem = document.getElementById("uncessessful")
        messageElem.innerText = responseBody.message
    }else{
        //redirect page to login page if credentials were successful
        window.location = `http://localhost:7777/login/index.html`
    }


}