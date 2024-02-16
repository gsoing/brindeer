const { connectToDatabase, closeDatabaseConnection } = require('../../database/mongo');
const {getUserById} = require("../users/read");

async function getProfiles() {

    try {
        const db = await connectToDatabase();
        const collection = db.collection('utilisateurs');
        let userName

        await getUserById(userName)

        return await collection.find({
            username: userName,
            location: {
                $near: {
                    $geometry: {
                        type: "Point",
                        coordinates: [parseFloat(longitude), parseFloat(latitude)]
                    },
                    $maxDistance: 100
                }
            }
        });
    } catch (error) {
        console.error('Error getting profiles:', error);
        throw error;
    } finally {
        closeDatabaseConnection()
    }
}