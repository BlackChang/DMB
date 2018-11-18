package Network_DMB;
import java.sql.*;

public class timetable_algorithm2 {
	
	public static class Lecture{
		private String course_id;
		private String title;
		private String major;
		private float grade;
		private int semester;
		private float credit;
		private String instructor;
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
			this.day = "";
			this.time = "";
			this.choose = 0;
		}
	}
	public static String professor;
	public static int semester;
	public static float grade;
	public static String restDay;

	public static void main(String[] args) {
		Lecture[] course = new Lecture[20];
		Lecture[] select = new Lecture[10];
 		
		for(int k = 0; k < 20; k++)
			course[k] = new Lecture();
 		
		for(int k = 0; k < 10; k++)
 			select[k] = new Lecture();
 		int count = 0;
		int i = 0;
		float totalCredit = 0;
		int[] monday = new int[] {1,0,0,0,0,0,0,0,0,0,0};
		int[] tuesday = new int[] {1,0,0,0,0,0,0,0,0,0,0};
		int[] wednesday = new int[] {1,0,0,0,0,0,0,0,0,0,0};
		int[] thursday = new int[] {1,0,0,0,0,0,0,0,0,0,0};
		int[] friday = new int[] {1,0,0,0,0,0,0,0,0,0,0};
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from course natural join timetable where grade = " + grade + "and semester = " + semester + "and day not in(select day from timetable where day = '" + restDay + "')";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				course[i].course_id = rs.getString(1);
				if(rs.wasNull())
					course[i].course_id = "";
				
				course[i].title = rs.getString(2);
				if(rs.wasNull())
					course[i].title = "";
				
				course[i].major = rs.getString(3);
				if(rs.wasNull())
					course[i].major = "";
				
				course[i].grade = rs.getFloat(4);
				if(rs.wasNull())
					course[i].grade = 0;
				
				course[i].semester = rs.getInt(5);
				if(rs.wasNull())
					course[i].semester = 0;
				
				course[i].credit = rs.getFloat(6);
				if(rs.wasNull())
					course[i].credit = 0;
				
				course[i].instructor = rs.getString(7);
				if(rs.wasNull())
					course[i].instructor = "";
				
				course[i].day = rs.getString(8);
				if(rs.wasNull())
					course[i].day = "";
				
				course[i].time = rs.getString(9);
				if(rs.wasNull())
					course[i].time = "";
				course[i].choose = 0;
				i++;
			}
			
			for(int k = 0; k < i; k++) {
				if(course[k].instructor.equalsIgnoreCase(professor)) {
					String hour;
					hour = course[k].time;
					int time, length, firstTime, lastTime;
					if(Character.isDigit(hour.charAt(0))) {
						length = hour.length();
						time = Integer.parseInt(hour);
						if(course[k].day.equalsIgnoreCase("월")) {
							firstTime = time/(10*(length - 1));
							lastTime = time % 10;
							for(int j = firstTime; j <= lastTime; j++)
								monday[j] = 1;
						}
						else if(course[k].day.equalsIgnoreCase("화")) {
							firstTime = time/(10*(length - 1));
							lastTime = time % 10;
							for(int j = firstTime; j <= lastTime; j++)
								tuesday[j] = 1;
						} 
						else if(course[k].day.equalsIgnoreCase("수")) {
							firstTime = time/(10*(length - 1));
							lastTime = time % 10;
							for(int j = firstTime; j <= lastTime; j++)
								wednesday[j] = 1;
						}
						else if(course[k].day.equalsIgnoreCase("목")) {
							firstTime = time/(10*(length - 1));
							lastTime = time % 10;
							for(int j = firstTime; j <= lastTime; j++)
								thursday[j] = 1;
						}
						else if(course[k].day.equalsIgnoreCase("금")) {
							firstTime = time/(10*(length - 1));
							lastTime = time % 10;
							for(int j = firstTime; j <= lastTime; j++)
								friday[j] = 1;
						}
						else {
							if(course[k].day.equalsIgnoreCase("월")) {
								if(hour.charAt(0) == 'A') {
									for(int j = 1; j < 3; j++)
										monday[j] = 1;
								}
								else if(hour.charAt(0) == 'B') {
									for(int j = 2; j < 4; j++)
										monday[j] = 1;
								}
								else if(hour.charAt(0) == 'C') {
									for(int j = 4; j < 6; j++)
										monday[j] = 1;
								}
								else if(hour.charAt(0) == 'D') {
									for(int j = 5; j < 7; j++)
										monday[j] = 1;
								}
								else if(hour.charAt(0) == 'E') {
									for(int j = 7; j < 9; j++)
										monday[j] = 1;
								}
								else if(hour.charAt(0) == 'F') {
									for(int j = 8; j < 10; j++)
										monday[j] = 1;
								}
							}
							else if(course[k].day.equalsIgnoreCase("화")) {
								if(hour.charAt(0) == 'A') {
									for(int j = 1; j < 3; j++)
										tuesday[j] = 1;
								}
								else if(hour.charAt(0) == 'B') {
									for(int j = 2; j < 4; j++)
										tuesday[j] = 1;
								}
								else if(hour.charAt(0) == 'C') {
									for(int j = 4; j < 6; j++)
										tuesday[j] = 1;
								}
								else if(hour.charAt(0) == 'D') {
									for(int j = 5; j < 7; j++)
										tuesday[j] = 1;
								}
								else if(hour.charAt(0) == 'E') {
									for(int j = 7; j < 9; j++)
										tuesday[j] = 1;
								}
								else if(hour.charAt(0) == 'F') {
									for(int j = 8; j < 10; j++)
										tuesday[j] = 1;
								}
							}
							else if(course[k].day.equalsIgnoreCase("수")) {
								if(hour.charAt(0) == 'A') {
									for(int j = 1; j < 3; j++)
										wednesday[j] = 1;
								}
								else if(hour.charAt(0) == 'B') {
									for(int j = 2; j < 4; j++)
										wednesday[j] = 1;
								}
								else if(hour.charAt(0) == 'C') {
									for(int j = 4; j < 6; j++)
										wednesday[j] = 1;
								}
								else if(hour.charAt(0) == 'D') {
									for(int j = 5; j < 7; j++)
										wednesday[j] = 1;
								}
								else if(hour.charAt(0) == 'E') {
									for(int j = 7; j < 9; j++)
										wednesday[j] = 1;
								}
								else if(hour.charAt(0) == 'F') {
									for(int j = 8; j < 10; j++)
										wednesday[j] = 1;
								}
							}
							else if(course[k].day.equalsIgnoreCase("목")) {
								if(hour.charAt(0) == 'A') {
									for(int j = 1; j < 3; j++)
										thursday[j] = 1;
								}
								else if(hour.charAt(0) == 'B') {
									for(int j = 2; j < 4; j++)
										thursday[j] = 1;
								}
								else if(hour.charAt(0) == 'C') {
									for(int j = 4; j < 6; j++)
										thursday[j] = 1;
								}
								else if(hour.charAt(0) == 'D') {
									for(int j = 5; j < 7; j++)
										thursday[j] = 1;
								}
								else if(hour.charAt(0) == 'E') {
									for(int j = 7; j < 9; j++)
										thursday[j] = 1;
								}
								else if(hour.charAt(0) == 'F') {
									for(int j = 8; j < 10; j++)
										thursday[j] = 1;
								}
							}
							else if(course[k].day.equalsIgnoreCase("금")) {
								if(hour.charAt(0) == 'A') {
									for(int j = 1; j < 3; j++)
										friday[j] = 1;
								}
								else if(hour.charAt(0) == 'B') {
									for(int j = 2; j < 4; j++)
										friday[j] = 1;
								}
								else if(hour.charAt(0) == 'C') {
									for(int j = 4; j < 6; j++)
										friday[j] = 1;
								}
								else if(hour.charAt(0) == 'D') {
									for(int j = 5; j < 7; j++)
										friday[j] = 1;
								}
								else if(hour.charAt(0) == 'E') {
									for(int j = 7; j < 9; j++)
										friday[j] = 1;
								}
								else if(hour.charAt(0) == 'F') {
									for(int j = 8; j < 10; j++)
										friday[j] = 1;
								}
							}
						}
						totalCredit = totalCredit + course[k].credit;
						select[0] = course[k];
						course[k].choose = 1;
						break;
					}
				}
			}
		} catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
}