var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.get('/', (req, res) => {
	res.send('Chat Server is running on port 3000')
});

io.on('connection', function(socket) {
	// Show new connection
	console.log('A new user connected!!!');

	// Handle event 'Join'
	socket.on('join', function(userNickname) {
		console.log(userNickname +" : has joined the chat "  )

		//socket.broadcast.emit('userjoinedthechat',userNickname +" : has joined the chat ")
	})

	// Handle event 'messagedetection'
	socket.on('messagedetection', function(senderNickname,messageContent) {
		console.log(senderNickname + " : " + messageContent)
		
		//create a message object and send the message to all users including the sender
    	let  message = {"message":messageContent, "senderNickname":senderNickname}
		socket.emit('message', message);
	})

	// Handle event 'disconnect'
	socket.on('disconnect', function() {
    	console.log( 'user has left ')
    	socket.broadcast.emit( "userdisconnect" ,' user has left')
    })
});

http.listen(3000, function(){
	console.log('Node app is listening on port 3000');
});