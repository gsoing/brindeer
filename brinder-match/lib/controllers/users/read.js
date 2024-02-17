const { connectToDatabase, closeDatabaseConnection } = require('../../database/mongo');
const User = require('../../models/users');

async function getUsers() {
    try {
        const db = await connectToDatabase();
        const collection = db.collection('utilisateurs');
        return await collection.find({}).toArray();
    } catch (error) {
        console.error('Error fetching users:', error);
        throw error;
    } finally {
        closeDatabaseConnection();
    }
}

async function getUser(userName) {
    try {
        const db = await connectToDatabase();
        const collection = db.collection('utilisateurs');
        return await collection.findOne({ "username": userName });
    } catch (error) {
        console.error("Error fetching user by ID:", error);
        return false;
    } finally {
        closeDatabaseConnection();
    }
}

module.exports = {
    getUsers,
    getUser
};