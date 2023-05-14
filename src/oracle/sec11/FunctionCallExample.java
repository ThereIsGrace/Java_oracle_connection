package oracle.sec11;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class FunctionCallExample {
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
			String sql = "{? = call user_login(?,?)}";
			CallableStatement csmt = conn.prepareCall(sql);
			
			//? 값 지정 및 리턴 타입 지정
			csmt.registerOutParameter(1, Types.INTEGER);
			csmt.setString(2, "winter");
			csmt.setString(3, "12345");
			
			//함수 실행 및 리턴값 얻기
			csmt.execute();
			int result = csmt.getInt(1);
			
			//CallableStatement 닫기
			csmt.close();
			
			//로그인 결과(Switch Expression 이용)
			String message = switch(result) {
			case 0 -> "로그인 성공";
			case 1 -> "비밀벙호가 틀림";
			default -> "아이디가 존재하지 않음";
			};
			System.out.println(message);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				try {
					//연결끊기
					conn.close();
				}catch(SQLException e) {
					
				}
			}
		}

	}

}
