package org.gso.brinder.match.service;

import lombok.RequiredArgsConstructor;
import org.gso.brinder.common.exception.NotFoundException;
import org.gso.brinder.match.model.Coordonnee;
import org.gso.brinder.match.model.UserModel;
import org.gso.brinder.match.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    //private final CustomUserRepository customUserRepository;
    private final UserRepository userRepository;

    public Coordonnee convert(Coordonnee c){
        //1 degré de latitude en mètre
        double lat_m = 111320;
        //1 degré de longitude en mètre
        double long_m = 40075000;

        double latitude_metter = Math.abs(c.getLatitude() * lat_m);
        double longitude_metter = Math.abs((c.getLongitude() * long_m) * Math.cos(c.getLatitude()) / 360);
        return new Coordonnee(longitude_metter, latitude_metter);
    }

    public boolean inTheRadius(Coordonnee coord1, Coordonnee coord2){

        //Si ces 2 coordonnées sont dans un rayon de 100m
        if (Math.abs(coord1.getLongitude() - coord2.getLongitude()) < 100 && Math.abs(coord1.getLatitude() - coord2.getLatitude()) < 100){
            System.out.println("IN");
            return true;
        }

        //Si ces 2 coordonnées ne sont pas dans un rayon de 100m
        else{
            System.out.println("OUT");
            return false;
        }
    }

    //Cette fonction va comparer 1 coordonnée à une liste de coordonnée
    public boolean inTheRadiusAll(Coordonnee coord1, List<Coordonnee> list_coord2){
        List<Coordonnee> match = new ArrayList<>();

        for (Coordonnee coordonnee : list_coord2) {
            //Si une coordonnée est dans le même cercle que celle de l'utilisateur alors on l'ajoute dans la liste
            if (inTheRadius(coord1, coordonnee)) {
                match.add(coordonnee);
            }
        }
        return !match.isEmpty();
    }

    public List<String> searchMatch(String id) {
        ArrayList<Coordonnee> allUserCoord = new ArrayList<>();
        Optional<UserModel> user = userRepository.findById(id);
        List<String> idLIst = new ArrayList<>();

        if (user.isPresent()) {
            Coordonnee user_coord = convert(new Coordonnee(user.get().getLongitude(), user.get().getLatitude()));

            //Traitement de la liste de User
            List<UserModel> allUser = userRepository.findAll(); //customUserRepository.getAllUser();

            for (UserModel userModel : allUser) {
                if (!(userModel.getId().equals(user.get().getId()))) {
                    Coordonnee coordonnee = new Coordonnee(userModel.getLongitude(), userModel.getLatitude());
                    Coordonnee coordonnee2 = convert(coordonnee);
                    allUserCoord.add(coordonnee2);
                    boolean result = inTheRadiusAll(user_coord, allUserCoord);

                    if(result){
                        idLIst.add(userModel.getId());
                    }
                    allUserCoord.clear();
                }
            }
            return idLIst;
        }

        System.out.print("No user present in DB");
        return null;
    }

    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> NotFoundException.DEFAULT);
    }

    public UserModel update(UserModel user) {
        UserModel userModel = this.getUser(user.getId());
        userModel.setId(user.getId());
        return userRepository.save(user);
    }
}
