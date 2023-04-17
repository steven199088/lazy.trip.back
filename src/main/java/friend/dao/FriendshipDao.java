package friend.dao;

import java.util.List;
import java.util.Map;

import member.model.Member;

public interface FriendshipDao<Friendship> {

    boolean addFriendship(Integer requesterId, Integer addresseeId);

    boolean updateFriendship(Integer memberIdA, Integer memberIdB, String statusCode, Integer specifierId);

    String getFriendshipStatus(Integer memberIdA, Integer memberIdB);

    List<Map<String, String>> getFriendships(Integer memberId, String statusCode);
    
    List<Member> getNonFriendships(Integer memberId);

    List<Map<String, String>> getFriendshipsAsRequester(Integer requesterId, String statusCode);

    List<Map<String, String>> getFriendshipsAsAddressee(Integer addresseeId, String statusCode);

    List<Map<String, String>> getFriendshipsAsRequesterSpecifier(Integer requesterId, String statusCode, Integer specifierId);

    List<Map<String, String>> getFriendshipsAsAddresseeSpecifier(Integer addresseeId, String statusCode, Integer specifierId);

}
