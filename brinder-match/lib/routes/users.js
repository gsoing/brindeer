const express = require('express');
const router = express.Router();
const { setUser } = require('../controllers/users/create');
const { getUsers } = require('../controllers/users/read');
const User = require('../models/users');

router.get('/', async (req, res) => {
    try {
        const users = await getUsers();
        res.status(200).json(users);
    } catch (error) {
        console.error('Error getting users:', error);
        res.status(500).json({ message: "An error occurred while getting users." });
    }
});

router.post('/', async (req, res) => {
    try {
        const { name, password } = req.body;

        if (!name || !password) {
            return res.status(400).json({ message: "Please provide a name and a password." });
        }

        const newUser = new User(name, password);
        await setUser(newUser);

        res.status(201).json({ message: "User created.", user: newUser });
    } catch (error) {
        console.error('Error creating user:', error);
        res.status(500).json({ message: "An error occurred while creating the user." });
    }
});

module.exports = router;