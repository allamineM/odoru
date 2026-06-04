package odoru.service;

import odoru.domain.User;
import odoru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User inscription(User user) {
        if (userRepository.existsByNomUtilisateur(user.getNomUtilisateur())) {
            throw new RuntimeException("Ce nom d'utilisateur est déjà pris");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }
        user.getRoles().add(User.Role.MEMBER);
        return userRepository.save(user);
    }

    public User login(String nomUtilisateur, String motDePasse){
        User user = userRepository.findByNomUtilisateur(nomUtilisateur)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        if (!user.getMotDePasse().equals(motDePasse)) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User updateNiveauExpertise(String id, int niveau) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        user.setNiveauExpertise(niveau);
        return userRepository.save(user);
    }

    public User addRole(String id, User.Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
