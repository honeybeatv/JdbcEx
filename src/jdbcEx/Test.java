package jdbcEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

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

			// sql문 준비, 바인딩, 실행
			String query = "select book_id, title from book ORDER BY book_id";
            pstmt = conn.prepareStatement(query);

			int count = pstmt.executeUpdate();
			rs = pstmt.executeQuery();
			System.out.println(count + " 건 처리");
			
			while (rs.next()) {
//                int authorId = rs.getInt("author_id");
//                String authorName = rs.getString("name");
//                String authorDesc = rs.getString("author_desc");
//                System.out.println(authorId + "\t" + authorName + "\t" + authorDesc + "\t");
				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				System.out.println(bookId + "\t" + title);
            }


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
