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
    subscribeCanvas();
}

function subscribeCanvas() {
    privateStompClient.connect({}, function (frame) {
        if (document.getElementById('page') != null) return;

        initGuessPanel();

        var gameId = document.getElementById('gameId').value;
        privateStompClient.subscribe('/game/' + gameId, function (result) {
            showImage(result.body);
        });
    });
}

function initGuessPanel(){
    var guessedWordsDiv = document.getElementById('guessedWordsDiv');
    guessedWordsDiv.style.maxHeight="400px";
    guessedWordsDiv.style.overflowY="auto";
}

function send() {
    var canvas = document.getElementById('canvas');
    var dataURL = canvas.toDataURL();
    var gameId = document.getElementById('gameId').value;

    if (canvasContent != dataURL) {
        canvasContent = dataURL;
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