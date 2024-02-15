const { MongoClient } = require('mongodb');
const config = require('../config/config');

let client = null;

async function connectToDatabase() {
    if (!client) {
        client = new MongoClient(config.mongoUrl);
        await client.connect();
    }
    return client.db(config.dbName);
}

function closeDatabaseConnection() {
    if (client) {
        client.close();
        client = null;
    }
}

module.exports = {
    connectToDatabase,
    closeDatabaseConnection
};