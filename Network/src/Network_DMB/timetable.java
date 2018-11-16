package Network_DMB;
import java.sql.*;

public class timetable {
	
	public static class Lecture{
		private String course_id;
		private String title;
		private String major;
		private float grade;
		private int semester;
		private float credit;
		private String instructor;
		
		public Lecture() {
			this.course_id = "";
			this.title = "";
			this.major = "";
			this.grade = 0;
			this.semester = 0;
			this.credit = 0;
			this.instructor = "";
		}
	}
	public static String professor;
	public static int semester;
	public static float grade;

	public static void main(String[] args) {
		Lecture[] course = new Lecture[20];
		for(int k = 0; k < 20; k++)
			course[k] = new Lecture();
		int i = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			String sql = "select * from course where grade = " + grade + "and semester = " + semester;
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
				
				i++;
			}
		} catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
}
