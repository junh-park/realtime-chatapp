var stompClient = null;

$(document).ready(() => {
    console.log("Index page is ready")
    connect()

    $("#send").click(() => {
        sendMessage()
    })
})

function connect() {
    var socket = new SockJS('/mychatapplication')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/messages', (message) => {
            showMessage(JSON.parse(message.body).messageContent)
            $("#message").val('')
        })
    })
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>")
}

function sendMessage() {
    stompClient.send("/app/messages", {}, JSON.stringify({'messageContent': $("#message").val()}))
}

