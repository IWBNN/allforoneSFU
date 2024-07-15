package ac.su.allforonesfu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDto {
    private String roomId;
    private Map<String, WebSocketSession> clients = new ConcurrentHashMap<>();
}
