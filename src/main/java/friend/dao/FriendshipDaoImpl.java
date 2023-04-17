package friend.dao;

import common.HikariDataSource;
import friend.model.Friendship;
import member.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendshipDaoImpl implements FriendshipDao<Friendship> {

    public FriendshipDaoImpl() {

    }

    @Override
    public boolean addFriendship(Integer requesterId, Integer addresseeId) {
        boolean hasRequested = false;
        String sql = "INSERT INTO friendship (`requester_id`, `addressee_id`, `status_code`, `specifier_id`) VALUES (?, ?, 'R', ?);";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, requesterId);
            ps.setInt(2, addresseeId);
            ps.setInt(3, requesterId);
            hasRequested = ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasRequested;
    }

    @Override
    public boolean updateFriendship(Integer memberIdA, Integer memberIdB, String statusCode, Integer specifierId) {
        boolean hasUpdated = false;
        String sql = "UPDATE friendship SET status_code = ?, specifier_id = ? WHERE (addressee_id = ? AND requester_id = ?) OR (requester_id = ? AND addressee_id = ?);";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, statusCode);
            ps.setInt(2, specifierId);
            ps.setInt(3, memberIdA);
            ps.setInt(4, memberIdB);
            ps.setInt(5, memberIdA);
            ps.setInt(6, memberIdB);
            hasUpdated = ps.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasUpdated;
    }

    @Override
    public String getFriendshipStatus(Integer memberIdA, Integer memberIdB) {
        String statusCode = "U"; // null
        String sql = "SELECT status_code FROM friendship WHERE (addressee_id = ? AND requester_id = ?) OR (requester_id = ? AND addressee_id = ?);";

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, memberIdA);
            ps.setInt(2, memberIdB);
            ps.setInt(3, memberIdA);
            ps.setInt(4, memberIdB);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                statusCode = rs.getString("status_code");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statusCode;
    }

    @Override
    public List<Map<String, String>> getFriendships(Integer memberId, String statusCode) {
        List<Map<String, String>> friends = new ArrayList<>();
        String sql = """
                SELECT member_id, member_account, member_name FROM member\r
                	WHERE member_id IN \r
                (SELECT addressee_id FROM friendship WHERE status_code = ? AND requester_id = ?\r
                	UNION\r
                SELECT requester_id FROM friendship WHERE status_code = ? AND addressee_id = ?);
                """;

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, statusCode);
            ps.setInt(2, memberId);
            ps.setString(3, statusCode);
            ps.setInt(4, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> friend = new HashMap<>();
                friend.put("member_id", rs.getString("member_id"));
                friend.put("member_account", rs.getString("member_account"));
                friend.put("member_name", rs.getString("member_name"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }
    
    @Override
    public List<Member> getNonFriendships(Integer memberId) {
    	List<Member> nonFriends = new ArrayList<>();
    	String sql = "SELECT member_id, member_account, member_name FROM member\r\n"
    			+ "	WHERE member_id NOT IN \r\n"
    			+ "(SELECT addressee_id AS friend_id FROM friendship WHERE requester_id = ?\r\n"
    			+ "	UNION\r\n"
    			+ "SELECT requester_id AS friend_id FROM friendship WHERE addressee_id = ?);";
    	
    	try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, memberId);
        	ps.setInt(2, memberId);
        	ResultSet rs = ps.executeQuery();
        	while (rs.next()) {
                Member nonFriend = new Member();
                nonFriend.setId(Integer.parseInt(rs.getString("member_id")));
                nonFriend.setAccount(rs.getString("member_account"));
                nonFriend.setName(rs.getString("member_name"));
                nonFriends.add(nonFriend);
            }
        	
            } catch (SQLException e) {
                e.printStackTrace();
            }
    	return nonFriends;
    }

    @Override
    public List<Map<String, String>> getFriendshipsAsRequester(Integer requesterId, String statusCode) {
        List<Map<String, String>> friends = new ArrayList<>();
        String sql = """
                SELECT member_id, member_account, member_name FROM member\r
                	WHERE member_id IN \r
                (SELECT addressee_id AS friend_id FROM friendship WHERE requester_id = ? AND status_code = ?);
                """;

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, requesterId);
            ps.setString(2, statusCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> friend = new HashMap<>();
                friend.put("member_id", rs.getString("member_id"));
                friend.put("member_account", rs.getString("member_account"));
                friend.put("member_name", rs.getString("member_name"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }

    @Override
    public List<Map<String, String>> getFriendshipsAsAddressee(Integer addresseeId, String statusCode) {
        List<Map<String, String>> friends = new ArrayList<>();
        String sql = """
                SELECT member_id, member_account, member_name FROM member\r
                	WHERE member_id IN \r
                (SELECT requester_id AS friend_id FROM friendship WHERE addressee_id = ? AND status_code = ?);
                """;

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, addresseeId);
            ps.setString(2, statusCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> friend = new HashMap<>();
                friend.put("member_id", rs.getString("member_id"));
                friend.put("member_account", rs.getString("member_account"));
                friend.put("member_name", rs.getString("member_name"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }

    @Override
    public List<Map<String, String>> getFriendshipsAsRequesterSpecifier(Integer requesterId, String statusCode, Integer specifierId) {
        List<Map<String, String>> friends = new ArrayList<>();
        String sql = """
                SELECT member_id, member_account, member_name FROM member\r
                	WHERE member_id IN \r
                (SELECT addressee_id FROM friendship WHERE requester_id = ? AND status_code = ? AND specifier_id = ?);
                """;

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, requesterId);
            ps.setString(2, statusCode);
            ps.setInt(3, specifierId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> friend = new HashMap<>();
                friend.put("member_id", rs.getString("member_id"));
                friend.put("member_account", rs.getString("member_account"));
                friend.put("member_name", rs.getString("member_name"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }


    @Override
    public List<Map<String, String>> getFriendshipsAsAddresseeSpecifier(Integer addresseeId, String statusCode, Integer specifierId) {
        List<Map<String, String>> friends = new ArrayList<>();
        String sql = """
                SELECT member_id, member_account, member_name FROM member\r
                	WHERE member_id IN \r
                (SELECT requester_id FROM friendship WHERE addressee_id = ? AND status_code = ? AND specifier_id = ?);""";

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, addresseeId);
            ps.setString(2, statusCode);
            ps.setInt(3, specifierId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> friend = new HashMap<>();
                friend.put("member_id", rs.getString("member_id"));
                friend.put("member_account", rs.getString("member_account"));
                friend.put("member_name", rs.getString("member_name"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }

}
