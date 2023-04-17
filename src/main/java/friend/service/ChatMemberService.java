package friend.service;

import friend.model.Chatroom;
import member.model.Member;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ChatMemberService {

    Chatroom createChatroom(List<Integer> membersId);

    boolean addNewChatMembers(List<Integer> membersId, Integer chatroomId);

    boolean removeChatMember(Integer memberId, Integer chatroomId);

    boolean renameChatroom(String name, Integer chatroomId);

    List<Chatroom> getChatroomsByMember(Integer memberId);

    Map<String, Set<Integer>> getChatroomsIdAndChatroomMembersId(Integer memberId);

    List<Chatroom> getChatroomsByMultipleMembers(List<Integer> memberId);

    List<Member> getMembersByChatroom(Integer chatroomId);

    List<Member> searchMembersByText(String searchText);

}
