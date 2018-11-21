package Network_DMB;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import com.mysql.jdbc.Connection;
public class TimetableServer {
    private static final int PORT = 9001;

    private static ArrayList<String> names = new ArrayList<String>();
    //Ŭ���̾�Ʈ���� �̸��� �����س��� ArrayList
  
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
    	private String professor;
    	private String spaceTime;
        private Socket socket;
        private BufferedReader in;
        //Ŭ���̾�Ʈ�κ��� ������ �ޱ� ���� ����
        private PrintWriter out;
        //Ŭ���̾𿡰� ������ �������� ���� ����        
        
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
                    //�����κ��� ������ �о�� line ������ ����
            	    if (line.startsWith("SIGNUP")) {
                        id = in.readLine(); 
                        pw = in.readLine();
                        signUp(id, pw, con);
                        //�����κ��� �о�� �����Ͱ� SUBMITNAME�� �� getName�Լ� �̿뿡 ����� �̸� �Է� ����
                    }
                    else if(line.startsWith("SIGNIN")) {
                    	id = in.readLine();
                    	pw = in.readLine();
                        signIn(id, pw, con);                	
                        line = in.readLine();
                        name = in.readLine();
                    	grade = in.read();
                    	semester = in.read();
                        if (name == null || grade == 0 || semester == 0)
                            return;
                        info(id, name, grade, semester, con);
                    }
                    else if(line.startsWith("INFO")) {
                    	name = in.readLine();
                    	grade = in.read();
                    	semester = in.read();
                        if (name == null || grade == 0 || semester == 0)
                            return;
                            //�й��� null�̸� return
                  }
                    else if(line.startsWith("OPTION")) {
                    	professor = in.readLine();
                    	spaceTime = in.readLine();
                    }                
                }                    
    	    } catch (IOException e) {
    	    	System.out.println(e);
    	    } catch(SQLException e) {
    	    	e.printStackTrace();
    	    }
    		
    	    try {
    	    	socket.close(); //���� ����
    	    } catch (IOException e) {
    	    	e.printStackTrace();
    	    }
        }
        public void signUp(String ID, String PW, Connection con) throws SQLException {
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
            	   //��� : �̹� ���ԵǾ� �ֽ��ϴ�.
               }   
               else { //���ο� ���̵�� ��й�ȣ DB�� ����
            	   String signUP = null;
                   signUP = "insert into id_pw values(?,?)";
            	   ps = con.prepareStatement(signUP);
                   ps.setString(1, ID);
                   ps.setString(2, PW);
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
               sql = "select NAME from userinfo natural join id_pw where ID='" + ID + "' and PW='" + PW +"'";
               ps = con.prepareStatement(sql);
               rs = ps.executeQuery();

               if (rs.next()) {
                  name = rs.getString(1);
                  names.add(name);
               } 
               else {
            	   //WARNINGS : Non ID or PW	
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
               sql = "select name from userinfo where NAME='" + name + "'";
               ps = con.prepareStatement(sql);
               rs = ps.executeQuery();
               
               while(rs.next())
            	   name_duplicate++; //�ߺ��Ǵ� �̸� ����
               
               if(name_duplicate > 0)
            	   name = name.concat(Integer.toString(name_duplicate));
               
               sql = "insert into userinfo values(?,?,?,?)";
               ps = con.prepareStatement(sql);
               ps.setString(1, ID);
               ps.setString(2, name);
               ps.setInt(3, grade);
               ps.setInt(4, semester);
               
               ps.executeUpdate();            		   
            } catch(SQLException e) {
            	e.printStackTrace();
            }
        }
    }
}