# TP2 Architecture cloud pour mobile

## Tindeer le final

# Build 
<b style="color:Orange">Warning:</b><b> prérequis Java version 11 </b>
- ### brinder-common
  Se positionner dans le package <b>brinder-common</b> puis exécuter les commandes suivantes: </br>
  ```console
  1. $ ./gradlew build
  ```
  ```console
  2. $ ./gradlew tasks
  ```
  ```console
  3. $ ./gradlew publishToMavenLocal
  ```
- ### brinder-profile-api
  Se positionner dans le package <b>brinder-profile-api</b> puis exécuter les commandes suivantes: </br>
  ```console
  1. $ ./gradlew build
  ```
  ```console
  2. $ ./gradlew tasks
  ```
  ```console
  3. $ ./gradlew bootBuildImage
- ### brinder-match-api
  Se positionner dans le package <b>brinder-match-api</b> puis exécuter les commandes suivantes: </br>
  ```console
  1. $ ./gradlew build
  ```
  ```console
  2. $ ./gradlew tasks
  ```
  ```console
  3. $ ./gradlew bootBuildImage

# Launch
Lancer les services avec la commande suivante:
  ```console
   $ docker-compose up -d
  ```

# Demo
### On a deux méthodes d'authentification: 

- apiKey 

![api-key](https://user-images.githubusercontent.com/60603990/146693231-afc749d9-a495-4b50-a6c9-75e2c3a32b0f.PNG)


- OAuth2

Avec le scope userId:

![OAuth2](https://user-images.githubusercontent.com/60603990/146693246-7b78a3fc-4e79-4f9f-b351-2766fba9e549.PNG)


- Création du scope userId:

![Scope-userId](https://user-images.githubusercontent.com/60603990/146693253-3eb34ab9-aa07-41d8-8c2c-b99d6f9f5dbe.PNG)


- Liste des Scopes:

![scopes](https://user-images.githubusercontent.com/60603990/146693257-aedc0c9a-f70d-43d3-80d2-90c419acbe4a.PNG)


- création du swagger-role(mapping):

![swagger-role](https://user-images.githubusercontent.com/60603990/146693270-33a4bef1-a101-448f-a8ed-83921d631e2d.PNG)


- Utilisation du rôle swagger-user:

![swagger-role-in-code](https://user-images.githubusercontent.com/60603990/146693276-c1077b75-ff58-4b68-bf58-e77e34e1ebb5.PNG)

# Team Members
- <b style="color:blue">KONE Yaya N'golo</b>
