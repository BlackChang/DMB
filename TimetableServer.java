package Network_DMB;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.*;
import java.net.*;
import com.mysql.jdbc.Connection;
public class TimetableServer {
    //chat
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    private static ArrayList<String> list = new ArrayList<String>();
    public static String s = "";
    //chat
    private static final int PORT = 9001;
    public static String[] instructor = new String[10];
    static ArrayList<String> names = new ArrayList<String>();
  
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
        private PrintWriter out;  
        
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
    		try 
    		{
    			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            	out = new PrintWriter(socket.getOutputStream(),true);
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
                    else if(line.startsWith("SIGNIN")) 
                    {
                    		id = in.readLine();
                    		pw = in.readLine();
                    		writers.add(out);
                    		signIn(id, pw, con);     
                    		if(!name.isEmpty())
                    		{
                    			names.add(name);
                        		list.add(name);
                        		s = "";

                        		for(int x=0; x<list.size(); x++)
                        			s = s + list.get(x) + ",";
                        		for (PrintWriter writer : writers) 
                        			writer.println("LIST " + "+ " + s);  
                        		for (PrintWriter writer : writers) 
                        			writer.println("MESSAGE " + name + " JOIN ROOM !"); 
                    		}
                    }
                    else if(line.startsWith("INFO")) 
                    {
                    	grade = in.readLine();
                    	semester = in.readLine();
                    	getProf(con);
                        info(this.id, grade, semester, con);
                    }
                    else if(line.startsWith("OPTION")) {
                    	prof = in.readLine();
                    	restTime = in.readLine();
            			algorithm(con);
                    }  
                    else
                    {
                    		for (PrintWriter writer : writers) //Send to all users
                    		{
                    			if(line.startsWith("WHISPER"))//If the sentence begins with "WHISPER"
                    				writer.println(line + " " + name);
                    			else//If the sentence begins with "MESSAGE"
                    				writer.println("MESSAGE " + name + ": " + line);
                    		}
                    }
                    
                }       

    	    } 
    		catch (IOException e) {
    	    	System.out.println(e);
    	    } 
    		catch(SQLException e) {
    	    	e.printStackTrace();
    	    }
    		
    		finally
            {
                if (name != null)
                {
                    names.remove(name);
                    for (PrintWriter writer : writers)//It informs the person who exited the program and deletes it from the list.
                    {
                			list.remove(name);
                			writer.println("LIST " + "- " + name);
                			writer.println("MESSAGE " + name + " EXIT ROOM...");
                    }
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
        	    			e.printStackTrace();
        	    		}
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
    
               }   
               else { 
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
            	sql = "select id from created_table where id='" + id + "' and " + "grade ='" + Integer.parseInt(Grade) + "' and semester = '" +  Integer.parseInt(Semester) + "'";
            	ps = con.prepareStatement(sql);
            	rs = ps.executeQuery();
               
            	if(rs.next())
            		out.println("EXISTTABLE"); 
            	else
            		out.println("NEWTABLE");
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
            			" and semester = " +  Integer.parseInt(semester) + " and major='�쟾�븘'";
            	ps = con.prepareStatement(sql);
            	rs = ps.executeQuery();
            	int num = 0;
            	if(rs.next())
            		num = rs.getInt(1);
            	System.out.println(num);
            	sql = "select distinct instructor from course where grade =" + Integer.parseInt(grade) + 
            			" and semester = " +  Integer.parseInt(semester) + " and major='�쟾�븘'";
            	ps = con.prepareStatement(sql);
            	rs = ps.executeQuery();
            	
            	instructor[0] = "�긽愿��뾾�쓬";
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
            } catch(SQLException e) {
            	e.printStackTrace();
            }
        }
        public static class Lecture{ //Class object that stores the information of the lecture
    		private String course_id;
    		private String title;
    		private String major;
    		private int grade;
    		private int semester;
    		private int credit;
    		private String instructor;
    		private int subID;
    		private String day;
    		private String time;
    		private int choose;
    		
    		public Lecture() {
    			this.course_id = "";
    			this.title = "";
    			this.major = "";
    			this.grade = 0;
    			this.semester = 0;
    			this.credit = 0;
    			this.instructor = "";
    			this.subID = 0;
    			this.day = "";
    			this.time = "";
    			this.choose = 0;
    		}
    	}
        public void algorithm(Connection con) {
        	PreparedStatement ps = null;
        	ResultSet rs = null;
            try {
            	Lecture[] course = new Lecture[30];
        		Lecture[] select = new Lecture[30];
        		for(int k = 0; k < 30; k++)
        			course[k] = new Lecture();
         		
        		for(int k = 0; k < 30; k++)
         			select[k] = new Lecture();
         		int count = 0;
        		int i = 0;
        		float totalCredit = 0; //Variable to prevent excess credit
        		int checkPoint = 0;
        		int location = 0;
        		int subject = 0;
        		int[] monday = new int[] {1,0,0,0,0,0,0,0,0,0,0}; //Array that stores the class times for each day of the week
        		int[] tuesday = new int[] {1,0,0,0,0,0,0,0,0,0,0};
        		int[] wednesday = new int[] {1,0,0,0,0,0,0,0,0,0,0};
        		int[] thursday = new int[] {1,0,0,0,0,0,0,0,0,0,0};
        		int[] friday = new int[] {1,0,0,0,0,0,0,0,0,0,0};
        		String sql = null;
            	sql = "select * from course natural join timetable where grade = " + grade + " and semester = " + semester + " and day not in (select day from timetable where day = '" + restTime + 
            			"') and course_id not in (select course_id from timetable where day = '" + restTime + "')"; //Create SQL query for user's option
    			ps = con.prepareStatement(sql);
            	rs = ps.executeQuery(); //Store relation corresponding to SQL query
    			
    			while(rs.next()) { //Store each tuple in Lecture object
    				course[i].course_id = rs.getString(1);
    				if(rs.wasNull())
    					course[i].course_id = "";
    				
    				course[i].title = rs.getString(2);
    				if(rs.wasNull())
    					course[i].title = "";
    				
    				course[i].major = rs.getString(3);
    				if(rs.wasNull())
    					course[i].major = "";
    				
    				course[i].grade = rs.getInt(4);
    				if(rs.wasNull())
    					course[i].grade = 0;
    				
    				course[i].semester = rs.getInt(5);
    				if(rs.wasNull())
    					course[i].semester = 0;
    				
    				course[i].credit = rs.getInt(6);
    				if(rs.wasNull())
    					course[i].credit = 0;
    				
    				course[i].instructor = rs.getString(7);
    				if(rs.wasNull())
    					course[i].instructor = "";

    				course[i].subID = rs.getInt(8);
    				if(rs.wasNull())
    					course[i].subID = 0;

    				course[i].day = rs.getString(9);
    				if(rs.wasNull())
    					course[i].day = "";
    				
    				course[i].time = rs.getString(10);
    				if(rs.wasNull())
    					course[i].time = "";
    				
    				course[i].choose = 0;
    				i++;
    			}
    			for(int k = 0; k < i; k++) {
    				if(course[k].instructor.equalsIgnoreCase(prof)) { //Part where the user selects the preferred professor's course
    					String hour;
    					hour = course[k].time;
    					int time, length, firstTime, lastTime;
    					
    					length = hour.length();
    					firstTime = (hour.charAt(0))-'0'; //Store time in integer type
    					lastTime = hour.charAt(length-1)-'0';
    					
    					if(course[k].day.equalsIgnoreCase("�썡")) { //Store the time of the course in the appropriate day
    						for(int j = firstTime; j <= lastTime; j++)
    							monday[j] = 1;
    					}
    					else if(course[k].day.equalsIgnoreCase("�솕")) {
    						for(int j = firstTime; j <= lastTime; j++)
    							tuesday[j] = 1;
    					} 
    					else if(course[k].day.equalsIgnoreCase("�닔")) {
    						for(int j = firstTime; j <= lastTime; j++)
    							wednesday[j] = 1;
    					}
    					else if(course[k].day.equalsIgnoreCase("紐�")) {
    						for(int j = firstTime; j <= lastTime; j++)
    							thursday[j] = 1;
    					}
    					else if(course[k].day.equalsIgnoreCase("湲�")) {
    						for(int j = firstTime; j <= lastTime; j++)
    							friday[j] = 1;
    					}
    					totalCredit = totalCredit + course[k].credit; //Update totalCredit
    					select[count] = course[k]; //Store this course in Select object
    					count++;
    					course[k].choose = 1; //Selected course initializes the choose variable to 1
    					for(int s = 0; s < i; s++) {
    						if(course[k].course_id.equalsIgnoreCase(course[s].course_id) && course[s].choose != 1) { //Select the same course on different days of the week
    							hour = course[s].time;
    							length = hour.length();
    	    					firstTime = (hour.charAt(0))-'0'; //Store time in integer type
    	    					lastTime = hour.charAt(length-1)-'0';
    	    		
    	    					if(course[s].day.equalsIgnoreCase("�썡")) { //Store the time of the course in the appropriate day
    								for(int j = firstTime; j <= lastTime; j++)
    									monday[j] = 1;
    							}
    							else if(course[s].day.equalsIgnoreCase("�솕")) {
    								for(int j = firstTime; j <= lastTime; j++)
    									tuesday[j] = 1;
    							} 
    							else if(course[s].day.equalsIgnoreCase("�닔")) {
    								for(int j = firstTime; j <= lastTime; j++)
    									wednesday[j] = 1;
    							}
    							else if(course[s].day.equalsIgnoreCase("紐�")) {
    								for(int j = firstTime; j <= lastTime; j++)
    									thursday[j] = 1;
    							}
    							else if(course[s].day.equalsIgnoreCase("湲�")) {
    								for(int j = firstTime; j <= lastTime; j++)
    									friday[j] = 1;
    							}
    							select[count] = course[s]; //Store this course in Select object
    							count++;
    							course[s].choose = 1; //Selected course initializes the choose variable to 1
    						}
    					}
    					
    					for(int s = 0; s < i; s++) {
    						if(course[k].title.equalsIgnoreCase(course[s].title)) //Course with the same name initializes the choose variable to 1
    							course[s].choose = 1;
    					}
    					break;
    				}
    			}
    			
    			for(int k = 0; k < i; k++) {
    				String hour;
    				int time, length, firstTime, lastTime;
    				if(course[k].major.equalsIgnoreCase("�쟾�븘") && course[k].choose != 1) { //Part of the selection of courses required for major	
    					hour = course[k].time;
    					length = hour.length();
    					firstTime = (hour.charAt(0))-'0'; //Store time in integer type
    					lastTime = hour.charAt(length-1)-'0';
    		
    					if(course[k].day.equalsIgnoreCase("�썡")) { //Store the time of the course in the appropriate day
    						if(monday[firstTime] == 0 && monday[lastTime] == 0) { //Store if the day of the week array is empty
    							for(int j = firstTime; j <= lastTime; j++)
    								monday[j] = 1;
    						}
    						else //Otherwise, do not select
    							continue;
    					}
    					else if(course[k].day.equalsIgnoreCase("�솕")) {
    						if(tuesday[firstTime] == 0 && tuesday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								tuesday[j] = 1;
    						}
    						else
    							continue;
    					} 
    					else if(course[k].day.equalsIgnoreCase("�닔")) {
    						if(wednesday[firstTime] == 0 && wednesday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								wednesday[j] = 1;
    						}
    						else
    							continue;
    					}
    					else if(course[k].day.equalsIgnoreCase("紐�")) {
    						if(thursday[firstTime] == 0 && thursday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								thursday[j] = 1;
    						}
    						else
    							continue;
    					}
    					else if(course[k].day.equalsIgnoreCase("湲�")) {
    						if(friday[firstTime] == 0 && friday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								friday[j] = 1;
    						}
    						else
    							continue;
    					}
    					course[k].choose = 1; //If make a selection, initialize the choose variable to 1
    					subject = 1;
    					for(int s = 0; s < i; s++) {
    						if(course[k].course_id.equalsIgnoreCase(course[s].course_id) && course[s].choose != 1) { //Select the same course on different days of the week
    							subject = 2; //If have the same course on another day of the week, set subject variable to 2
    							String hour2;
    							hour2 = course[s].time;
    							int time2, length2, firstTime2, lastTime2;
    							
    							length2 = hour2.length();
    	    					firstTime2 = (hour2.charAt(0))-'0'; //Store time in integer type
    	    					lastTime2 = hour2.charAt(length2-1)-'0';
    	    		
    	    					if(course[s].day.equalsIgnoreCase("�썡")) { //Store the time of the course in the appropriate day
    								if(monday[firstTime2] == 0 && monday[lastTime2] == 0) { //Store if the day of the week array is empty
    									for(int j = firstTime2; j <= lastTime2; j++)
    										monday[j] = 1;
    									location = s; //Save location
    								}
    								else { //Otherwise, delete the previously saved course and initialize checkPoint variable to 1
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    							else if(course[s].day.equalsIgnoreCase("�솕")) {
    								if(tuesday[firstTime2] == 0 && tuesday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										tuesday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							} 
    							else if(course[s].day.equalsIgnoreCase("�닔")) {
    								if(wednesday[firstTime2] == 0 && wednesday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										wednesday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    							else if(course[s].day.equalsIgnoreCase("紐�")) {
    								if(thursday[firstTime2] == 0 && thursday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										thursday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    							else if(course[s].day.equalsIgnoreCase("湲�")) {
    								if(friday[firstTime2] == 0 && friday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										friday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    						}
    					}
    					
    					if(checkPoint == 1) { //If checkPoint is 1, an error occurs saving the second day of the class
    						if(course[k].day.equalsIgnoreCase("�썡")) { //So have to delete the course you have chosen before
    							for(int j = firstTime; j <= lastTime; j++)
    								monday[j] = 0;
    						}
    						else if(course[k].day.equalsIgnoreCase("�솕")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								tuesday[j] = 0;
    						} 
    						else if(course[k].day.equalsIgnoreCase("�닔")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								wednesday[j] = 0;
    						}
    						else if(course[k].day.equalsIgnoreCase("紐�")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								thursday[j] = 0;
    						}
    						else if(course[k].day.equalsIgnoreCase("湲�")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								friday[j] = 0;
    						}
    						subject = 0; //If delete course, initialize the subject variable to 0
    						continue;
    					}
    					
    					if(subject == 1) { //If the subject variable is 1, this is a one-day class
    						totalCredit = totalCredit + course[k].credit;
    						select[count] = course[k];
    						count++;
    						course[k].choose = 1; //Selected course initializes the choose variable to 1
    					}
    					else if(subject == 2) { //If the subject variable is 2, this is a two-day class
    						totalCredit = totalCredit + course[k].credit;
    						select[count] = course[k];
    						count++;
    						select[count] = course[location];
    						count++;
    						course[k].choose = 1; //Selected course initializes the choose variable to 1
    						course[location].choose = 1;
    					}
    					
    					for(int s = 0; s < i; s++) {
    						if(course[k].title.equalsIgnoreCase(course[s].title)) //Course with the same name initializes the choose variable to 1
    							course[s].choose = 1;
    					}
    					location = 0;
    					checkPoint = 0;
    				}
    			}
    			
    			for(int k = 0; k < i; k++) {
    				String hour;
    				int time, length, firstTime, lastTime;
    				if(course[k].major.equalsIgnoreCase("�쟾�븘") && course[k].choose != 1) {	 //Part of the selection of courses required for liberal art
    					hour = course[k].time;
    					length = hour.length();
    					firstTime = (hour.charAt(0))-'0'; //Store time in integer type
    					lastTime = hour.charAt(length-1)-'0';
    		
    					if(course[k].day.equalsIgnoreCase("�썡")) { //Store the time of the course in the appropriate day
    						if(monday[firstTime] == 0 && monday[lastTime] == 0) { //Store if the day of the week array is empty
    							for(int j = firstTime; j <= lastTime; j++)
    								monday[j] = 1;
    						}
    						else //Otherwise, do not select
    							continue;
    					}
    					else if(course[k].day.equalsIgnoreCase("�솕")) {
    						if(tuesday[firstTime] == 0 && tuesday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								tuesday[j] = 1;
    						}
    						else
    							continue;
    					} 
    					else if(course[k].day.equalsIgnoreCase("�닔")) {
    						if(wednesday[firstTime] == 0 && wednesday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								wednesday[j] = 1;
    						}
    						else
    							continue;
    					}
    					else if(course[k].day.equalsIgnoreCase("紐�")) {
    						if(thursday[firstTime] == 0 && thursday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								thursday[j] = 1;
    						}
    						else
    							continue;
    					}
    					else if(course[k].day.equalsIgnoreCase("湲�")) {
    						if(friday[firstTime] == 0 && friday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								friday[j] = 1;
    						}
    						else
    							continue;
    					}
    					course[k].choose = 1; //If make a selection, initialize the choose variable to 1
    					subject = 1;
    					for(int s = 0; s < i; s++) {
    						if(course[k].course_id.equalsIgnoreCase(course[s].course_id) && course[s].choose != 1) { //Select the same course on different days of the week
    							subject = 2; //If have the same course on another day of the week, set subject variable to 2
    							String hour2;
    							hour2 = course[s].time;
    							int time2, length2, firstTime2, lastTime2;
    							length2 = hour2.length();
    	    					firstTime2 = (hour2.charAt(0))-'0'; //Store time in integer type
    	    					lastTime2 = hour2.charAt(length2-1)-'0';
    	    				if(course[s].day.equalsIgnoreCase("�썡")) { //Store the time of the course in the appropriate day
    								if(monday[firstTime2] == 0 && monday[lastTime2] == 0) { //Store if the day of the week array is empty
    									for(int j = firstTime2; j <= lastTime2; j++)
    										monday[j] = 1;
    									location = s; //Save location
    								}
    								else { //Otherwise, delete the previously saved course and initialize checkPoint variable to 1
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    							else if(course[s].day.equalsIgnoreCase("�솕")) {
    								if(tuesday[firstTime2] == 0 && tuesday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										tuesday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							} 
    							else if(course[s].day.equalsIgnoreCase("�닔")) {
    								if(wednesday[firstTime2] == 0 && wednesday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										wednesday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    							else if(course[s].day.equalsIgnoreCase("紐�")) {
    								if(thursday[firstTime2] == 0 && thursday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										thursday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    							else if(course[s].day.equalsIgnoreCase("湲�")) {
    								if(friday[firstTime2] == 0 && friday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										friday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    						}
    					}
    					
    					if(checkPoint == 1) { //If checkPoint is 1, an error occurs saving the second day of the class
    						if(course[k].day.equalsIgnoreCase("�썡")) { //So have to delete the course you have chosen before
    							for(int j = firstTime; j <= lastTime; j++)
    								monday[j] = 0;
    						}
    						else if(course[k].day.equalsIgnoreCase("�솕")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								tuesday[j] = 0;
    						} 
    						else if(course[k].day.equalsIgnoreCase("�닔")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								wednesday[j] = 0;
    						}
    						else if(course[k].day.equalsIgnoreCase("紐�")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								thursday[j] = 0;
    						}
    						else if(course[k].day.equalsIgnoreCase("湲�")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								friday[j] = 0;
    						}
    						subject = 0; //If delete course, initialize the subject variable to 0
    						continue;
    					}
    					
    					if((totalCredit + course[k].credit) > 17) //Do not save if total credit exceeds 17 credits
    						break;
    					
    					if(subject == 1) { //If the subject variable is 1, this is a one-day class
    						totalCredit = totalCredit + course[k].credit;
    						select[count] = course[k];
    						count++;
    						course[k].choose = 1; //Selected course initializes the choose variable to 1
    					}
    					else if(subject == 2) { //If the subject variable is 2, this is a two-day class
    						totalCredit = totalCredit + course[k].credit;
    						select[count] = course[k];
    						count++;
    						select[count] = course[location];
    						count++;
    						course[k].choose = 1; //Selected course initializes the choose variable to 1
    						course[location].choose = 1;
    					}
    					for(int s = 0; s < i; s++) {
    						if(course[k].title.equalsIgnoreCase(course[s].title)) //Course with the same name initializes the choose variable to 1
    							course[s].choose = 1;
    					}
    					location = 0;
    					checkPoint = 0;
    				}
    			}
    			
    			for(int k = 0; k < i; k++) {
    				String hour;
    				int time, length, firstTime, lastTime;
    				if(course[k].choose != 1) { //Part of the selection of courses for remainder courses
    					hour = course[k].time;
    					length = hour.length();
    					firstTime = (hour.charAt(0))-'0'; //Store time in integer type
    					lastTime = hour.charAt(length-1)-'0';
    		
    					if(course[k].day.equalsIgnoreCase("�썡")) { //Store the time of the course in the appropriate day
    						if(monday[firstTime] == 0 && monday[lastTime] == 0) { //Store if the day of the week array is empty
    							for(int j = firstTime; j <= lastTime; j++)
    								monday[j] = 1;
    						}
    						else //Otherwise, do not select
    							continue;
    					}
    					else if(course[k].day.equalsIgnoreCase("�솕")) {
    						if(tuesday[firstTime] == 0 && tuesday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								tuesday[j] = 1;
    						}
    						else
    							continue;
    					} 
    					else if(course[k].day.equalsIgnoreCase("�닔")) {
    						if(wednesday[firstTime] == 0 && wednesday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								wednesday[j] = 1;
    						}
    						else
    							continue;
    					}
    					else if(course[k].day.equalsIgnoreCase("紐�")) {
    						if(thursday[firstTime] == 0 && thursday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								thursday[j] = 1;
    						}
    						else
    							continue;
    					}
    					else if(course[k].day.equalsIgnoreCase("湲�")) {
    						if(friday[firstTime] == 0 && friday[lastTime] == 0) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								friday[j] = 1;
    						}
    						else
    							continue;
    					}
    					course[k].choose = 1; //If make a selection, initialize the choose variable to 1
    					subject = 1;
    					for(int s = 0; s < i; s++) {
    						if(course[k].course_id.equalsIgnoreCase(course[s].course_id) && course[s].choose != 1) { //Select the same course on different days of the week
    							subject = 2; //If have the same course on another day of the week, set subject variable to 2
    							String hour2;
    							hour2 = course[s].time;
    							int time2, length2, firstTime2, lastTime2;
    							length2 = hour2.length();
    	    					firstTime2 = (hour2.charAt(0))-'0'; //Store time in integer type
    	    					lastTime2 = hour2.charAt(length2-1)-'0';
    	    					if(course[s].day.equalsIgnoreCase("�썡")) { //Store the time of the course in the appropriate day
    								if(monday[firstTime2] == 0 && monday[lastTime2] == 0) { //Store if the day of the week array is empty
    									for(int j = firstTime2; j <= lastTime2; j++)
    										monday[j] = 1;
    									location = s; //Save location
    								}
    								else { //Otherwise, delete the previously saved course and initialize checkPoint variable to 1
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    							else if(course[s].day.equalsIgnoreCase("�솕")) {
    								if(tuesday[firstTime2] == 0 && tuesday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										tuesday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							} 
    							else if(course[s].day.equalsIgnoreCase("�닔")) {
    								if(wednesday[firstTime2] == 0 && wednesday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										wednesday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    							else if(course[s].day.equalsIgnoreCase("紐�")) {
    								if(thursday[firstTime2] == 0 && thursday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										thursday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    							else if(course[s].day.equalsIgnoreCase("湲�")) {
    								if(friday[firstTime2] == 0 && friday[lastTime2] == 0) {	
    									for(int j = firstTime2; j <= lastTime2; j++)
    										friday[j] = 1;
    									location = s;
    								}
    								else {
    									course[k].choose = 0;
    									checkPoint = 1;
    									break;
    								}
    							}
    						}
    					}
    					
    					if(checkPoint == 1) { //If checkPoint is 1, an error occurs saving the second day of the class
    						if(course[k].day.equalsIgnoreCase("�썡")) { //So have to delete the course you have chosen before
    							for(int j = firstTime; j <= lastTime; j++)
    								monday[j] = 0;
    						}
    						else if(course[k].day.equalsIgnoreCase("�솕")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								tuesday[j] = 0;
    						} 
    						else if(course[k].day.equalsIgnoreCase("�닔")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								wednesday[j] = 0;
    						}
    						else if(course[k].day.equalsIgnoreCase("紐�")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								thursday[j] = 0;
    						}
    						else if(course[k].day.equalsIgnoreCase("湲�")) {	
    							for(int j = firstTime; j <= lastTime; j++)
    								friday[j] = 0;
    						}
    						subject = 0; //If delete course, initialize the subject variable to 0
    						continue;
    					}
    					if((totalCredit + course[k].credit) > 17) //Do not save if total credit exceeds 17 credits
    						break;
    					
    					if(subject == 1) { //If the subject variable is 1, this is a one-day class
    						totalCredit = totalCredit + course[k].credit;
    						select[count] = course[k];
    						count++;
    						course[k].choose = 1; //Selected course initializes the choose variable to 1
    					}
    					else if(subject == 2) { //If the subject variable is 2, this is a two-day class
    						totalCredit = totalCredit + course[k].credit;
    						select[count] = course[k];
    						count++;
    						select[count] = course[location];
    						count++;
    						course[k].choose = 1; //Selected course initializes the choose variable to 1
    						course[location].choose = 1;
    					}
    					
    					for(int s = 0; s < i; s++) {
    						if(course[k].title.equalsIgnoreCase(course[s].title)) //Course with the same name initializes the choose variable to 1
    							course[s].choose = 1;
    					}
    					location = 0;
    					checkPoint = 0;
    				}
    			}

    			for(int k = 0; k < count; k++) { //Store selected courses to database
        			if(select[k].grade == 0) //If there is no selected course, break
        				break;
    				String nsql = null;
        			nsql = "insert into takes values(?,?,?,?,?,?,?,?,?,?,?)";
        			ps = con.prepareStatement(nsql);
        			ps.setString(1, id);
        			ps.setString(2, select[k].course_id);
        			ps.setString(3, select[k].title);
        			ps.setString(4, select[k].major);
        			ps.setInt(5, select[k].grade);
        			ps.setInt(6, select[k].semester);
        			ps.setInt(7, select[k].credit);
        			ps.setString(8, select[k].instructor);
        			ps.setInt(9, select[k].subID);
        			ps.setString(10, select[k].day);
        			ps.setString(11, select[k].time);
        			ps.executeUpdate();
    			}

    			out.println("CREATED");
            } catch(SQLException e) {
            	e.printStackTrace();
            }
        }
    }
}
