package ai.maum.mcl.skins.api.chat.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chat {
    private String input;
    private String output;

    public Chat(String input, String output) {
    }
}
