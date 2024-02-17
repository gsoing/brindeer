const { connectToDatabase, closeDatabaseConnection } = require('../../database/mongo');
const {getUserById, getUsers, getUser} = require("../users/read");
const geolib = require("geolib")

/**
 * Get profiles that are within 100 meters of a user
 */
async function getProfiles(userName) {

    try {

        console.log( userName )
        let userWhoSearch = await getUser(userName)

        console.log( 'userWhoSearch', userWhoSearch )

        if ( userWhoSearch === null ) {
            throw new Error("User not found in database" )
        }

        let users = await getUsers()
        let usersNear = []

        users.map( user => {

            const distance = geolib.getDistance(
                { latitude: userWhoSearch.coordinates.latitude, longitude: userWhoSearch.coordinates.longitude },
                { latitude: user.coordinates.latitude, longitude: user.coordinates.longitude }
            );

            if ( distance  < 100 ) {
                usersNear.push(user)
            }

        })

        console.log("usersNear", usersNear)
        return usersNear

    } catch (error) {
        console.error('Error getting profiles:', error);
        throw error;
    }
}

module.exports = {
    getProfiles,
};