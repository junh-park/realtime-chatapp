let http = require('http')
const url = 'http://localhost:8080'
let stompClient
let selectedUser

function connectToChat(userName) {
	console.log("connecting to chat..")
	let socket = new SockJs(url + '/chat')
	stompClient = Stomp.over(socket)
	stompClient.
	stompClient.connect({}, function (frame) {
		console.log("connected to: " + frame)
		stompClient.subscribe("/topic/messages" + userName, (res) => {
			let data = JSON.parse(res.body)
			console.log(data)
		})
	})
}

function sendMessage(from, text) {
	stompClient.send("/app/chat" + selectedUser, {}, JSON.stringify(
		{
			fromLogin: from,
			message: text
		})
	)
}

function registration() {
	let userName = document.getElementById("userName").value
	$.get(url + "/registration/" + userName, function(res) {
		connectToChat(userName)
	}).fail(function(error) {
		if (error.status === 400) {
			alert("Login is already busy!")
		}
	})
}

function fetchAll() {
	$.get(url + "/fetchAllUsers", function(res) {
		let users = res
		let usersTemplateHtml = ""
		for (let i = 0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i] + '\')"><li class="clearfix">\n' +
                '                <img src="https://rtfm.co.ua/wp-content/plugins/all-in-one-seo-pack/images/default-user-image.png" width="55px" height="55px" alt="avatar" />\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>';
        }
        $('#usersList').html(usersTemplateHTML);
	})
}

function selectUser(userName) {
	selectUser = userName
	console.log(`Selected user is ${userName}`)
	$('#selectedUser').append(`Chat with ${userName}`)
}