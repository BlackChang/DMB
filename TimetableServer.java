package Network_DMB;

import java.sql.*;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import com.mysql.jdbc.Connection;

public class TimetableServer {
    private static final int PORT = 9001;

    private static ArrayList<String> ids = new ArrayList<String>();
    //Ŭ���̾�Ʈ���� �й��� �����س��� ArrayList
  
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
	
	private static class userInfo{
		private String id;
		private String name;
		private int grade;
		private int semester;
		
		public userInfo() {
			id = "";
			name = "";
			grade = 0;
			semester = 0;
		}
		
		public void setInput(String newID, String newName, int newGrade, int newSemester) {
			this.id = newID;
			this.name = newName;
			this.grade = newGrade;
			this.semester = newSemester;
		}
		public String getID() {
			return this.id;
		}
		public String getName() {
			return this.name;
		}
		public int getGrade() {
			return this.grade;
		}
		public int getSemester() {
			return this.semester;
		}
	}
    
    private static class Handler extends Thread {
    	private String id;
    	private String name;
    	private int grade;
    	private int semester;
        private String option;
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
    		} finally{
    		}
    	    
    		PreparedStatement insertInfo = null;		
    		PreparedStatement syncCheck = null;		
    	    try {
            	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            	out = new PrintWriter(socket.getOutputStream(), true);
            	
            	int index = 0;
            	userInfo[] info = new userInfo[1000];
            	
            	String psql = "insert into userinfo value (?, ?, ?, ?)";
    			String sync = "select id from userinfo where id = (?)";
            	insertInfo = con.prepareStatement(psql);	
    			syncCheck = con.prepareStatement(sync);
    			
            	while (true) {
                	info[index] = new userInfo();
                    out.println("SUBMITINFO");
                    //Ŭ���̾�Ʈ���� SUBMITID�̶� �޼��� ����
                    
                    id = in.readLine();
                    name = "";
                    grade = 0;
                    semester = 0;
                    syncCheck.setString(1, id);
                    //Ŭ���̾�Ʈ�κ��� ���� ����(�й�, �̸�, �г�, �б�)�� id ������ ����
                    if (id == null || name == null || grade == 0 || semester == 0)
                        return;
                        //�й��� null�̸� return
          
                    if (!syncCheck.execute()) {
                        info[index].setInput(id, name, grade, semester);
                        index++;
                        insertInfo.setString(1, id);
                		insertInfo.setString(2, name);
                		insertInfo.setInt(3, grade);
               			insertInfo.setInt(4, semester);
               			break;
                    }
                        //Ŭ���̾�Ʈ�κ��� ���� �й��� ���� ArrayList�� �������� ������ ArrayList�� �ش� �й� �߰�
                    }                    
                    out.println("SUBMITOPTION");
                    option = in.readLine();                                                                                                                                                                       
                    out.println("INFOACCEPTED");
                    //Ŭ���̾�Ʈ���� �̸� �޾Ҵٴ� �ǹ��� NAMEACEPTED ����
    	    } catch (IOException e) {
    	    	System.out.println(e);
    	    } catch(SQLException e) {
    	    	e.printStackTrace();
    	    } finally {
    	    	if (id != null)
    	    		ids.remove(id);
    	    	//names ArrayList���� ���� ��� �̸� ����
    	    }
    	    try {
    	    	socket.close(); //���� ����
    	    } catch (IOException e) {
    	    	e.printStackTrace();
    	    }
        }
    }
}

