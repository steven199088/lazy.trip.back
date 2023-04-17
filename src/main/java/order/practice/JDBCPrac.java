package order.practice;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCPrac {
    public static void main(String[] args) {

        BorisDataBaseJDBC sc = new BorisDataBaseJDBC();
        String sql1 = "INSERT INTO getkeyprac (number) VALUES (?)";
        String[] columns = {"number"};
        int[] num = new int[20];

        try (Connection conn = DriverManager.getConnection(sc.URL, sc.USER, sc.PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql1,columns);){

            stmt.setInt(1, 4);
            int rowCount = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            System.out.println(rs.next());
            System.out.println(rs.getInt(1));
            System.out.println("rowCount:"+rowCount);

        } catch(Exception e){

            System.out.println("錯誤 " + e.getMessage());
        }
    }
}