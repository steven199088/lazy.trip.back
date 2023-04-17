package friend.service;

import java.util.List;
import java.util.Map;

import member.model.Member;

public interface FriendshipService {

    Map<String, String> requestNewFriend(Integer requesterId, Integer addresseeId);

    Map<String, String> updateFriendRequestDirectional(Integer requesterId, Integer addresseeId, String updateStatus);

    Map<String, String> blockFriendRequest(Integer specifierId, Integer otherId);

    Map<String, String> resetFriendRequest(Integer specifierId, Integer otherId);

    Map<String, String> unfriend(Integer specifierId, Integer otherId);
    
    List<Member> getAllSuggestions(Integer memberId);

    List<Map<String, String>> getAllFriends(Integer memberId);

    List<Map<String, String>> getRequestsWithStatus(Integer memberId, String direction);
}
