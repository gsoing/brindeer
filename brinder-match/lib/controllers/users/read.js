const { connectToDatabase, closeDatabaseConnection } = require('../../database/mongo');
const User = require('../../models/users');

async function getUsers() {
    try {
        const db = await connectToDatabase();
        const collection = db.collection('utilisateurs');
        return await collection.find({}).toArray();
    } catch (error) {
        console.error('Error getting users:', error);
        throw error;
    } finally {
        closeDatabaseConnection();
    }
}

module.exports = {
    getUsers
};