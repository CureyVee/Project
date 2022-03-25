let user;

window.onload =  async function(){

    let response = await fetch(`${domain}/session`);

    let responseBody = await response.json();

    if(!responseBody.success){ // if a session was not found redirect to login
        window.location = "../";
    }

    user = responseBody.data; 

    let messageElem = document.getElementById("welcomeMessage")
    messageElem.innerText = `Welcome ${user.firstname} ${user.lastname} ! Here are your past tickets:`

    getAllReimbursementsForUser()
}

async function getAllReimbursementsForUser(){

    let response = await fetch(`${domain}/reimb?userId=${user.id}`);

    let responseBody = await response.json();

    console.log(responseBody)

    let reimbursements = responseBody.data;


    reimbursements.forEach(reimb => {
        createReimbInfoCard(reimb)
    });


}

function createReimbInfoCard(reimb){
    let statusString = "";

    switch (reimb.statusId){
        case 1:
            statusString = "Pending"
            break
        case 2:
            statusString = "Approved"
            break
        case 3:
            statusString = "Denied"
            break
    }

    let listContainerElem = document.getElementById("list-container");

    let listCardElem = document.createElement("div");
    listCardElem.className = "info-card"

    timeSubmitted = new Date(reimb.timeSubmitted).toDateString()

    listCardElem.innerHTML = `
    <div id="infoCard">
        <div class="list-title">Reimbursement Id : ${reimb.reimbId}</div>
        <div class="list-title">Type: ${reimb.type}</div>
        <div class="list-title">Amount : $${reimb.amount}</div>
        <div class="list-title">Date Submitted : ${timeSubmitted}</div> 
        <div class="list-title">Status: ${statusString}</div>
    </div>`

    listContainerElem.appendChild(listCardElem);

}


//creating a new reimbursement using the popup form
async function createNewReimb(event){ 
    event.preventDefault();

    let newReimbAmountInputElem = document.getElementById("inputAmount");
    let newReimbTypeInputElem = document.getElementById("inputType");

    let reimbToCreate = 
    {   amount : newReimbAmountInputElem.value, 
        authorId : user.id,
        typeId : newReimbTypeInputElem.value
    }

    let response = await fetch(`${domain}/reimb/new`,{
        method: "POST",
        body: JSON.stringify(reimbToCreate)
    })

    let responseBody = await response.json();

    let messageElem = document.getElementById("message")
        messageElem.innerText = responseBody.message
    
    console.log(responseBody)

    

    createReimbInfoCard(responseBody.data);

}

// for the pop up "Create New Reimbersement" form 
function openForm() {
    document.getElementById("new-reimb").style.display = "block";
}

function closeForm() {
    let messageElem = document.getElementById("message")
        messageElem.innerText = ""
    document.getElementById("new-reimb").style.display = "none";

}

//allows us to end our session and logout
async function logout(){
    let response = await fetch(`${domain}/session`, {
        method: "DELETE"
    });

    window.location = "../";
}