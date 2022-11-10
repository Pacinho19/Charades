var stompClient = null;

function initStomp(){
    socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    initGuessClient();
}

function init(){
    initStomp();
}