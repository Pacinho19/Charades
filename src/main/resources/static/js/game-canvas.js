var stompClient = null;
var privateStompClient = null;
var canvasContent = null;

function initStomp() {
    socket = new SockJS('/ws');
    privateStompClient = Stomp.over(socket);
    stompClient = Stomp.over(socket);
    initGuessClient();
}

function init() {
    initStomp();
    initCanvas();
    subscribeCanvas();
}

function subscribeCanvas() {
    privateStompClient.connect({}, function (frame) {
        var gameId = document.getElementById('gameId').value;
        privateStompClient.subscribe('/players/game/' + gameId, function (result) {
            showImage(result.body);
        });
    });
}

function send() {
    var canvas = document.getElementById('can');
    var dataURL = canvas.toDataURL();
    var gameId = document.getElementById('gameId').value;

    if (canvasContent != dataURL) {
        canvasContent =  dataURL;
        stompClient.send("/app/canvas", {},
            JSON.stringify({ 'canvas': dataURL, 'gameId': gameId }));
    }
}

function showImage(content) {
    var imageDiv = document.getElementById("imageDiv");
    var img = document.getElementById("image");
    if (img == null) {
        img = document.createElement("img");
        img.id = "image";
        img.width = "800";
        img.height = "400";
        imageDiv.appendChild(img);
        img.classList.add("center");
    }
    img.src = content;
}

var sendInterval = null;

function initSendCanvasTask() {
    sendInterval = setInterval(function () {
        send();
    }, 1000);
}