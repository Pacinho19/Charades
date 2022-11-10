var guessClient = null;

function initGuessClient(){
    socket = new SockJS('/ws');
    guessClient = Stomp.over(socket);
    guessClient.connect({}, function (frame) {
        subscribeGuess();
    });
}

function home() {
    window.location.href = '/charades'
};

function subscribeGuess(){
    var gameId = document.getElementById('gameId').value;

    guessClient.subscribe('/guess/' + gameId, function (result) {

        var guessJson = JSON.parse(result.body);

        var guessedWordsDiv = document.getElementById("guessedWordsDiv");
        var divA = document.createElement("div");
        divA.class = "row";
        var guessLabel = document.createElement("h3");
        guessLabel.innerHTML = guessJson.text;
        if (guessJson.correct) guessLabel.style = 'color:green';

        divA.appendChild(guessLabel);
        guessedWordsDiv.appendChild(divA);
        guessedWordsDiv.scrollTop = guessedWordsDiv.scrollHeight;

        checkEndGame(guessJson.correct);
    });
}

function guess() {
    var gameId = document.getElementById('gameId').value;
    var wordElement = document.getElementById('word');
    if (wordElement == null) return;

    var word = wordElement.value;
    if (word.length != wordElement.minLength) {
        showAlert("Word length must be " + wordElement.minLength);
        wordElement.focus();
        return false;
    }

    stompClient.send("/app/guess", {},
        JSON.stringify({ 'gameId': gameId, 'word': word }));

    wordElement.value = '';
}

function showAlert(text) {
    document.getElementById('errorText').innerHTML = text;
    $("#error-alert").fadeTo(2000, 500).slideUp(500, function () {
        $("#error-alert").slideUp(500);
    });
};

function checkEndGame(endGame) {
    if (endGame) {
        var guessForm = document.getElementById('guessForm');
        if (guessForm != null) {
            guessForm.remove();
            document.getElementById('lettersDiv').remove();
        } else {
            disableCanvas();
        }
    }
}

function disableCanvas() {
    clearInterval(sendInterval);
    var canvas = document.getElementById('can');
    var dataURL = canvas.toDataURL();

    var newCanvas = canvas.cloneNode(true);
    canvas.remove();

    var ctx = newCanvas.getContext("2d");

    var image = new Image();
    image.onload = function() {
      ctx.drawImage(image, 0, 0);
    };
    image.src = dataURL;

    document.getElementById('canvasDiv').appendChild(newCanvas);
}

$(document).ready(function () {
    $(window).keydown(function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            guess();
            return false;
        }
    });
});