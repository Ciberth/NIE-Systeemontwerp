var stompClient = null;
connect()

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        
        // subscribe to the web socket topics
        stompClient.subscribe('/topic/parking_code', function (parkingCode) {
            console.log('Connected: ' + parkingCode.body);
            showParkingCode(parkingCode.body);
        });
        
        stompClient.subscribe('/topic/registration_details', function (registration) {
            console.log('Connected: ' + registration.body);         
        	showRegistrationDetails(JSON.parse(registration.body));
        });

        // tell the server the client has connected
        stompClient.send("/app/connected", {}, registrationId);       
    });
}

function showParkingCode(parkingCode) {
    $("#parking_code").html("<tr><td>parking code: </td><td>" + parkingCode + "</td></tr>"  );  
}



/**
 * 
 * @param details e.g. 
 * 					{
						"bedAssignment":"{\"id\":4,\"room\":{\"id\":2,\"ward\":\"emergency_ward\"}}",
						"invoice":"{\"result\":true,\"hospitalStay\":1}"
					}
 */
function showRegistrationDetails(details) {
	// show what bed the patient has been assigned to
	if (details["bedAssignment"] != null){
		showBedAssignment(JSON.parse(details["bedAssignment"]));  
	}
	
    // show the invoice result
	if (details["invoice"] != null){
		showInvoiceResult(JSON.parse(details["invoice"]));
	}
}

/**
 * 
 * @param bedAssignment e.g. {\"id\":4,\"room\":{\"id\":2,\"ward\":\"emergency_ward\"}}
 * @returns
 */
function showBedAssignment(bedAssignment){
	console.log('bedAssignment =' + bedAssignment);
	$("#registration_details").html(""); 
    if (bedAssignment["room"] != null){
    	$("#registration_details").append(
    		"<tr><td>Ward </td><td>" + bedAssignment["room"]["ward"] + "</td></tr>"  +
    		"<tr><td>Room </td><td>" + bedAssignment["room"]["id"] + "</td></tr>" +
    		"<tr><td>Bed </td><td>"  + bedAssignment["id"] + "</td></tr>"  +
    		"");
    } else {
    	$("#registration_details").append("<tr><td>Failed to assign bed </td><td></td></tr>")
    }
}

/**
 * 
 * @param invoice e.g. {\"result\":true,\"hospitalStay\":1}
 * @returns
 */
function showInvoiceResult(invoice){
	console.log('invoice =' + invoice);
    
    if (invoice["result"]){
    	$("#invoice_details").html("<tr><td>Invoice approved </td></tr>");
    } else {
    	$("#invoice_details").html("<tr><td>Invoice failed </td></tr>");
    }
}


$(function () {
    $( "#confirm" ).click(function() {  stompClient.send("/app/confirm", {}, registrationId); });
   
});



function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}


