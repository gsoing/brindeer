const { connectToDatabase, closeDatabaseConnection} = require('../../database/mongo');

async function setUser( user ) {

    try {

        const db = await connectToDatabase();
        const collection = db.collection('utilisateurs');
        await collection.insertOne(user)

        console.log('Users added');

    } catch (error) {
        console.error('Error to add a user on /users :', error);
    } finally {
        closeDatabaseConnection()
    }
}

module.exports = {
    setUser
}
