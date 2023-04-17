package friend.service;

import friend.repository.FriendMemberRepository;
import friend.repository.FriendMemberRepositoryImpl;
import friend.repository.MemberPagerAndSorter;
import member.model.Member;

import java.util.List;
import java.util.Map;

public class FriendMemberServiceImpl implements FriendMemberService {

    private FriendMemberRepository repository;

    public FriendMemberServiceImpl() {
        this.repository = new FriendMemberRepositoryImpl();
    }
    
	@Override
	public boolean createFriendRequest(Integer requesterId, Integer addresseeId) {
		//TODO 加上過濾來自封鎖名單的邀請
		return repository.addFriendship(requesterId, addresseeId);
	}

	@Override
	public boolean acceptFriendRequest(Integer requesterId, Integer addresseeId) {
		return repository.updateFriendship(requesterId, addresseeId, "A");
	}

	@Override
	public boolean blockFriendRequest(Integer requesterId, Integer addresseeId) {
		return repository.updateFriendship(requesterId, addresseeId, "B");
	}

	@Override
	public boolean removeFriendRequest(Integer requesterId, Integer addresseeId) {
		return repository.deleteFriendship(requesterId, addresseeId);
	}

	@Override
	public Map<String, String> checkFriendshipBetween(Integer specifierId, Integer otherId) {
		return repository.getFriendshipBetween(specifierId, otherId);
	}

	@Override
	public List<Member> getFriends(Integer memberId, MemberPagerAndSorter pagerAndSorter) {
		return repository.getMembersByFriendship(memberId, "A", pagerAndSorter);
	}

	@Override
	public List<Member> getFriends(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter) {
		return repository.getMembersByFriendship(memberId, "A", searchText, pagerAndSorter);
	}

	@Override
	public List<Member> getBlockedMembers(Integer memberId, MemberPagerAndSorter pagerAndSorter) {
		return repository.getMembersByFriendship(memberId, "B", pagerAndSorter);
	}

	@Override
	public List<Member> getBlockedMembers(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter) {
		return repository.getMembersByFriendship(memberId, "B", searchText, pagerAndSorter);
	}

	@Override
	public List<Member> getSentRequests(Integer memberId, MemberPagerAndSorter pagerAndSorter) {
		return repository.getMembersByRequest(memberId, "sent", pagerAndSorter);
	}

	@Override
	public List<Member> getSentRequests(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter) {
		return repository.getMembersByRequest(memberId, "sent", searchText, pagerAndSorter);
	}

	@Override
	public List<Member> getReceivedRequests(Integer memberId, MemberPagerAndSorter pagerAndSorter) {
		return repository.getMembersByRequest(memberId, "received", pagerAndSorter);
	}

	@Override
	public List<Member> getReceivedRequests(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter) {
		return repository.getMembersByRequest(memberId, "received", searchText, pagerAndSorter);
	}

	@Override
	public List<Member> getFriendSuggestions(Integer memberId, MemberPagerAndSorter pagerAndSorter) {
		return repository.getMembersByNonFriendship(memberId, pagerAndSorter);
	}

	@Override
	public List<Member> getFriendSuggestions(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter) {
		return repository.getMembersByNonFriendship(memberId, searchText, pagerAndSorter);
	}

}
