package Network_DMB;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

import com.mysql.jdbc.Connection;
public class TimetableServer {
    private static final int PORT = 9001;
    public static String[] instructor = new String[10];
    static ArrayList<String> names = new ArrayList<String>();
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
    	private String grade;
    	private String semester;
    	private String prof;
    	private String restTime;
        private Socket socket;
        private BufferedReader in;
        //Ŭ���̾�Ʈ�κ��� ������ �ޱ� ���� ����
        private PrintWriter out;
        private PrintWriter list;
        //Ŭ���̾𿡰� ������ �������� ���� ����        
        
        public Handler(Socket socket) {
            this.socket = socket;
        }
        public void run() {
        	Connection con = null;
    		try {
    			Class.forName("com.mysql.jdbc.Driver");
    			String url = "jdbc:mysql://localhost:3306/timedb?useUnicode=true&characterEncoding=utf8";
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
            	out = new PrintWriter(socket.getOutputStream(),true);
            	list = new PrintWriter(socket.getOutputStream(),true);
            	while (true) {
                    String line = in.readLine();
                    //�����κ��� ������ �о�� line ������ ����
            	    if (line.startsWith("SIGNUP")) {
                        id = in.readLine(); 
                        pw = in.readLine();
                        name = in.readLine();
                        signUp(id, pw, name, con);
                        //�����κ��� �о�� �����Ͱ� SUBMITNAME�� �� getName�Լ� �̿뿡 ����� �̸� �Է� ����
                    }
                    else if(line.startsWith("SIGNIN")) {
                    	id = in.readLine();
                    	pw = in.readLine();
                        signIn(id, pw, con);         
                    }
                    else if(line.startsWith("INFO")) {
                    	grade = in.readLine();
                    	semester = in.readLine();
                    	getProf(con);
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
    	    	socket.close(); //���� ����
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
            	   //��� : �̹� ���ԵǾ� �ֽ��ϴ�.
               }   
               else { //���ο� ���̵�� ��й�ȣ DB�� ����
            	   String signUP = null;
                   signUP = "insert into id_pw values(?,?,?)";
                   ps = con.prepareStatement(signUP);
                   ps.setString(1, ID);
                   ps.setString(2, PW);
                   ps.setString(3, NAME);
             	   ps.executeUpdate();
             	   System.out.println(ps);
               	   System.out.println(ID);
             	   System.out.println(PW);
             	   System.out.println(NAME);
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
        public void getProf(Connection con) {
        	PreparedStatement ps = null;
        	ResultSet rs = null;
            try {
            	String sql = null;
            	sql = "select count(distinct instructor) from course where grade =" + Integer.parseInt(grade) + 
            			" and semester = " +  Integer.parseInt(semester) + " and major='전필'";
            	ps = con.prepareStatement(sql);
            	System.out.println(ps);
            	rs = ps.executeQuery();
            	int num = 0;
            	if(rs.next())
            		num = rs.getInt(1);
            	System.out.println(num);
            	sql = "select distinct instructor from course where grade =" + Integer.parseInt(grade) + 
            			" and semester = " +  Integer.parseInt(semester) + " and major='전필'";
            	ps = con.prepareStatement(sql);
            	System.out.println(ps);
            	rs = ps.executeQuery();
            	
            	instructor[0] = "상관없음";
            	out.println("PROFLIST");
            	String send = Integer.toString(num);
            	out.println(send);
            	
            	int i = 1;
            	
            	while(rs.next()) {
            		instructor[i] = rs.getString(1);
            		i++;
            	}
            	i = 0;
            	while(i < num + 1) {
            		out.println(instructor[i]);
                	i++;
            	}
            	i = 0;
            	//while(i < instructor.length) {
            		//list.println(instructor[i]);
            		//i++;
            	//}
            	//else
            		//out.println("NEWTABLE");
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