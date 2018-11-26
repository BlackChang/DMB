package Network_DMB;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.*;
import java.net.*;
import com.mysql.jdbc.Connection;
public class TimetableServer {
    private static final int PORT = 9001;
    public static String[] instructor = new String[10];
    static ArrayList<String> names = new ArrayList<String>();
    //chat
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    public static ArrayList<String> list = new ArrayList<String>();
    public static String s = "";
    //chat
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
        //Ŭ���̾𿡰� ������ �������� ���� ����        
        
        public Handler(Socket socket) {
            this.socket = socket;
        }
        public void run() {
        	Connection con = null;
    		try {
    			Class.forName("com.mysql.jdbc.Driver");
    			String url = "jdbc:mysql://localhost/timedb?useUnicode=true&characterEncoding=UTF-8";
    			String user = "root";
    			String pw = "";
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
            	while (true) 
                {
            			String line = in.readLine();
            			
            	    		if (line.startsWith("SIGNUP")) 
            	    		{
                        id = in.readLine(); 
                        pw = in.readLine();
                        name = in.readLine();
                        signUp(id, pw, name, con);
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
                    name = in.readLine();
                    if (name == null)
                    {
                        return;
                    }
                    synchronized (names)
                    {
                        if (!names.contains(name))  
                        {
                            	names.add(name);
                            	list.add(name);
                            	s = "";

                    			out.println("NAMEACCEPTED");
                    			writers.add(out);
                    			
                    			for(int x=0; x<list.size(); x++)
                            		s = s + list.get(x) + ",";
                            	
                            	for (PrintWriter writer : writers) 
                    				writer.println("LIST " + "+ " + s);  
                            	
                    			for (PrintWriter writer : writers) 
                    				writer.println("MESSAGE " + name + " JOIN ROOM !");  
                    			break;
                        }
                    }
                }
                out.println("NAMEACCEPTED");
                writers.add(out);
                while (true) //output
                {
                    String input = in.readLine();
                    if (input == null)
                    {
                        return;
                    }
                    else
                    {
                    		for (PrintWriter writer : writers) // 모든 클라이언트에게 전송한다  
                        {
                    			if(input.startsWith("WHISPER")) // 채팅의 시작부분이 WHISPER일시 WHISPER함수를 이용하게만든다 
                    				writer.println(input + " " + name);
                    			else//아닐시 일반적인채팅기능  
                    				writer.println("MESSAGE " + name + ": " + input);
                        }
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
            	sql = "select distinct instructor from course where grade ='" + Integer.parseInt(grade) + 
            			"' and semester = '" +  Integer.parseInt(semester) + "' and major='전필'";
            	ps = con.prepareStatement(sql);
            	rs = ps.executeQuery();
            	
            	instructor[0] = "상관없음";
            	while(rs.next()) {
            		int i = 1;
            		instructor[i] = rs.getString(1);
            		System.out.println(instructor[i]);
            		i++;
            	}
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
            finally
            {
            		for (PrintWriter writer : writers) // 사용자가 나가서 스레드가 끝났음을 모든 클라이언트에게 제공 
                {
            			list.remove(name);
            			writer.println("LIST " + "- " + name);
            			writer.println("MESSAGE " + name + " EXIT ROOM...");
                }
                if (name != null)
                {
                    names.remove(name);
                }
                if (out != null) 
                {
                    writers.remove(out);
                }
                try
                {
                    socket.close();
                } 
                catch (IOException e)
                {
                }
            }
        }
    }
}
