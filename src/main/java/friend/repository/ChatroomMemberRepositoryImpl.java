package friend.repository;

import common.HikariDataSource;
import friend.model.Chatroom;
import member.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

public class ChatroomMemberRepositoryImpl implements ChatroomMemberRepository {

    @Override
    public Integer addChatroom(List<Integer> membersId) {
        boolean hasAdded = false;
        String sqlInsertChatroom = "INSERT INTO `chatroom` (`chatroom_name`) VALUES (' ');";
        String sqlInsertChatroomMember = "INSERT INTO `chatroom_member` (`chatroom_id`, `member_id`) VALUES (?, ?);";
        String[] generatedColumnsA = {"chatroom_id"};
        int chatroomId;

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps1 = connection.prepareStatement(sqlInsertChatroom, generatedColumnsA);
             PreparedStatement ps2 = connection.prepareStatement(sqlInsertChatroomMember)) {
            hasAdded = ps1.executeUpdate() != 0;
            ResultSet rsA = ps1.getGeneratedKeys();

            if (rsA.next()) {
                chatroomId = rsA.getInt(1);
                ps2.setInt(1, chatroomId);
            } else {
                throw new RuntimeException("Failed to create a chatroom");
            }

            for (Integer m : membersId) {
                ps2.setInt(2, m);
                ps2.addBatch();
            }

            int[] insertResults = ps2.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
//            throw new RuntimeException("無法將該名會員加入聊天室");
            throw new RuntimeException(e);
        }
        return chatroomId;
    }

    @Override
    public boolean addMembersToChatroom(List<Integer> membersId, Integer chatroomId) {
        boolean hasAdded = false;
        String sql = "INSERT INTO `chatroom_member` (`chatroom_id`, `member_id`) VALUES (?, ?);";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);

            for (Integer m : membersId) {
                ps.setInt(2, m);
                ps.addBatch();
            }

            int[] result = ps.executeBatch();
            OptionalInt sum = Arrays.stream(result).reduce(Integer::sum);
            hasAdded = sum.getAsInt() == result.length;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return hasAdded;
    }

    @Override
    public boolean deleteMemberFromChatroom(Integer memberId, Integer chatroomId) {
        boolean hasDeleted = false;
        String sql = "DELETE FROM `chatroom_member` WHERE chatroom_id = ? and member_id = ?;";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);
            ps.setInt(2, memberId);
            hasDeleted = ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException("無法從聊天室移除該名成員");
        }
        return hasDeleted;
    }

    @Override
    public boolean updateChatroomName(String name, Integer chatroomId) {
        boolean hasUpdated = false;
        String sql = "UPDATE `lazy`.`chatroom` SET `chatroom_name` = ? WHERE (`chatroom_id` = ?);";
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, chatroomId);
            hasUpdated = ps.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasUpdated;
    }

    @Override
    public Chatroom getChatroom(Integer chatroomId) {
        Chatroom chatroom = null;
        String sql = "SELECT chatroom_id, chatroom_name, UNIX_TIMESTAMP(created_at) created_at_unix FROM lazy.chatroom WHERE chatroom_id = ?;";

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                chatroom = new Chatroom();
                chatroom.setId(rs.getInt("chatroom_id"));
                chatroom.setName(rs.getString("chatroom_name"));
                chatroom.setCreatedAt(rs.getLong("created_at_unix"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
//            throw new RuntimeException("目前資料庫有異狀，無法取得所有聊天室");
            throw new RuntimeException(e);
        }

        return chatroom;
    }

    @Override
    public List<Chatroom> getChatrooms(Integer memberId) {
        List<Chatroom> chatrooms = new ArrayList<>();
        String sql = """
                SELECT ch.chatroom_id, ch.chatroom_name, UNIX_TIMESTAMP(ch.created_at) created_at_unix FROM chatroom_member cm
                \tJOIN chatroom ch ON ch.chatroom_id = cm.chatroom_id
                WHERE member_id = ?;
                """;

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, memberId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chatroom chatroom = new Chatroom();
                chatroom.setId(rs.getInt("chatroom_id"));
                chatroom.setName(rs.getString("chatroom_name"));
                chatroom.setCreatedAt(rs.getLong("created_at_unix"));
                chatrooms.add(chatroom);
            }

        } catch (SQLException e) {
            e.printStackTrace();
//            throw new RuntimeException("目前資料庫有異狀，無法取得所有聊天室");
            throw new RuntimeException(e);
        }

        return chatrooms;
    }

    @Override
    public List<Member> getChatroomMembers(Integer chatroomId) {
        List<Member> members = new ArrayList<>();
        String sql = """
                SELECT m.member_id, m.member_name, m.member_account, m.member_username FROM chatroom_member cm
                \tJOIN member m ON cm.member_id = m.member_id
                WHERE cm.chatroom_id = ?;
                """;
        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, chatroomId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setId(Integer.parseInt(rs.getString("member_id")));
                member.setAccount(rs.getString("member_account"));
                member.setName(rs.getString("member_name"));
                member.setUsername(rs.getString("member_username"));
                members.add(member);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return members;
    }

    @Override
    public List<Member> getMemberByNameOrUsername(String searchText) {
        List<Member> members = new ArrayList<>();
        String sql = """
                SELECT member_id, member_account, member_name, member_username, member_address
                \tFROM lazy.member
                WHERE member_name LIKE ? OR member_username LIKE ?;""";
        String text = "%" + searchText + "%";

        try (Connection connection = HikariDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, text);
            ps.setString(2, text);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("member_id"));
                member.setAccount(rs.getString("member_account"));
                member.setName(rs.getString("member_name"));
                member.setUsername(rs.getString("member_username"));
                member.setAddress(rs.getString("member_address"));
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return members;
    }
}
