const express = require('express');
const jwt = require('jsonwebtoken');

const usersRoutes = require('./routes/users');
const profilesRoutes = require('./routes/profiles');
const {setUser} = require("./controllers/users/create");
const {getUser} = require("./controllers/users/read");

const app = express();
const PORT = 3000;

app.use(express.json());

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.use(async (req, res, next) => {

    try {
        const token = req.headers.authorization.split(' ')[1];

        if (token) {

            const decodedToken = jwt.decode(token);

            let userExist = await getUser(decodedToken.given_name)

            if( !userExist ) {

                let newUser = {
                    "username": decodedToken.given_name,
                    "coordinates": {
                        "latitude": 48.857064616,
                        "longitude": 2.3522219
                    }
                }

                await setUser(newUser);
                console.log("User created")
            } else {
                console.log("User already exist")
            }

            req.currentUser = decodedToken.given_name;
        }
    } catch (error) {
        console.error('Error with token:', error);
        return res.status(500).json({ message:"An error occurred."});
    }

    next();
});


app.use('/api/v1/matches/users', usersRoutes);
app.use('/api/v1/matches/profiles', profilesRoutes);

app.listen(PORT, () => {
    console.log(`Server started on port : ${PORT}`);
});
