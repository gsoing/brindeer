const { connectToDatabase } = require('../../database/mongo');
const User = require('../../models/users');

const users = [
    new User('utilisateur1', 'mot_de_passe1'),
    new User('utilisateur2', 'mot_de_passe2')
];

async function insertUser() {
    try {
        const db = await connectToDatabase();
        const collection = db.collection('utilisateurs');
        await collection.insertMany(users);
        console.log('Users added');
    } catch (error) {
        console.error('Error to add a user on /users :', error);
    }
}
