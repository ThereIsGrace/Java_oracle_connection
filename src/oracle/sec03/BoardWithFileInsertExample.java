package oracle.sec03;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardWithFileInsertExample {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			//JDBC Driver 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//연결하기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/xe",
					"jaeeun",
					"12345");
			
			
			//매개변수화된 SQL 문 작성
			String sql = ""+
			"INSERT INTO boards (bno, btitle, bcontent, bwriter, bdate, "
			+ "bfilename, bfiledata) VALUES (SEQ_BNO.NEXTVAL,?,?,?,SYSDATE,?,?)";
			
			PreparedStatement psmt = conn.prepareStatement(sql, new String[] {"bno"});
			psmt.setString(1, "크리스마스");
			psmt.setString(2, "메리 크리스마스~");
			psmt.setString(3, "winter");
			psmt.setString(4, "christmas.jpg");
			psmt.setBlob(5, new FileInputStream("src/oracle/sec03/christmas.jpg"));
			
			//SQL 문 실행
			int rows = psmt.executeUpdate();
			System.out.println("저장된 행 수: " + rows);
			
			//bno 값 얻기
			if(rows == 1) {
				ResultSet rs = psmt.getGeneratedKeys();
				if(rs.next()) {
					int bno = rs.getInt(1);
					System.out.println("저장된 bno: " + bno);
				}
				rs.close();
			}
			
			//PreparedStatement 닫기
			psmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {
					//연결 끊기
					conn.close();
				}catch(SQLException e) {
					
				}
			}
		}
	}
}