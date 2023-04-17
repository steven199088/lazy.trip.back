package friend.repository;

import common.HikariDataSource;
import member.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendMemberRepositoryImpl implements FriendMemberRepository {
	
	@Override
	public boolean addFriendship(Integer requesterId, Integer addresseeId) {
        boolean hasAdded = false;
        String sql = "INSERT INTO friendship (`requester_id`, `addressee_id`, `status_code`) VALUES (?, ?, 'R');";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, requesterId);
            ps.setInt(2, addresseeId);
            hasAdded = ps.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasAdded;
	}

	@Override
	public boolean updateFriendship(Integer requesterId, Integer addresseeId, String statusCode) {
        boolean hasUpdated = false;
        String sql = "UPDATE friendship SET status_code = ? WHERE requester_id = ? AND addressee_id = ?;";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, statusCode);
            ps.setInt(2, requesterId);
            ps.setInt(3, addresseeId);
            hasUpdated = ps.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasUpdated;
    }

    @Override
    public boolean deleteFriendship(Integer requesterId, Integer addresseeId) {
        boolean hasDeleted = false;
        String sql = "DELETE FROM friendship WHERE requester_id = ? and addressee_id = ?;";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, requesterId);
            ps.setInt(2, addresseeId);
            hasDeleted = ps.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasDeleted;
    }

    @Override
    public Map<String, String> getFriendshipBetween(Integer specifierId, Integer otherId) {
        String sql = """
                SELECT requester_id, addressee_id, status_code from lazy.friendship
                \tWHERE (requester_id = ? AND addressee_id = ?) OR (requester_id = ? AND addressee_id = ?);
                """;
        Map<String, String> map = new HashMap<>();
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, specifierId);
            ps.setInt(2, otherId);
            ps.setInt(3, otherId);
            ps.setInt(4, specifierId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                map.put("isRelated", "true");
            } else {
                map.put("isRelated", "false");
                return map;
            }
            int requesterId = rs.getInt("requester_id");
            String statusCode = rs.getString("status_code"); // A: accepted, B: blocked, R: requested
            switch (statusCode) {
                case "B" -> map.put("type", "封鎖對方");
                case "R" -> {
                    String mapVal = requesterId == specifierId ? "邀請對方" : "對方邀請";
                    map.put("type", mapVal);
                }
                case "A" -> map.put("type", "彼此好友");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Member> getMembersByFriendship(Integer memberId, String statusCode, MemberPagerAndSorter pagerAndSorter) {
        List<Member> friends = new ArrayList<>();
        String statusAR = """
                SELECT member_id, member_account, member_name, member_username FROM member\r
                	WHERE member_id IN \r
                (SELECT addressee_id FROM friendship WHERE status_code = ? AND requester_id = ?\r
                	UNION\r
                SELECT requester_id FROM friendship WHERE status_code = ? AND addressee_id = ?)
                """;
        String statusB = """
                SELECT member_id, member_account, member_name, member_username FROM member
                	WHERE member_id IN
                (SELECT requester_id FROM friendship WHERE status_code = ? AND addressee_id = ?)
                """;
        String sql = statusCode.equals("B") ? statusB : statusAR;
        sql = sql + pagerAndSorter.asQueryClause();
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (statusCode.equals("B")) {
                ps.setString(1, statusCode);
                ps.setInt(2, memberId);
            } else {
                ps.setString(1, statusCode);
                ps.setInt(2, memberId);
                ps.setString(3, statusCode);
                ps.setInt(4, memberId);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member friend = new Member();
                friend.setId(Integer.parseInt(rs.getString("member_id")));
                friend.setAccount(rs.getString("member_account"));
                friend.setName(rs.getString("member_name"));
                friend.setUsername(rs.getString("member_username"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }

    @Override
    public List<Member> getMembersByFriendship(Integer memberId, String statusCode, String searchText, MemberPagerAndSorter pagerAndSorter) {
        List<Member> friends = new ArrayList<>();
        String statusAR = """
                SELECT member_id, member_account, member_name, member_username FROM member\r
                	WHERE member_id IN \r
                (SELECT addressee_id FROM friendship WHERE status_code = ? AND requester_id = ?\r
                	UNION\r
                SELECT requester_id FROM friendship WHERE status_code = ? AND addressee_id = ?)
                    AND (member_name LIKE ? OR member_username LIKE ?)
                """;
        String statusB = """
                SELECT member_id, member_account, member_name, member_username FROM member
                	WHERE member_id IN
                (SELECT requester_id FROM friendship WHERE status_code = ? AND addressee_id = ?)
                    AND (member_name LIKE ? OR member_username LIKE ?)
                """;
        String sql = statusCode.equals("B") ? statusB : statusAR;
        sql = sql + pagerAndSorter.asQueryClause();
        String text = "%" + searchText + "%";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (statusCode.equals("B")) {
                ps.setString(1, statusCode);
                ps.setInt(2, memberId);
                ps.setString(3, text);
                ps.setString(4, text);
            } else {
                ps.setString(1, statusCode);
                ps.setInt(2, memberId);
                ps.setString(3, statusCode);
                ps.setInt(4, memberId);
                ps.setString(5, text);
                ps.setString(6, text);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member friend = new Member();
                friend.setId(Integer.parseInt(rs.getString("member_id")));
                friend.setAccount(rs.getString("member_account"));
                friend.setName(rs.getString("member_name"));
                friend.setUsername(rs.getString("member_username"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }

    @Override
    public List<Member> getMembersByRequest(Integer memberId, String direction, MemberPagerAndSorter pagerAndSorter) {
        List<Member> friends = new ArrayList<>();
        String sql = direction.equals("sent")
                ? """
                SELECT member_id, member_account, member_name, member_username FROM member\r
                	WHERE member_id IN \r
                (SELECT addressee_id AS friend_id FROM friendship WHERE requester_id = ? AND status_code = 'R')
                """
                : """
                SELECT member_id, member_account, member_name, member_username FROM member\r
                      	WHERE member_id IN \r
                      (SELECT requester_id AS friend_id FROM friendship WHERE addressee_id = ? AND status_code = 'R')
                """;
        sql = sql + pagerAndSorter.asQueryClause();

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member friend = new Member();
                friend.setId(Integer.parseInt(rs.getString("member_id")));
                friend.setAccount(rs.getString("member_account"));
                friend.setName(rs.getString("member_name"));
                friend.setUsername(rs.getString("member_username"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    @Override
    public List<Member> getMembersByRequest(Integer memberId, String direction, String searchText, MemberPagerAndSorter pagerAndSorter) {
        List<Member> friends = new ArrayList<>();
        String sql = direction.equals("sent")
                ? """
                SELECT member_id, member_account, member_name, member_username FROM member\r
                	WHERE member_id IN \r
                (SELECT addressee_id AS friend_id FROM friendship WHERE requester_id = ? AND status_code = 'R')
                    AND (member_name LIKE ? OR member_username LIKE ?)
                """
                : """
                SELECT member_id, member_account, member_name, member_username FROM member\r
                      	WHERE member_id IN \r
                (SELECT requester_id AS friend_id FROM friendship WHERE addressee_id = ? AND status_code = 'R')
                    AND (member_name LIKE ? OR member_username LIKE ?)
                """;
        String text = "%" + searchText + "%";
        sql = sql + pagerAndSorter.asQueryClause();

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ps.setString(2, text);
            ps.setString(3, text);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member friend = new Member();
                friend.setId(Integer.parseInt(rs.getString("member_id")));
                friend.setAccount(rs.getString("member_account"));
                friend.setName(rs.getString("member_name"));
                friend.setUsername(rs.getString("member_username"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    @Override
    public List<Member> getMembersByNonFriendship(Integer memberId, MemberPagerAndSorter pagerAndSorter) {
        List<Member> nonFriends = new ArrayList<>();
        String sql = """
                SELECT member_id, member_account, member_name, member_username FROM member\r
                	WHERE member_id NOT IN \r
                (SELECT addressee_id AS friend_id FROM friendship WHERE requester_id = ?\r
                	UNION\r
                SELECT requester_id AS friend_id FROM friendship WHERE addressee_id = ?) AND member_id != ?""" + pagerAndSorter.asQueryClause();

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ps.setInt(2, memberId);
            ps.setInt(3, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member nonFriend = new Member();
                nonFriend.setId(Integer.parseInt(rs.getString("member_id")));
                nonFriend.setAccount(rs.getString("member_account"));
                nonFriend.setName(rs.getString("member_name"));
                nonFriend.setUsername(rs.getString("member_username"));
                nonFriends.add(nonFriend);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nonFriends;
    }

    @Override
    public List<Member> getMembersByNonFriendship(Integer memberId, String searchText, MemberPagerAndSorter pagerAndSorter) {
        List<Member> nonFriends = new ArrayList<>();
        String sql = """
                SELECT member_id, member_account, member_name, member_username FROM member\r
                	WHERE member_id NOT IN \r
                (SELECT addressee_id AS friend_id FROM friendship WHERE requester_id = ?\r
                	UNION\r
                SELECT requester_id AS friend_id FROM friendship WHERE addressee_id = ?)
                    AND member_id != ? AND (member_name LIKE ? OR member_username LIKE ?)
                """;
        String text = "%" + searchText + "%";
        sql = sql + pagerAndSorter.asQueryClause();

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ps.setInt(2, memberId);
            ps.setInt(3, memberId);
            ps.setString(4, text);
            ps.setString(5, text);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member nonFriend = new Member();
                nonFriend.setId(Integer.parseInt(rs.getString("member_id")));
                nonFriend.setAccount(rs.getString("member_account"));
                nonFriend.setName(rs.getString("member_name"));
                nonFriend.setUsername(rs.getString("member_username"));
                nonFriends.add(nonFriend);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nonFriends;
    }

}
