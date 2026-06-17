package odoru;

import odoru.entities.Utilisateur;
import odoru.repository.UtilisateurRepository;
import odoru.service.UserService;
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
    private UserService userService;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setNom("Dupont");
        utilisateur.setPrenom("Marie");
        utilisateur.setEmail("marie@test.com");
        utilisateur.setNomUtilisateur("marie123");
        utilisateur.setMotDePasse("secret");
        utilisateur.setNiveauExpertise(3);
    }

    @Test
    void inscription_succes() {
        when(utilisateurRepository.existsByNomUtilisateur("marie123")).thenReturn(false);
        when(utilisateurRepository.existsByEmail("marie@test.com")).thenReturn(false);
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        Utilisateur result = userService.inscription(utilisateur);

        assertNotNull(result);
        assertTrue(result.getRoles().contains(Utilisateur.Role.MEMBER));
        verify(utilisateurRepository, times(1)).save(utilisateur);
    }

    @Test
    void inscription_nomUtilisateurDejaExistant() {
        when(utilisateurRepository.existsByNomUtilisateur("marie123")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.inscription(utilisateur));

        assertEquals("Ce nom d'utilisateur est déjà pris", ex.getMessage());
        verify(utilisateurRepository, never()).save(any());
    }

    @Test
    void inscription_emailDejaExistant() {
        when(utilisateurRepository.existsByNomUtilisateur("marie123")).thenReturn(false);
        when(utilisateurRepository.existsByEmail("marie@test.com")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.inscription(utilisateur));

        assertEquals("Cet email est déjà utilisé", ex.getMessage());
        verify(utilisateurRepository, never()).save(any());
    }

    @Test
    void getAllUsers_retourneListe() {
        when(utilisateurRepository.findAll()).thenReturn(List.of(utilisateur));

        List<Utilisateur> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(utilisateurRepository, times(1)).findAll();
    }

    @Test
    void updateNiveauExpertise_succes() {
        utilisateur = spy(utilisateur);
        when(utilisateurRepository.findById("1")).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        Utilisateur result = userService.updateNiveauExpertise("1", 5);

        assertEquals(5, result.getNiveauExpertise());
        verify(utilisateurRepository, times(1)).save(utilisateur);
    }

    @Test
    void addRole_succes() {
        when(utilisateurRepository.findById("1")).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);

        Utilisateur result = userService.addRole("1", Utilisateur.Role.TEACHER);

        assertTrue(result.getRoles().contains(Utilisateur.Role.TEACHER));
        verify(utilisateurRepository, times(1)).save(utilisateur);
    }
}
