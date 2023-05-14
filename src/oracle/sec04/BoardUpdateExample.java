package oracle.sec04;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardUpdateExample {

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
			String sql = new StringBuilder()
					.append("UPDATE boards SET ")
					.append("btitle=?, ")
					.append("bcontent=?, ")
					.append("bfilename=?, ")
					.append("bfiledata=? ")
					.append("WHERE bno=?")
					.toString();
			
			//PreparedStatement 얻기 및 값 지정
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, "눈사람");
			psmt.setString(2, "눈으로 만든 사람");
			psmt.setString(3, "snowman.jpg");
			psmt.setBlob(4, new FileInputStream("src/oracle/sec04/snowman.jpg"));
			psmt.setInt(5, 1);    //boards 테이블에 있는 게시물 번호(bno) 지정
			
			//SQL 문 실행
			int rows = psmt.executeUpdate();
			System.out.println("수정된 행 수: " + rows);
			
			//PreparedStatement 닥기
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
