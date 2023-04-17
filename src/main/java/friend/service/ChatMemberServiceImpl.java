package friend.service;

import friend.controller.ChatServer;
import friend.model.Chatroom;
import friend.repository.ChatroomMemberRepository;
import friend.repository.ChatroomMemberRepositoryImpl;
import member.model.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatMemberServiceImpl implements ChatMemberService {

    private ChatroomMemberRepository chatroomMemberRepository;

    public ChatMemberServiceImpl() {
        this.chatroomMemberRepository = new ChatroomMemberRepositoryImpl();
    }

    @Override
    public Chatroom createChatroom(List<Integer> membersId) {
        boolean canCreateNewChatroom = false;
        Chatroom createdChatroom = null;

        // 檢查是否輸入的會員已有共同的聊天室，而不能新增
        List<List<Integer>> listOfCurrentChatroomsId = membersId
                .stream()
                .map(id -> chatroomMemberRepository.getChatrooms(id))
                .map(list -> list.stream()
                        .map(Chatroom::getId)
                        .collect(Collectors.toList()))
                .toList();
        List<Integer> refList = listOfCurrentChatroomsId.get(0);

        for (int i = 1; i < listOfCurrentChatroomsId.size(); i++) {
            System.out.printf("refList = %s, check if it intersects %s\n", refList, listOfCurrentChatroomsId.get(i));
            refList.retainAll(listOfCurrentChatroomsId.get(i));
            if (refList.size() == 0) {
                canCreateNewChatroom = true;
                break;
            }
        }

        if (canCreateNewChatroom) {
            Integer chatroom = chatroomMemberRepository.addChatroom(membersId);
            createdChatroom = chatroomMemberRepository.getChatroom(chatroom);
            //TODO 即時通知其他正在上線的聊天室成員產生新聊天室；是否該移至 ChatroomController ?
            ChatServer.notifyNewChatroom(createdChatroom);
        }
        return createdChatroom;
    }

    @Override
    public boolean addNewChatMembers(List<Integer> membersId, Integer chatroomId) {
        return chatroomMemberRepository.addMembersToChatroom(membersId, chatroomId);
    }

    @Override
    public boolean removeChatMember(Integer memberId, Integer chatroomId) {
        return chatroomMemberRepository.deleteMemberFromChatroom(memberId, chatroomId);
    }

    @Override
    public boolean renameChatroom(String name, Integer chatroomId) {
        return chatroomMemberRepository.updateChatroomName(name, chatroomId);
    }

    @Override
    public List<Chatroom> getChatroomsByMember(Integer memberId) {
        return chatroomMemberRepository.getChatrooms(memberId);
    }

    @Override
    public Map<String, Set<Integer>> getChatroomsIdAndChatroomMembersId(Integer memberId) {
        Map<String, Set<Integer>> resultMap = new HashMap<>();

        Set<Integer> chatroomsId = chatroomMemberRepository.getChatrooms(memberId).stream()
                .map(Chatroom::getId)
                .collect(Collectors.toSet());
        Set<Integer> chatroomMembersId = chatroomsId.stream()
                .map(chatroomId -> chatroomMemberRepository.getChatroomMembers(chatroomId))
                .flatMap(List::stream)
                .map(Member::getId)
                .collect(Collectors.toSet());

        resultMap.put("chatroomsId", chatroomsId);
        resultMap.put("chatroomMembersId", chatroomMembersId);
        return resultMap;
    }

    @Override
    public List<Chatroom> getChatroomsByMultipleMembers(List<Integer> memberId) {
        return null;
    }

    @Override
    public List<Member> getMembersByChatroom(Integer chatroomId) {
        return chatroomMemberRepository.getChatroomMembers(chatroomId);
    }

    @Override
    public List<Member> searchMembersByText(String searchText) {
        return chatroomMemberRepository.getMemberByNameOrUsername(searchText);
    }
}
