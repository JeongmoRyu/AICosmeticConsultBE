package ai.maum.mcl.skins.api.chat.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatHistory {
    private Long seq;
    private String input;
    private String output;

    public ChatHistory(Long seq, Object o, Object o1) {
    }
}
