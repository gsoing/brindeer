# brinder

Pour créé les 2 services 

curl -i -X POST \
  --url http://localhost:8001/services/ \
  --data 'name=match' \
  --data 'url=http://localhost:8081/api/match'

 curl -i -X POST   --url http://localhost:8001/services/   --data 'name=profiles'   --data 'url=http://localhost:9080/api/vi/profiles'


Pour créé les 2 routes

curl -i -X POST \
    --url http://localhost:8001/services/profiles/routes \
    --data 'paths[]=/api/vi/profiles'


 curl -i -X POST     --url http://localhost:8001/services/match/routes     --data 'paths[]=/api/match'

 curl -i -X POST     --url http://localhost:8001/services/profiles/routes     --data 'paths[]=/api/vi/profiles'

Test :

Sans les api keys 

quand je tape sur l'url 
http://localhost:8000/api/match/get-all-match
j'obtiens l'erreur :

brinder-kong-12024/02/15 20:54:17 [error] 1260#0: *2350 connect() failed (111: Connection refused) while connecting to upstream, client: 172.21.0.1, server: kong, request: "GET /api/match/get-all-match HTTP/1.1", upstream: "http://127.0.0.1:8081/api/match/get-all-match", host: "localhost:8000"
2024/02/15 20:54:17 [error] 1260#0: *2350 connect() failed (111: Connection refused) while connecting to upstream, client: 172.21.0.1, server: kong, request: "GET /api/match/get-all-match HTTP/1.1", upstream: "http://127.0.0.1:8081/api/match/get-all-match", host: "localhost:8000"
2024/02/15 20:54:17 [error] 1260#0: *2350 connect() failed (111: Connection refused) while connecting to upstream, client: 172.21.0.1, server: kong, request: "GET /api/match/get-all-match HTTP/1.1", upstream: "http://127.0.0.1:8081/api/match/get-all-match", host: "localhost:8000"
2024/02/15 20:54:17 [error] 1260#0: *2350 connect() failed (111: Connection refused) while connecting to upstream, client: 172.21.0.1, server: kong, request: "GET /api/match/get-all-match HTTP/1.1", upstream: "http://127.0.0.1:8081/api/match/get-all-match", host: "localhost:8000"
2024/02/15 20:54:17 [error] 1260#0: *2350 connect() failed (111: Connection refused) while connecting to upstream, client: 172.21.0.1, server: kong, request: "GET /api/match/get-all-match HTTP/1.1", upstream: "http://127.0.0.1:8081/api/match/get-all-match", host: "localhost:8000"
2024/02/15 20:54:17 [error] 1260#0: *2350 connect() failed (111: Connection refused) while connecting to upstream, client: 172.21.0.1, server: kong, request: "GET /api/match/get-all-match HTTP/1.1", upstream: "http://127.0.0.1:8081/api/match/get-all-match", host: "localhost:8000"
172.21.0.1 - - [15/Feb/2024:20:54:17 +0000] "GET /api/match/get-all-match HTTP/1.1" 502 220 "-" "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 OPR/106.0.0.0 (Edition std-1)"

J'ai essayé de résoudre cette erreur mais je n'ai pas résussis.

pour créé un user :

curl -d "username=enzo" http://localhost:8001/consumers

pour créé l'apikey de l'user :

curl -X POST http://localhost:8001/consumers/enzo/key-auth -d ''

cela m'a créé l'api key = HcGVcLmqOU99XTZIg0Wjh5cxIgVQeUvq

Test : 
avec l'api key 
http://localhost:8000/api/match/get-all-match?apikey=HcGVcLmqOU99XTZIg0Wjh5cxIgVQeUvq

j'ai la meme erreur que plutôt
