package ac.su.allforonesfu.service;

import org.springframework.stereotype.Service;
import ac.su.sfu.dto.ChatRoomDto;
import ac.su.sfu.dto.ChatRoomMap;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
public class MsgChatService {

    public String addUser(Map<String, ChatRoomDto> chatRooms, String roomId, String sender) {
        ChatRoomDto room = chatRooms.get(roomId);
        if (room == null) {
            room = new ChatRoomDto();
            room.setRoomId(roomId);
            chatRooms.put(roomId, room);
        }

        String userUUID = UUID.randomUUID().toString();
        // Assuming a clients map exists in ChatRoomDto
        room.getClients().put(userUUID, null);
        return userUUID;
    }

    public String findUserNameByRoomIdAndUserUUID(Map<String, ChatRoomDto> chatRooms, String roomId, String userUUID) {
        ChatRoomDto room = chatRooms.get(roomId);
        if (room != null) {
            for (Map.Entry<String, WebSocketSession> entry : room.getClients().entrySet()) {
                if (entry.getKey().equals(userUUID)) {
                    return entry.getKey();  // Or whatever data structure holds the username
                }
            }
        }
        return null;
    }

    public void delUser(Map<String, ChatRoomDto> chatRooms, String roomId, String userUUID) {
        ChatRoomDto room = chatRooms.get(roomId);
        if (room != null) {
            room.getClients().remove(userUUID);
        }
    }

    public ArrayList<String> getUserList(Map<String, ChatRoomDto> chatRooms, String roomId) {
        ChatRoomDto room = chatRooms.get(roomId);
        if (room != null) {
            return new ArrayList<>(room.getClients().keySet());
        }
        return new ArrayList<>();
    }

    public String isDuplicateName(Map<String, ChatRoomDto> chatRooms, String roomId, String username) {
        ChatRoomDto room = chatRooms.get(roomId);
        if (room != null && room.getClients().containsKey(username)) {
            return username;
        }
        return null;
    }
}
