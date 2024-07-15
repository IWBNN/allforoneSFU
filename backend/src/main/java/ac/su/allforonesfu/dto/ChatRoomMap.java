package ac.su.allforonesfu.dto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoomMap {
    private static ChatRoomMap instance = new ChatRoomMap();
    private Map<String, ChatRoomDto> chatRooms = new ConcurrentHashMap<>();

    private ChatRoomMap() {}

    public static ChatRoomMap getInstance() {
        return instance;
    }

    public Map<String, ChatRoomDto> getChatRooms() {
        return chatRooms;
    }
}
