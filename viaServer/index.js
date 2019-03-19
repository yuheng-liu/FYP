const app = require('express')();
const server = require('http').Server(app);
const io = require('socket.io')(server);

app.get('/', (req, res) => {
	res.send('Server is running on port 5000')
});

io.on('connection', (socket) => {
	// Handle event 'Join'
	socket.on('join', (userNickname) => {
		console.log(userNickname +" : has joined server");
		socket.userNickname = userNickname;
	})

	/* 					  *
	 * Events from Porter *
	 *					  */
	socket.on('location_update_porter', (locationUpdate) => {
		console.log("porter location update");
		socket.broadcast.emit('porter_location_update', locationUpdate);
	})

	socket.on('porter_state_change', (state) => {
		console.log("porter state changed" + state);
		socket.broadcast.emit('porter_state_changed', state);
	})

	/* 					  *
	 * Events from Patron *
	 *					  */
	socket.on('location_update_patron', (locationUpdate) => {
		console.log("PATRON LOCATION UPDATE");
		socket.broadcast.emit('patron_location_update', locationUpdate);
	})

	socket.on('patron_state_changed', (state) => {
		console.log("patron state changed" + state);
		socket.broadcast.emit('patron_state_change', state);
	})

	// Handle event 'disconnect'
	socket.on('disconnect', () => {
    	console.log(socket.userNickname +" : has disconnected from server");
    })
});

server.listen(5000, () => {
	console.log('Server is listening on port 5000');
});