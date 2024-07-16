package ac.su.allforonesfu.service;

import org.springframework.stereotype.Service;
import ac.su.sfu.dto.ChatRoomMap;

@Service
public class ChatServiceMain {

    public void plusUserCnt(String roomId) {
        // Implement logic to increment user count in a room
        // For example: get the room from ChatRoomMap and increment a user count field
    }

    public void minusUserCnt(String roomId) {
        // Implement logic to decrement user count in a room
        // For example: get the room from ChatRoomMap and decrement a user count field
    }

    public Map<String, Object> findAllRoom() {
        // Implement logic to find all rooms
        return ChatRoomMap.getInstance().getChatRooms();
    }
}
