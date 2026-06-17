/*
package odoru.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // On groupe toutes les erreurs liées aux règles de gestion (HTTP 400)
    @ExceptionHandler({
            NomUtilisateurExistantException.class,
            EmailExistantException.class,
            MotDePasseIncorrectException.class,
            DateInvalideException.class,
            RoleInvalideException.class,
            AptitudeInsuffisanteException.class,
            NoteInvalideException.class,
            BadgeDejaAssocieException.class,
            PresenceDejaEnregistreeException.class
    })
    public ResponseEntity<Object> handleBadRequestExceptions(RuntimeException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Erreur Métier", ex.getMessage());
    }

    // On groupe toutes les erreurs liées à un élément introuvable en base (HTTP 404)
    @ExceptionHandler({
            UtilisateurIntrouvableException.class,
            CoursIntrouvableException.class,
            CompetitionIntrouvableException.class,
            BadgeIntrouvableException.class
    })
    public ResponseEntity<Object> handleNotFoundExceptions(RuntimeException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, "Ressource Introuvable", ex.getMessage());
    }

    // Sécurité de base pour toute autre erreur non prévue (HTTP 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur Serveur", ex.getMessage());
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String error, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
*/