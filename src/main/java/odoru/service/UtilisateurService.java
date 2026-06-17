package odoru.service;

import odoru.entities.Utilisateur;
import odoru.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur inscription(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByNomUtilisateur(utilisateur.getNomUtilisateur())) {
            throw new RuntimeException("Ce nom d'utilisateur est déjà pris");
        }
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }
        utilisateur.getRoles().add(Utilisateur.Role.MEMBER);
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur login(String nomUtilisateur, String motDePasse){
        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        if (!utilisateur.getMotDePasse().equals(motDePasse)) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        return utilisateur;
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(String id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur updateNiveauExpertise(String id, int niveau) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        utilisateur.setNiveauExpertise(niveau);
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur addRole(String id, Utilisateur.Role role) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        utilisateur.getRoles().add(role);
        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUtilisateur(String id) {
        utilisateurRepository.deleteById(id);
    }
}