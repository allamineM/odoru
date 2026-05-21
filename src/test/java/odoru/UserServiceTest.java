package odoru;

import odoru.domain.User;
import odoru.repository.UserRepository;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setNom("Dupont");
        user.setPrenom("Marie");
        user.setEmail("marie@test.com");
        user.setNomUtilisateur("marie123");
        user.setMotDePasse("secret");
        user.setNiveauExpertise(3);
    }

    @Test
    void inscription_succes() {
        when(userRepository.existsByNomUtilisateur("marie123")).thenReturn(false);
        when(userRepository.existsByEmail("marie@test.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.inscription(user);

        assertNotNull(result);
        assertTrue(result.getRoles().contains(User.Role.MEMBER));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void inscription_nomUtilisateurDejaExistant() {
        when(userRepository.existsByNomUtilisateur("marie123")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.inscription(user));

        assertEquals("Ce nom d'utilisateur est déjà pris", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void inscription_emailDejaExistant() {
        when(userRepository.existsByNomUtilisateur("marie123")).thenReturn(false);
        when(userRepository.existsByEmail("marie@test.com")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.inscription(user));

        assertEquals("Cet email est déjà utilisé", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void getAllUsers_retourneListe() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void updateNiveauExpertise_succes() {
        user = spy(user);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateNiveauExpertise("1", 5);

        assertEquals(5, result.getNiveauExpertise());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void addRole_succes() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.addRole("1", User.Role.TEACHER);

        assertTrue(result.getRoles().contains(User.Role.TEACHER));
        verify(userRepository, times(1)).save(user);
    }
}
