package jdbcEx;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookInsertTest {

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// jdbc드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// connection얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "1234");
			System.out.println("접속 성공");


			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "Insert INTO book VALUES(?, ?, ?, ?, ?)" ;

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 5);
            pstmt.setString(2, "소녀의 세계");
            pstmt.setString(3, "중앙북스");
            pstmt.setString(4, "20200115");
            pstmt.setInt(5, 5);
            
            int count = pstmt.executeUpdate();

            // 4.결과처리
            System.out.println(count + "건 처리");


		} catch (ClassNotFoundException e) {
			System.out.println("error : 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error : " + e);
			}
		}
	}

}
