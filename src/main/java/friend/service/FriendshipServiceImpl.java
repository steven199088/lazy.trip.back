package friend.service;

import friend.dao.FriendshipDao;
import friend.dao.FriendshipDaoImpl;
import friend.model.Friendship;
import member.model.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FriendshipServiceImpl implements FriendshipService {

    private FriendshipDao<Friendship> dao;

    public FriendshipServiceImpl() {
        dao = new FriendshipDaoImpl();
    }

    @Override
    public Map<String, String> requestNewFriend(Integer requesterId, Integer addresseeId) {
        String str = "failure";


        if (dao.addFriendship(requesterId, addresseeId)) {
            str = "success";
        }

        Map<String, String> result = new HashMap<>();
        result.put("result", str);
        return result;
    }

    @Override
    public Map<String, String> updateFriendRequestDirectional(Integer requesterId, Integer addresseeId, String updateStatus) {
        Map<String, String> result = new HashMap<>();
        String str = "failure";

        // 將更新狀態字串轉換成狀態代碼
        String statusCode = switch (updateStatus) {
            case "accept" -> "A";
            case "cancel" -> "C";
            case "decline" -> "D";
            default -> throw new IllegalStateException("Unexpected value");
        };

        // 檢查目前申請關係為 R 申請中
        if (dao.getFriendshipStatus(requesterId, addresseeId).compareTo("R") != 0) {
            result.put("result", str);
            return result;
        }

        // 接受或取消時，specifier 即為 requester；拒絕時，specifier 為 addressee
        if (statusCode.equals("A") || statusCode.equals("C")) {
            str = dao.updateFriendship(requesterId, addresseeId, statusCode, requesterId) ? "success" : "failure";
        } else {
            str = dao.updateFriendship(requesterId, addresseeId, statusCode, addresseeId) ? "success" : "failure";
        }

        result.put("result", str);
        return result;
    }


    @Override
    public Map<String, String> blockFriendRequest(Integer specifierId, Integer otherId) {
        Map<String, String> result = new HashMap<>();
        String str = "failure";

        if (dao.getFriendshipStatus(specifierId, otherId).compareTo("A") != 0) {
            result.put("result", str);
            return result;
        }

        if (dao.updateFriendship(specifierId, otherId, "B", specifierId)) {
            str = "success";
        }

        result.put("result", str);
        return result;
    }

    @Override
    public Map<String, String> resetFriendRequest(Integer specifierId, Integer otherId) {
        Map<String, String> result = new HashMap<>();
        String str = "failure";

//		if(dao.getFriendshipStatus(specifierId, otherId).compareTo("A") != 1) {
//			result.put("result", str);
//			return result;
//		}

        if (dao.updateFriendship(specifierId, otherId, "N", specifierId)) {
            str = "success";
        }

        result.put("result", str);
        return result;
    }

    @Override
    public Map<String, String> unfriend(Integer specifierId, Integer otherId) {
        Map<String, String> result = new HashMap<>();
        String str = "failure";

        if (dao.getFriendshipStatus(specifierId, otherId).compareTo("A") != 0) {
            result.put("result", str);
            return result;
        }

        if (dao.updateFriendship(specifierId, otherId, "N", specifierId)) {
            str = "success";
        }

        result.put("result", str);
        return result;
    }

    @Override
    public List<Member> getAllSuggestions(Integer memberId) {
    	return dao.getNonFriendships(memberId);
    }
    
    @Override
    public List<Map<String, String>> getAllFriends(Integer memberId) {
        return dao.getFriendships(memberId, "A");
    }

    @Override
    public List<Map<String, String>> getRequestsWithStatus(Integer memberId, String direction) {
    	
    	List<Map<String, String>> pendingRequests;
    	List<Map<String, String>> resolvedRequests;
    	List<Map<String, String>> combinedList;

        if (direction.equals("sent")) {
        	pendingRequests = dao.getFriendshipsAsRequester(memberId, "R");
        	resolvedRequests = dao.getFriendshipsAsRequesterSpecifier(memberId, "C", memberId);
        	pendingRequests.forEach(pr -> pr.put("request_status", "requested"));
        	resolvedRequests.forEach(rr -> rr.put("request_status", "cancelled"));

        } else {
        	pendingRequests = dao.getFriendshipsAsAddressee(memberId, "R");
        	resolvedRequests = dao.getFriendshipsAsAddresseeSpecifier(memberId, "D", memberId);
        	pendingRequests.forEach(pr -> pr.put("request_status", "requested"));
        	resolvedRequests.forEach(rr -> rr.put("request_status", "declined"));
        }
        
    	combinedList = Stream.of(pendingRequests, resolvedRequests)
    	        .flatMap(x -> x.stream())
    	        .collect(Collectors.toList());
        
        return combinedList;

    }

}
