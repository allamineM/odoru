package odoru.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "competitions")
public class Competition {

    @Id
    private String id;

    private String title;
    private int targetLevel;
    private LocalDateTime dateTime;
    private String teacherId;
    private String location;
    private int durationMinutes;
    private LocalDateTime createdAt;

    // memberId -> note sur 10 (précision 1/10)
    private Map<String, Double> results = new HashMap<>();

    public Competition() {
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getTargetLevel() { return targetLevel; }
    public void setTargetLevel(int targetLevel) { this.targetLevel = targetLevel; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public Map<String, Double> getResults() { return results; }
    public void setResults(Map<String, Double> results) { this.results = results; }
}
