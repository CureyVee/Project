let user;

window.onload =  async function(){

    let response = await fetch(`${domain}/session`);

    let responseBody = await response.json();

    if(!responseBody.success){ // if a session was not found redirect to login
        window.location = "../";
    }

    user = responseBody.data; 

    let messageElem = document.getElementById("welcomeMessage")
    messageElem.innerText = `Welcome ${user.firstname} ${user.lastname} ! Here are all the reimbursement requests:`

    getAllforAll()
}

async function getAllforAll(){
    let response = await fetch(`${domain}/reimb?userId=${user.id}`);

    let responseBody = await response.json();

    let reimbursements = responseBody.data;

    let listContainerElem = document.getElementById("list-container");
    listContainerElem.innerText="";

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
        <div class="list-title">Author User Id : ${reimb.authorId}</div>
        <div class="list-title">Author Username : ${reimb.authorUsername}</div>
        <div class="list-title">Type: ${reimb.type}</div>
        <div class="list-title">Amount : ${reimb.amount}</div>
        <div class="list-title">Time Submitted : ${timeSubmitted}</div>
        <div class="list-title">Status: ${statusString}</div>
        <form class="ESForm" id="editStatusForm-${reimb.reimbId}" onsubmit="changeStatus(event)" >
            <div id="editStatus">Edit Reimbursement Status :</div>
            <select id="newStatus-${reimb.reimbId}" class="changeStatus" required>
                <option value=""> Choose New Status...</option>
                <option value="2">Approved</option>
                <option value="3">Denied</option>
                <option value="1">Pending</option>
            </select>
            
            <button class="ESFBtn" type="submit" id="change-status-btn-${reimb.reimbId}" >Change Status</button>
        </form>
    </div> `

    listContainerElem.appendChild(listCardElem);

}

async function filterByStatus(event){
    event.preventDefault();
    let statusfilterInputElem = document.getElementById("statusFilterInput")

    let response = await fetch("http://localhost:7777/reimb/filter/status?userId=${user.id}&statusId=${statusfilterInputElem.value}");

    let responseBody = await response.json();

    let reimbursements = responseBody.data;

    let listContainerElem = document.getElementById("list-container");
    listContainerElem.innerText="";
    
    reimbursements.forEach(reimb => {
        createReimbInfoCard(reimb)
    });

}

async function filterByType(event){
    event.preventDefault();
    let typefilterInputElem = document.getElementById("typeFilterInput")

    let response = await fetch("http://localhost:7777/reimb/filter/type?userId=${user.id}&typeId=${typefilterInputElem.value}");

    let responseBody = await response.json();

    let reimbursements = responseBody.data;

    let listContainerElem = document.getElementById("list-container");
    listContainerElem.innerText="";
    
    reimbursements.forEach(reimb => {
        createReimbInfoCard(reimb)
    });
}

async function changeStatus(event){
    event.preventDefault()

    //retrieving reimbursement id:
    let changeStatusButton = event.target;
    let reimbId = changeStatusButton.id.substring("editStatusForm-".length);

    let statusIdElem = document.getElementById(`newStatus-${reimbId}`);
    let statusId = statusIdElem.value;

    // once i have the reimb id i can send a request to change the status
    let response = await fetch("http://localhost:7777/reimb/changestatus?userId=${user.id}&reimbId=${reimbId}&statusId=${statusId}",{
        method: "PATCH"
    });

    let responseBody = await response.json();

    let reimbursements = responseBody.data;

    getAllforAll()
}

function displayApproved(){
    console.log("List will be displayed of approved people with descriptions")
}

function displayPending(){
    console.log("List will be displayed of pending people with descriptions")
}

function displayDenied(){
    console.log("List will be displayed of denied people with descriptions")
}
