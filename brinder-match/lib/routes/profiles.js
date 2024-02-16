const express = require("express");
const {getUsers} = require("../controllers/users/read");
const router = express.Router();

router.get('/', async (req, res) => {
    try {

        let users

        res.status(200).json(users);
    } catch (error) {
        console.error('Error matching users:', error);
        res.status(500).json({ message: "An error occurred while matching users." });
    }
});

module.exports = router;