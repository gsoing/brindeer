readme through the practical work 

do the following steps : 

1- cd brinder-common 
2- docker build -t brinder-common .
3- cd .. 
4- docker compose build 
5- docker network create kong-net
6 docker compose up -d




pour les tests de match/{distance}
j'ai eu un probleme avec kong je n'arrive pas  a requeter mon microservice mathc autravers de kong mais
les tests peuvent s'effectuer dans l'url du swagger 

  - url: http://localhost:9090/api/v1
    description: Environnement de test match-api

il faut créer plusieurs users avec des des coordonnées différentes en utilisant 
/match/createorupdate

et il faut faire les étapes suivante dans le container de mongo : 
1- mongosh
2- use brinder 
3- db.matchedUser.createIndex({"geoCoordinates.location": "2dsphere"}); // car mon indexation geoindex via code ne marchait pas.


