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
	socket.on('bid_request', (bidRequest) => {
		console.log("bidRequest received");
		socket.broadcast.emit('porter_bid_request', bidRequest);
	})

	socket.on('accept_trip', (acceptTrip) => {
		console.log("acceptTrip received");
		socket.broadcast.emit('porter_accept_trip', acceptTrip);
	})

	socket.on('location_update_porter', (locationUpdate) => {
		console.log("porter location update");
		socket.broadcast.emit('porter_location_update', locationUpdate);
	})

	/* 					  *
	 * Events from Patron *
	 *					  */
	socket.on('trip_request', (tripRequest) => {
		console.log("tripRequest received");
		socket.broadcast.emit('patron_trip_request', tripRequest);
	})

	socket.on('accept_bidder', (acceptBidder) => {
		console.log("acceptBidder received");
		socket.broadcast.emit('patron_bid_success', acceptBidder);
	})

	socket.on('location_update_patron', (locationUpdate) => {
		console.log("PATRON LOCATION UPDATE");
		socket.broadcast.emit('patron_location_update', locationUpdate);
	})

	// Handle event 'disconnect'
	socket.on('disconnect', () => {
    	console.log(socket.userNickname +" : has disconnected from server");
    })
});

server.listen(5000, () => {
	console.log('Server is listening on port 5000');
});