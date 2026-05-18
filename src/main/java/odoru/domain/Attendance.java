package odoru.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "attendances")
public class Attendance {

    @Id
    private String id;

    private String memberId;
    private String courseId;
    private LocalDateTime scannedAt;

    public Attendance() {
        this.scannedAt = LocalDateTime.now();
    }

    public String getId() { return id; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public LocalDateTime getScannedAt() { return scannedAt; }
}
