package friend.service;

import friend.repository.MemberPagerAndSorter;
import member.model.Member;

import java.util.List;
import java.util.Map;

public interface FriendMemberService {

    boolean createFriendRequest(Integer requesterId, Integer addresseeId);

    boolean acceptFriendRequest(Integer requesterId, Integer addresseeId);

    boolean blockFriendRequest(Integer requesterId, Integer addresseeId);

    boolean removeFriendRequest(Integer requesterId, Integer addresseeId);

    Map<String, String> checkFriendshipBetween(Integer specifierId, Integer otherId);

    List<Member> getFriends(Integer memberId, MemberPagerAndSorter pagerAndSorter);

    List<Member> getFriends(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter);

    List<Member> getSentRequests(Integer memberId, MemberPagerAndSorter pagerAndSorter);

    List<Member> getSentRequests(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter);

    List<Member> getReceivedRequests(Integer memberId, MemberPagerAndSorter pagerAndSorter);

    List<Member> getReceivedRequests(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter);

    List<Member> getBlockedMembers(Integer memberId, MemberPagerAndSorter pagerAndSorter);

    List<Member> getBlockedMembers(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter);

    List<Member> getFriendSuggestions(Integer memberId, MemberPagerAndSorter pagerAndSorter);

    List<Member> getFriendSuggestions(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter);
}
