package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BankDAOImpl implements BankDAO {
	
	BankInfoVO bankVO = new BankInfoVO();
	
	//JDBC
	private Connection getConnection() throws SQLException {
		
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(dburl, "webdb", "1234");
			
		} catch (ClassNotFoundException e) {
			
			System.err.println("JDBC 드라이버 로드 실패!");
		}
		return conn;
	}

	//전체 출력하는 메소드
	@Override
	public List<BankInfoVO> getList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<BankInfoVO> list = new ArrayList<BankInfoVO>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String query = "SELECT seq_id, deposit, withdraw, tr_date, balance FROM bank";
			
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				BankInfoVO bankVO = new BankInfoVO(
						rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5)
						);
				list.add(bankVO);
			}
		}catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}
		
		return list;
	}

	//입금하는 메소드
	@Override
	public boolean insert(int inMoney) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int insertedCount = 0;

		
		try {
			conn = getConnection();
			String query1 = " INSERT INTO bank(seq_id, deposit, tr_date, balance) VALUES(SEQ_BANK_ID.NEXTVAL, ?, sysdate, ?) ";
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, inMoney);
			int temp = select();
			pstmt.setInt(2, (temp + inMoney));
			
			insertedCount = pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			}catch(Exception e){
				System.out.println(e);
			}
		}
		return insertedCount == 1;
	}

	//출금하는 메소드
	@Override
	public boolean delete(int outMoney) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int insertedCount = 0;
		
		try {
			conn = getConnection();
			String query = " INSERT INTO bank(seq_id, withdraw, tr_date, balance) VALUES(SEQ_BANK_ID.NEXTVAL, ?, sysdate, ?) ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, outMoney);
			
			//잔액 확인하는 메소드로 잔액을 가져옴
			int temp = select();
			
			//잔액이 출금액보다 큰지 작은지 확인
			if(temp == 0 || temp<outMoney) {
				System.out.println("잔액을 확인해주세요");
				return false;
			}else {
				pstmt.setInt(2, (temp - outMoney));
			}
			
			insertedCount = pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			}catch(Exception e){
				System.out.println(e);
			}
		}
		return insertedCount == 1;
	}
	
	//잔액(balance) 구하는 메소드
	public int select() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			//MAX로 최신날짜의 잔액을 가져옴
			String sql = "SELECT balance FROM bank WHERE tr_date = (SELECT MAX(tr_date) FROM bank)";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				bankVO.setBalance(rs.getInt(1));
			}
			result = bankVO.getBalance();
		
		}catch(Exception e) {
			System.out.println(e);
		}
		return result;		
	}

	//특정 날짜 거래 내역
	@Override
	public List<BankInfoVO> getList(String date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BankInfoVO> list = new ArrayList<BankInfoVO>();
		
		try {
			conn = getConnection();
			String query = "SELECT * FROM bank WHERE tr_date LIKE TO_DATE(?, 'YYYYMMDD')";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, date);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				bankVO = new BankInfoVO(
						rs.getInt(1), 
						rs.getInt(2), 
						rs.getInt(3), 
						rs.getString(4), 
						rs.getInt(5)
						);
				list.add(bankVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e) {
				System.out.println(e);
			}
		}
		
		return list;
	}

	//특정 기간 거래 내역
	@Override
	public List<BankInfoVO> getList(String date1, String date2) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BankInfoVO> list = new ArrayList<BankInfoVO>();
		
		
		try {
			conn = getConnection();
			String query = "SELECT * FROM BANK WHERE tr_date BETWEEN TO_DATE(?, 'YYYYMMDD') AND TO_DATE(?, 'YYYYMMDD') + 0.99999";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				bankVO = new BankInfoVO(
						rs.getInt(1), 
						rs.getInt(2), 
						rs.getInt(3), 
						rs.getString(4), 
						rs.getInt(5));
				list.add(bankVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return list;
	}

	
}
