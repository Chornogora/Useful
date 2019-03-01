let address = "http://192.168.0.107:8090";

function callPostToTheServer() {
    let request = new XMLHttpRequest();

    request.open("POST", address + "/WEB_war_exploded/Servlet", true);
    //На практике применяется в технических целях
    request.setRequestHeader("name", "Anthony");

    request.onload = function(){
        let resp = request.response;
        document.getElementById("view").innerText = resp;
    };

    request.send();
}

function callGetToTheServer() {
    let request = new XMLHttpRequest();

    let param = "name=Anthony";

    request.open("GET", address + "/WEB_war_exploded/Servlet?" + param, true);
    //На практике применяется в технических целях
    request.setRequestHeader("header", "Some text in header");

    request.onload = function(){
        let resp = request.response;
        let object = JSON.parse(resp);

        let text = "New user " + object.name + " was created on " + object.creationDate;
        document.getElementById("view").innerText = text;
    };

    request.send();
}