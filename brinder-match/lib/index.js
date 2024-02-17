const express = require('express');
const usersRoutes = require('./routes/users');
const profilesRoutes = require('./routes/profiles');
const app = express();
const PORT = 3000;

app.use(express.json());

app.use('/api/v1/users', usersRoutes);
app.use('/api/v1/profiles', profilesRoutes);

app.listen(PORT, () => {
    console.log(`Server started on port : ${PORT}`);
});
