package ai.maum.mcl.skins.api.proai.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashMap;

@Getter
@Setter
public class ChatroomDetail {
    @Schema(description = "챗봇룸목록ID", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "챗봇룸ID", example = "1")
    private Long room_id;

    @Schema(description = "순서", example = "CARD")
    private Long seq;

    @Schema(description = "ROLE", example = "user/assistant")
    private String role;

    @Schema(description = "채팅내용", example = "까꿍 놀이는 보호자가 아기의 얼굴을 가린 후 다시 보여주는 놀이입니다. 단순한 놀이이지만 아기의 인지 발달, 사회적 상호작용, 정서 발달에 중요한 역할을 합니다.")
    private String content;

    @Schema(description = "피드백", example = "대답 꼬라지 하고는.")
    private String feedback;

    @Schema(description = "등록일", example = "2024-01-24 14:10:01")
    private Timestamp created_at;

    @UpdateTimestamp
    @Schema(description = "수정일", example = "2024-01-24 14:10:02")
    private Timestamp updated_at;

    public ChatroomDetail(Long roomId, Long seq, String role, String content) {
        this.room_id = room_id;
        this.seq = seq;
        this.role = role;
        this.content = content;
    }

    public ChatroomDetail() {
    }

    public ChatroomDetail(HashMap<String,String> infoMap) {
        this.room_id = Long.valueOf(infoMap.get("room_id")==null?"":infoMap.get("room_id"));
        this.seq = Long.valueOf(infoMap.get("seq")==null?"":infoMap.get("seq"));
        this.role = infoMap.get("role")==null?"":infoMap.get("role");
        this.content = infoMap.get("content")==null?"":infoMap.get("content");
    }
}
