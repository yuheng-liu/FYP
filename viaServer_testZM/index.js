/* Express initializes app to be a function handler that you can supply 
	to a HTTP server */
const express = require('express'),
http = require('http'),
app = express(),
server = http.createServer(app),
io = require('socket.io').listen(server);

// Define a route handler "/" that gets called when we hit website
app.get('/', function(req, res){
	res.send('Server is running on port 3000');
});


// Make the http server listen on port 3000
server.listen(3000, function(){
	console.log('Node app is running on port 3000');
});


io.on('connection', (socket) => {

	console.log('user connected')

	socket.on('join', function(userNickname) {
		console.log(userNickname + " : has joined the chat")

		socket.broadcast.emit('userjoinedthechat' , userNickname + " : has joined the chat")
	});

	socket.on('disconnect', function(userNickname) {

		console.log(userNickname +' has left')

        socket.broadcast.emit( "userdisconnect" ,' user has left')
	});
});