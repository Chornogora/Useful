webSocket = new WebSocket('ws://127.0.0.1:8080/Train_Sockets/point');

webSocket.onmessage = function(evt) { 
	alert(evt.data);
};

function send(message){
    webSocket.send(message);
}
