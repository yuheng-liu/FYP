// 'use strict';

// require('dotenv').config();

const express = require('express'),
http = require('http'),
app = express(),
server = http.createServer(app),
io = require('socket.io').listen(server);

app.get('/', (req, res) => {

    res.send('viaServer is running on port 3000')
});

server.listen(3000, () => {
    console.log('Node app is running on port 3000')
});

io.on('connection', (socket) => {

    console.log('user connected')

    socket.on('join', function(userNickname) {

        console.log(userNickname + " : has joined the server" )

        // socket.broadcast.emit will fire to all users except sender
        socket.broadcast.emit('userjoinedtheserver', userNickname + " : has joined the server" )
    });

    socket.on('messagedetection', (senderNickname, messageContent) => {

        // log message in console

        console.log(senderNickname + " : " + messageContent)

        // create a message object
        let message = {"message":messageContent, "senderNickname":senderNickname}

        // send the message to the client side
        socket.emit('message', message)
    });


    socket.on('disconnect', function() {
    
        console.log('user has left')

        socket.broadcast.emit("userdisconnect", ' user has left')
    });
});



// const exphbs = require('express-handlebars');
// const bodyParser = require('body-parser');
// const app = express();

// // set views lookup folder
// app.set('views', __dirname + '/views');
// app.engine('handlebars', exphbs({extname:'handlebars', defaultLayout:'main', layoutsDir: __dirname + '/views/layouts'}));
// app.set('view engine', 'handlebars');

// const server = require('http').Server(app);
// const models = require('./models');
// const config = require('./config');

// // create driver document upload directory
// const mkdirp = require('mkdirp');
// mkdirp.sync(config.DRIVER_DOCUMENT_UPLOAD);

// // Config app instance
// if (isNaN(process.env.NODE_APP_INSTANCE)) {
//     app.set('port', (process.env.PORT || 5000));
// } else {
//     if (typeof process.env.PORT != 'undefined') {
//         app.set('port', (process.env.PORT + parseInt(process.env.NODE_APP_INSTANCE)))
//     } else {
//         app.set('port', (5000 + parseInt(process.env.NODE_APP_INSTANCE)));
//     }
// }

// // Process application/x-www-form-urlencoded
// app.use(bodyParser.urlencoded({ extended: false }));

// // Process application/json
// app.use(bodyParser.json());

// // Index route
// app.get('/', (req, res) => {
//     let body = 'Auto';
//     if (process.env.NODE_ENV != 'production') {
//         body += ` ${config.APP_VERSION} on ${config.APP_VERSION_DATE}`;
//     }
//     res.send(body);
// });

// // app.get('/test', (req, res) => {
// //     res.render('test');
// // });

// // routes
// app.use('/api', require('./routes'));
// app.use('/app-services', require('./routes/app_services'));
// app.use('/manage-services', require('./routes/manage_services'));

// // socket.io
// require('./sockets')(server, app);

// // sync the models then start server
// models.sync().then(() => {
//     // Spin up the server
//     server.listen(app.get('port'), () => {
//         try {
//             process.send('ready');
//         } catch (e) {
//             console.log("Unable to send process signal to pm2");
//         }
//         console.log('running on port', app.get('port'))
//     });
// });