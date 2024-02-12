package Chatting;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomId;
    private List<ChatHandlerObject> participants = new ArrayList<>();

    public Room(int id) {
        this.roomId = id;
    }

    public int getId() {
        return roomId;
    }

    public void addParticipant(ChatHandlerObject participant) {
        participants.add(participant);
    }

    public void clearParticipant() {
        participants.clear();
    }
    public void removeParticipant(ChatHandlerObject participant) {
        participants.remove(participant);
    }

    public List<ChatHandlerObject> getParticipants() {
        return participants;
    }

    public int participantsSize() {
        return participants.size();
    }
}