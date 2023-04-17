package friend.repository;

import friend.model.Chatroom;
import member.model.Member;

import java.util.List;

public interface ChatroomMemberRepository {

    Integer addChatroom(List<Integer> membersId);

    boolean addMembersToChatroom(List<Integer> membersId, Integer chatroomId);

    boolean deleteMemberFromChatroom(Integer memberId, Integer chatroomId);

    boolean updateChatroomName(String name, Integer chatroomId);

    Chatroom getChatroom(Integer chatroomId);

    List<Chatroom> getChatrooms(Integer memberId);

    List<Member> getChatroomMembers(Integer chatroomId);

    List<Member> getMemberByNameOrUsername(String searchText);

}
