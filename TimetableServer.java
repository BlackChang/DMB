package Network_DMB;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import com.mysql.jdbc.Connection;
public class TimetableServer {
    private static final int PORT = 9001;

    private static ArrayList<String> names = new ArrayList<String>();
    //클라이언트들의 이름을 저장해놓은 ArrayList
  
	public static void main(String[] args) throws Exception{
		System.out.println("Server start..\n");
		ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true)
                new Handler(listener.accept()).start();
        } finally {
            listener.close();
        }
	}    
    private static class Handler extends Thread {
    	private String id;
    	private String name;
    	private String pw;
    	private int grade;
    	private int semester;
        private Socket socket;
        private BufferedReader in;
        //클라이언트로부터 데이터 받기 위한 변수
        private PrintWriter out;
        //클라이언에게 데이터 내보내기 위한 변수        
        
        public Handler(Socket socket) {
            this.socket = socket;
        }
        public void run() {
        	Connection con = null;
    		try {
    			Class.forName("com.mysql.jdbc.Driver");
    			String url = "jdbc:mysql://localhost/timedb";
    			String user = "root"; 
    			String pw = "12345";
    			con = (Connection) DriverManager.getConnection(url, user, pw);
    			System.out.println(con);
    		} catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		} 
    		try {
    			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            	out = new PrintWriter(socket.getOutputStream(), true);
            	    			
            	while (true) {
                    out.println("SIGNIN");
                    //클라이언트에게 SUBMITID이란 메세지 보냄                        
                    //클라이언트로부터 받은 정보(학번, 이름, 학년, 학기)를 id 변수에 저장
                    signIn(id, pw, con);
                    if (id == null || name == null || grade == 0 || semester == 0)
                        return;
                        //학번이 null이면 return
          
                        //클라이언트로부터 받은 학번이 기존 ArrayList에 존재하지 않으면 ArrayList에 해당 학번 추가
                	out.println("SUBMITOPTION");
                	out.println("INFOACCEPTED");
                }                    
            	//클라이언트에게 이름 받았다는 의미의 NAMEACEPTED 보냄
    	    } catch (IOException e) {
    	    	System.out.println(e);
    	    } catch(SQLException e) {
    	    	e.printStackTrace();
    	    }
    		
    	    try {
    	    	socket.close(); //소켓 종료
    	    } catch (IOException e) {
    	    	e.printStackTrace();
    	    }
        }
        public void signUp(String ID, String PW, Connection con) throws SQLException {
        	PreparedStatement ps = null;
            ResultSet rs = null;
            try {
               String sql = null;
               sql = "select ID from id_pw where ID='" + ID + "'";
               ps = con.prepareStatement(sql);
               rs = ps.executeQuery();

               if(rs.next()) {
            	   //경고문 : 이미 가입되어 있습니다.
               }   
               else { //새로운 아이디와 비밀번호 DB에 저장
            	   String signUP = null;
            	   signUP = "insert into id_pw values('" + ID + "', '" + PW + "')";
            	   ps = con.prepareStatement(signUP);
            	   ps.executeQuery();
               }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }
        public void info(String ID, String name, int grade, int semester, Connection con) {
        	PreparedStatement ps = null;
        	ResultSet rs = null;
            int name_duplicate = 0;
            try {
               String sql = null;
               sql = "select count(name) from userinfo where NAME='" + name + "'";
               ps = con.prepareStatement(sql);
               rs = ps.executeQuery();
               
               while(rs.next())
            	   name_duplicate++; //중복되는 이름 세기
               
               if(name_duplicate > 0)
            	   name = name.concat(Integer.toString(name_duplicate));
               
               sql = "insert into userinfo values(?,?,?,?)";
               ps = con.prepareStatement(sql);
               ps.setString(1, ID);
               ps.setString(2, name);
               ps.setInt(3, grade);
               ps.setInt(4, semester);
               
               ps.executeQuery();            		   
            } catch(SQLException e) {
            	e.printStackTrace();
            }
        }
        public void signIn(String ID, String PW, Connection con) throws SQLException {
        	PreparedStatement ps = null;
            ResultSet rs = null;
            try {
               String sql = null;
               sql = "select NAME from userinfo natural join id_pw where ID='" + ID + "' and PW='" + PW +"'";
               ps = con.prepareStatement(sql);
               rs = ps.executeQuery();

               while (rs.next()) {
                  name = rs.getString(1);
                  names.add(name);
               }   
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }
    }
}