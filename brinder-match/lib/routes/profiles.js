const express = require("express");
const {getProfiles} = require("../controllers/profil/read");
const router = express.Router();

router.get('/', async (req, res) => {
    try {

        console.log( "/profiles - userName" , req.currentUser )
        let users = await getProfiles(req.currentUser)

        if ( users.length > 0 ) {
            res.status(200).json(users);
        } else {
            res.status(200).json({ message:"There is no profiles next to you..."} );
        }

    } catch (error) {
        console.error('Error matching users:', error);
        res.status(500).json({ message: "An error occurred while matching users." });
    }
});

module.exports = router;