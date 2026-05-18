package odoru.service;

import odoru.domain.Competition;
import odoru.domain.User;
import odoru.repository.CompetitionRepository;
import odoru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private UserRepository userRepository;

    public Competition createCompetition(Competition competition, String teacherId) {
        if (competition.getDateTime().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new RuntimeException("La date doit être au moins 7 jours après aujourd'hui");
        }

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        if (!teacher.getRoles().contains(User.Role.TEACHER)) {
            throw new RuntimeException("Cet utilisateur n'est pas enseignant");
        }

        if (teacher.getExpertiseLevel() < competition.getTargetLevel()) {
            throw new RuntimeException("L'enseignant n'est pas apte au niveau " + competition.getTargetLevel());
        }

        competition.setTeacherId(teacherId);
        return competitionRepository.save(competition);
    }

    public Competition addResult(String competitionId, String memberId, double score) {
        if (score < 0 || score > 10) {
            throw new RuntimeException("La note doit être entre 0 et 10");
        }

        // Arrondi au 1/10e
        double rounded = Math.round(score * 10.0) / 10.0;

        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Compétition introuvable"));

        competition.getResults().put(memberId, rounded);
        return competitionRepository.save(competition);
    }

    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    public List<Competition> getCompetitionsByLevel(int level) {
        return competitionRepository.findByTargetLevel(level);
    }

    public List<Competition> getCompetitionsByMember(String memberId) {
        return competitionRepository.findAll().stream()
                .filter(c -> c.getResults().containsKey(memberId))
                .toList();
    }
}
