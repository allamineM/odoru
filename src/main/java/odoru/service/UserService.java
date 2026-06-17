package odoru.service;

import odoru.entities.Utilisateur;
import odoru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Utilisateur inscription(Utilisateur utilisateur) {
        if (userRepository.existsByNomUtilisateur(utilisateur.getNomUtilisateur())) {
            throw new RuntimeException("Ce nom d'utilisateur est déjà pris");
        }
        if (userRepository.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }
        utilisateur.getRoles().add(Utilisateur.Role.MEMBER);
        return userRepository.save(utilisateur);
    }

    public Utilisateur login(String nomUtilisateur, String motDePasse){
        Utilisateur utilisateur = userRepository.findByNomUtilisateur(nomUtilisateur)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        if (!utilisateur.getMotDePasse().equals(motDePasse)) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        return utilisateur;
    }

    public List<Utilisateur> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Utilisateur> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Utilisateur updateNiveauExpertise(String id, int niveau) {
        Utilisateur utilisateur = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        utilisateur.setNiveauExpertise(niveau);
        return userRepository.save(utilisateur);
    }

    public Utilisateur addRole(String id, Utilisateur.Role role) {
        Utilisateur utilisateur = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        utilisateur.getRoles().add(role);
        return userRepository.save(utilisateur);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
