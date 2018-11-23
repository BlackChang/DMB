package Network_DMB;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import com.mysql.jdbc.Connection;
public class TimetableServer {
    private static final int PORT = 9001;

    static ArrayList<String> names = new ArrayList<String>();
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
    	private String grade;
    	private String semester;
    	private String prof;
    	private String restTime;
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
                    String line = in.readLine();
                    //서버로부터 데이터 읽어와 line 변수에 저장
            	    if (line.startsWith("SIGNUP")) {
                        id = in.readLine(); 
                        pw = in.readLine();
                        name = in.readLine();
                        signUp(id, pw, name, con);
                        //서버로부터 읽어온 데이터가 SUBMITNAME일 때 getName함수 이용에 사용자 이름 입력 받음
                    }
                    else if(line.startsWith("SIGNIN")) {
                    	id = in.readLine();
                    	pw = in.readLine();
                        signIn(id, pw, con);         
                    }
                    else if(line.startsWith("INFO")) {
                    	grade = in.readLine();
                    	semester = in.readLine();
                        info(this.id, grade, semester, con);
                    }
                    else if(line.startsWith("OPTION")) {
                    	prof = in.readLine();
                    	restTime = in.readLine();
                    }                
            	    
                }                    
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
        public void signUp(String ID, String PW, String NAME, Connection con) throws SQLException {
        	PreparedStatement ps = null;
            ResultSet rs = null;
            try {
               String sql = null;
               sql = "select ID from id_pw where ID=(?)";
               ps = con.prepareStatement(sql);
               ps.setString(1, ID);
               rs = ps.executeQuery();
               if(rs.next()) {
            	   out.println("Duplicate ID");
            	   //경고문 : 이미 가입되어 있습니다.
               }   
               else { //새로운 아이디와 비밀번호 DB에 저장
            	   String signUP = null;
                   signUP = "insert into id_pw values(?,?,?)";
            	   ps = con.prepareStatement(signUP);
                   ps.setString(1, ID);
                   ps.setString(2, PW);
                   ps.setString(3, NAME);
             	   ps.executeUpdate();
             	   out.println("COMPLETE");
               }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }
        public void signIn(String ID, String PW, Connection con) throws SQLException {
        	PreparedStatement ps = null;
            ResultSet rs = null;
            try {
               String sql = null;
               sql = "select NAME from id_pw where ID='" + ID + "'";
               ps = con.prepareStatement(sql);
               rs = ps.executeQuery();

               if (rs.next()) {
                  sql = "select NAME from id_pw where ID='" + ID + "' and PW='" + PW + "'";
                  ps = con.prepareStatement(sql);
                  rs = ps.executeQuery();
                  if(rs.next()) {
                	  name = rs.getString(1);
                      names.add(name);
                      out.println("SIGNIN");
                  }
                  else
                	  out.println("WRONGPW");
               } 
               else {
            	   out.println("NOTEXIST");
               }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }
        public void info(String ID, String Grade, String Semester, Connection con) {
        	PreparedStatement ps = null;
        	ResultSet rs = null;
            try {
            	Integer.parseInt(Grade);
            	Integer.parseInt(Semester);
            	String sql = null;
            	sql = "select id from created_table where grade ='" + Integer.parseInt(Grade) + "' and semester = '" +  Integer.parseInt(Semester) + "'";
            	ps = con.prepareStatement(sql);
            	rs = ps.executeQuery();
               
            	if(rs.next())
            		out.println("EXISTTABLE"); 
            	else
            		out.println("NEWTABLE");
            	/*else {
            		sql = "insert into created_table values(?,?,?)";
            		ps = con.prepareStatement(sql);
            		ps.setString(1, ID); 
            		ps.setInt(2, Integer.parseInt(Grade));
            		ps.setInt(3, Integer.parseInt(Semester));
            		ps.executeUpdate();            		   
            		out.println("NEWTABLE");
               } */              
            } catch(SQLException e) {
            	e.printStackTrace();
            }
        }
    }
}