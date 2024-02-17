const express = require('express');
const usersRoutes = require('./routes/users');
const profilesRoutes = require('./routes/profiles');
const app = express();
const PORT = 3000;

app.use(express.json());

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.use('/api/v1/matches/users', usersRoutes);
app.use('/api/v1/matches/profiles', profilesRoutes);

app.listen(PORT, () => {
    console.log(`Server started on port : ${PORT}`);
});

