# brindeer

# Groupe 3
# GROUP MEMBERS:
# DONFACK ANAELLE
# KAMTA MERVEILLE

#Stapes of Keyloack configuration
Aller sur Clients:
- cliquer client ID : tindeer
  Dans Settings Onglet
- Remplacer http://localhost:8080/tinderr par http://localhost:9080/*
- Mettre Service Account and Authorization Enabled à On
- Save les modifications
Dans Credentials onglet:
- Regenerate le secret
- Le conserver quelque part et l'appeler dans le docker_compose grâce à la 
  variable d'environnement keycloak_credentials_secret

Dans l'onglet de Rôles:
	- Creer un nouveau role : tindeer-role
	
	Manage -> Users
	 - 	Creer un nouvel  User
	 - Dans l'onglet Role Mapping de l'user toto que l'on vient de creer : l'assigner le role tindeer-role
  <img src="../image/role.png" alt="Image d'assignation de role ">
  <img src="../image/role2.png" alt="Image d'assignation de role ">

