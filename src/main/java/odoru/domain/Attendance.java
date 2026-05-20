package odoru.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "attendances")
public class Attendance {

    @Id
    private String id;

    private String membreId;
    private String coursId;
    private LocalDateTime scanneLe;

    public Attendance() {
        this.scanneLe = LocalDateTime.now();
    }

    public String getId() { return id; }

    public String getMembreId() { return membreId; }
    public void setMembreId(String membreId) { this.membreId = membreId; }

    public String getCoursId() { return coursId; }
    public void setCoursId(String coursId) { this.coursId = coursId; }

    public LocalDateTime getScanneLe() { return scanneLe; }
}
