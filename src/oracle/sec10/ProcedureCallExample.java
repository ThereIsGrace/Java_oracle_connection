package oracle.sec10;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class ProcedureCallExample {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			//JDBC Driver 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//연결하기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521/xe",
					"jaeeun",
					"12345"
			);
			
			//매개변수화된 호출문 작성과 CallableStatement 얻기
			String sql = "{call user_create(?,?,?,?,?,?)}";
			CallableStatement csmt = conn.prepareCall(sql);
			
			//값 지정 및 리턴 타입 지정
			csmt.setString(1, "summer");
			csmt.setString(2, "한여름");
			csmt.setString(3, "12345");
			csmt.setInt(4, 26);
			csmt.setString(5, "summer@mycompany.com");
			csmt.registerOutParameter(6, Types.INTEGER);
			
			//프로시저 실행 및 리턴값 얻기
			csmt.execute();
			int rows = csmt.getInt(6);
			System.out.println("저장된 행 수: " + rows);
			
			//CallableStatement 닫기
			csmt.close();
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
