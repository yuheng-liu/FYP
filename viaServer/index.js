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
		console.log(userNickname +" : has joined");
	})

	// Event for trip accept
	socket.on('trip_accept', function(tripAccept) {
		console.log("tripAccept received");
		
		socket.broadcast.emit('porter_trip_accept', tripAccept);
	})

	// Event for trip request
	socket.on('trip_request', function(tripRequest){
		console.log("tripRequest received");

		socket.broadcast.emit('patron_trip_request', tripRequest);
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