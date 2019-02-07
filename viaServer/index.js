const app = require('express')();
const server = require('http').Server(app);
const io = require('socket.io')(server);

app.get('/', (req, res) => {
	res.send('Chat Server is running on port 3000')
});

io.on('connection', function(socket) {
	// Show new connection
	console.log('A new user connected!!!');

	// Handle event 'Join'
	socket.on('join', function(userNickname) {
		console.log(userNickname +" : has joined")

		socket.broadcast.emit('newMessage',userNickname +" : has joined the server")
	})

	// Handle event 'messagedetection'
	socket.on('messagedetection', function(senderNickname,messageContent) {
		console.log(senderNickname + " : " + messageContent)
		
		//create a message object and send the message to all users including the sender
    	let  message = {"message":messageContent, "senderNickname":senderNickname}
		socket.emit('message', message);
	})

	// Handle event 'disconnect'
	socket.on('disconnect', function(userNickname) {
    	console.log(userNickname +" : has left")
    	// socket.broadcast.emit( "userdisconnect" ,userNickname +' user has left')
    })
});

server.listen(3000, function(){
	console.log('Server is listening on port 3000');
});