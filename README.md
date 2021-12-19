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
![image](./images/api-key.png)

- OAuth2
Avec le scope userId: 
![image](./images/OAuth2.png)

- Création du scope userId:
![image](./images/Scope-userId.png)

- Liste des Scopes:
![image](./images/scopes.png)

- création du swagger-role(mapping):
![image](./images/swagger-role.png)

- Utilisation du rôle swagger-user:
![image](./images/swagger-role-in-code.png)

# Team Members
- <b style="color:blue">KONE Yaya N'golo</b>
