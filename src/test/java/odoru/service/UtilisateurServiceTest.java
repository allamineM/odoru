package odoru.service;

import odoru.entities.Utilisateur;
import odoru.repository.UtilisateurRepository;
import odoru.utilities.EmailExistantException;
import odoru.utilities.NomUtilisateurExistantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurService utilisateurService;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setNom("Massir");
        utilisateur.setPrenom("Abdellah");
        utilisateur.setEmail("abdellah@test.com");
        utilisateur.setNomUtilisateur("abdellah123");
        utilisateur.setMotDePasse("secret");
        utilisateur.setNiveauExpertise(3);
    }

    @Test
    void inscription_succes() {
        when(utilisateurRepository.existsByNomUtilisateur("abdellah123")).thenReturn(false);
        when(utilisateurRepository.existsByEmail("abdellah@test.com")).thenReturn(false);
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        Utilisateur result = utilisateurService.inscription(utilisateur);

        assertNotNull(result);
        assertTrue(result.getRoles().contains(Utilisateur.Role.MEMBER));
        verify(utilisateurRepository, times(1)).save(utilisateur);
    }

    @Test
    void inscription_nomUtilisateurDejaExistant() {
        when(utilisateurRepository.existsByNomUtilisateur("abdellah123")).thenReturn(true);

        NomUtilisateurExistantException ex = assertThrows(NomUtilisateurExistantException.class,
                () -> utilisateurService.inscription(utilisateur));

        assertEquals("Ce nom d'utilisateur est déjà pris", ex.getMessage());
        verify(utilisateurRepository, never()).save(any());
    }

    @Test
    void inscription_emailDejaExistant() {
        when(utilisateurRepository.existsByNomUtilisateur("abdellah123")).thenReturn(false);
        when(utilisateurRepository.existsByEmail("abdellah@test.com")).thenReturn(true);

        EmailExistantException ex = assertThrows(EmailExistantException.class,
                () -> utilisateurService.inscription(utilisateur));

        assertEquals("Cet email est déjà utilisé", ex.getMessage());
        verify(utilisateurRepository, never()).save(any());
    }

    @Test
    void updateNiveauExpertise_succes() {
        utilisateur = spy(utilisateur);
        when(utilisateurRepository.findById("1")).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        Utilisateur result = utilisateurService.updateNiveauExpertise("1", 5);

        assertEquals(5, result.getNiveauExpertise());
        verify(utilisateurRepository, times(1)).save(utilisateur);
    }
}