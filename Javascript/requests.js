let address = "http://192.168.0.107:8090";

function callPostToTheServer() {
    let request = new XMLHttpRequest();

	let param = "name=Anthony";

    request.open("POST", address + "/Servlet?" + param, true);

    request.onload = function(){
        let resp = request.response;
        document.getElementById("view").innerText = resp;
    };

    request.send();
}

function callGetToTheServer() {
    let request = new XMLHttpRequest();

    let param = "name=Anthony";

    request.open("GET", address + "/Servlet?" + param, true);

    request.onload = function(){
        let resp = request.response;
        let object = JSON.parse(resp);

        let text = "New user " + object.name + " was created on " + object.creationDate;
        document.getElementById("view").innerText = text;
    };

    request.send();
}
