var db = db.getSiblingDB('brinder');

db.createCollection('match');
db.createCollection('profiles');
db.match.createIndex({ location: '2dsphere' });

db.match.insertMany(
    [
        {
            "username": "enzo liblin",
            "location": {
              "type": "Point",
              "coordinates": [ -73.856077, 40.848447 ]
            }
        },
        {
            "username": "theo la",
            "location": {
              "type": "Point",
              "coordinates": [ -74.005974, 40.712776 ]
            }
        },
        {
            "username": "sarah ici",
            "location": {
              "type": "Point",
              "coordinates": [ -78.036871, 39.907192 ]
            }
        },
        {
            "username": "nab oh",
            "location": {
              "type": "Point",
              "coordinates": [ -77.036871, 38.907192 ]
            }
        },
        {
            "username": "xiao ping",
            "location": {
              "type": "Point",
              "coordinates": [ -76.036871, 37.907192 ]
            }
        }
    ]
)

db.profiles.insertMany(
  [
    {
      "userId": "1",
      "mail": "test@example.com",
      "age": 13,
      "firstName": "enzo",
      "lastName": "liblin",
      "created": "2024-02-16T15:05:31.487Z",
      "modified": "2024-02-16T15:05:31.497Z"
    },
    {
      "userId": "2",
      "mail": "user@example.com",
      "age": 18,
      "firstName": "nab",
      "lastName": "bat",
      "created": "2024-02-16T15:05:31.487Z",
      "modified": "2024-02-16T15:05:31.507Z"
    }
  ]
)