const express = require('express');
const router = express.Router();
const { setUser } = require('../controllers/users/create');
const { getUsers } = require('../controllers/users/read');
const User = require('../models/users');

router.get('/', async (req, res) => {
    try {
        const users = await getUsers();
        console.log( 'Get /users')
        res.status(200).json(users);
    } catch (error) {
        console.error('Error getting users:', error);
        res.status(500).json({ message: "An error occurred while getting users." });
    }
});

router.post('/', async (req, res) => {
    try {
        const { name, coordinates } = req.body;

        if (!name || !coordinates) {
            return res.status(400).json({ message: "Please provide a name and a coordinates." });
        }

        const newUser = new User(name, coordinates);
        await setUser(newUser);

        console.log( 'Post /users')
        res.status(201).json({ message: "User created.", user: newUser });
    } catch (error) {
        console.error('Error creating user:', error);
        res.status(500).json({ message: "An error occurred while creating the user." });
    }
});

module.exports = router;