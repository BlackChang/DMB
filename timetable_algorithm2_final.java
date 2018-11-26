package Network_DMB;
import java.sql.*;

public class timetable_algorithm2_final {
	
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
		int checkPoint = 0;
		int location = 0;
		int subject = 0;
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
					length = hour.length();
					time = Integer.parseInt(hour);
					firstTime = time/(10*(length - 1));
					lastTime = time % 10;
					if(course[k].day.equalsIgnoreCase("월")) {
						for(int j = firstTime; j <= lastTime; j++)
							monday[j] = 1;
					}
					else if(course[k].day.equalsIgnoreCase("화")) {
						for(int j = firstTime; j <= lastTime; j++)
							tuesday[j] = 1;
					} 
					else if(course[k].day.equalsIgnoreCase("수")) {
						for(int j = firstTime; j <= lastTime; j++)
							wednesday[j] = 1;
					}
					else if(course[k].day.equalsIgnoreCase("목")) {
						for(int j = firstTime; j <= lastTime; j++)
							thursday[j] = 1;
					}
					else if(course[k].day.equalsIgnoreCase("금")) {
						for(int j = firstTime; j <= lastTime; j++)
							friday[j] = 1;
					}
					totalCredit = totalCredit + course[k].credit;
					select[count] = course[k];
					count++;
					course[k].choose = 1;
					for(int s = 0; s < i; s++) {
						if(course[k].course_id.equalsIgnoreCase(course[s].course_id) && course[s].choose != 1) {
							hour = course[s].time;
							length = hour.length();
							time = Integer.parseInt(hour);
							firstTime = time/(10*(length - 1));
							lastTime = time % 10;
							if(course[s].day.equalsIgnoreCase("월")) {
								for(int j = firstTime; j <= lastTime; j++)
									monday[j] = 1;
							}
							else if(course[s].day.equalsIgnoreCase("화")) {
								for(int j = firstTime; j <= lastTime; j++)
									tuesday[j] = 1;
							} 
							else if(course[s].day.equalsIgnoreCase("수")) {
								for(int j = firstTime; j <= lastTime; j++)
									wednesday[j] = 1;
							}
							else if(course[s].day.equalsIgnoreCase("목")) {
								for(int j = firstTime; j <= lastTime; j++)
									thursday[j] = 1;
							}
							else if(course[s].day.equalsIgnoreCase("금")) {
								for(int j = firstTime; j <= lastTime; j++)
									friday[j] = 1;
							}
							select[count] = course[s];
							count++;
							course[s].choose = 1;
						}
					}
					
					for(int s = 0; s < i; s++) {
						if(course[k].title.equalsIgnoreCase(course[s].title))
							course[s].choose = 1;
					}
					break;
				}
			}
			
			for(int k = 0; k < i; k++) {
				String hour;
				int time, length, firstTime, lastTime;
				if(course[k].major.equalsIgnoreCase("전필") && course[k].choose != 1) {			
					hour = course[k].time;
					length = hour.length();
					time = Integer.parseInt(hour);
					firstTime = time/(10*(length - 1));
					lastTime = time % 10;
					if(course[k].day.equalsIgnoreCase("월")) {
						if(monday[firstTime] == 0 && monday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								monday[j] = 1;
						}
						else
							continue;
					}
					else if(course[k].day.equalsIgnoreCase("화")) {
						if(tuesday[firstTime] == 0 && tuesday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								tuesday[j] = 1;
						}
						else
							continue;
					} 
					else if(course[k].day.equalsIgnoreCase("수")) {
						if(wednesday[firstTime] == 0 && wednesday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								wednesday[j] = 1;
						}
						else
							continue;
					}
					else if(course[k].day.equalsIgnoreCase("목")) {
						if(thursday[firstTime] == 0 && thursday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								thursday[j] = 1;
						}
						else
							continue;
					}
					else if(course[k].day.equalsIgnoreCase("금")) {
						if(friday[firstTime] == 0 && friday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								friday[j] = 1;
						}
						else
							continue;
					}
					course[k].choose = 1;
					subject = 1;
					for(int s = 0; s < i; s++) {
						if(course[k].course_id.equalsIgnoreCase(course[s].course_id) && course[s].choose != 1) {
							subject = 2;
							String hour2;
							hour2 = course[s].time;
							int time2, length2, firstTime2, lastTime2;
							length2 = hour2.length();
							time2 = Integer.parseInt(hour2);
							firstTime2 = time/(10*(length2 - 1));
							lastTime2 = time % 10;
							if(course[s].day.equalsIgnoreCase("월")) {
								if(monday[firstTime2] == 0 && monday[lastTime2] == 0) {	
									for(int j = firstTime2; j <= lastTime2; j++)
										monday[j] = 1;
									location = s;
								}
								else {
									course[k].choose = 0;
									checkPoint = 1;
									break;
								}
							}
							else if(course[s].day.equalsIgnoreCase("화")) {
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
							else if(course[s].day.equalsIgnoreCase("수")) {
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
							else if(course[s].day.equalsIgnoreCase("목")) {
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
							else if(course[s].day.equalsIgnoreCase("금")) {
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
					
					if(checkPoint == 1) {
						if(course[k].day.equalsIgnoreCase("월")) {	
							for(int j = firstTime; j <= lastTime; j++)
								monday[j] = 0;
						}
						else if(course[k].day.equalsIgnoreCase("화")) {	
							for(int j = firstTime; j <= lastTime; j++)
								tuesday[j] = 0;
						} 
						else if(course[k].day.equalsIgnoreCase("수")) {	
							for(int j = firstTime; j <= lastTime; j++)
								wednesday[j] = 0;
						}
						else if(course[k].day.equalsIgnoreCase("목")) {	
							for(int j = firstTime; j <= lastTime; j++)
								thursday[j] = 0;
						}
						else if(course[k].day.equalsIgnoreCase("금")) {	
							for(int j = firstTime; j <= lastTime; j++)
								friday[j] = 0;
						}
						subject = 0;
						continue;
					}
					
					if(subject == 1) {
						totalCredit = totalCredit + course[k].credit;
						select[count] = course[k];
						count++;
						course[k].choose = 1;
					}
					else if(subject == 2) {
						totalCredit = totalCredit + course[k].credit;
						select[count] = course[k];
						count++;
						select[count] = course[location];
						count++;
						course[k].choose = 1;
						course[location].choose = 1;
					}
					
					for(int s = 0; s < i; s++) {
						if(course[k].title.equalsIgnoreCase(course[s].title))
							course[s].choose = 1;
					}
					location = 0;
					checkPoint = 0;
				}
			}
			
			for(int k = 0; k < i; k++) {
				String hour;
				int time, length, firstTime, lastTime;
				if(course[k].major.equalsIgnoreCase("교필") && course[k].choose != 1) {			
					hour = course[k].time;
					length = hour.length();
					time = Integer.parseInt(hour);
					firstTime = time/(10*(length - 1));
					lastTime = time % 10;
					if(course[k].day.equalsIgnoreCase("월")) {
						if(monday[firstTime] == 0 && monday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								monday[j] = 1;
						}
						else
							continue;
					}
					else if(course[k].day.equalsIgnoreCase("화")) {
						if(tuesday[firstTime] == 0 && tuesday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								tuesday[j] = 1;
						}
						else
							continue;
					} 
					else if(course[k].day.equalsIgnoreCase("수")) {
						if(wednesday[firstTime] == 0 && wednesday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								wednesday[j] = 1;
						}
						else
							continue;
					}
					else if(course[k].day.equalsIgnoreCase("목")) {
						if(thursday[firstTime] == 0 && thursday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								thursday[j] = 1;
						}
						else
							continue;
					}
					else if(course[k].day.equalsIgnoreCase("금")) {
						if(friday[firstTime] == 0 && friday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								friday[j] = 1;
						}
						else
							continue;
					}
					course[k].choose = 1;
					subject = 1;
					for(int s = 0; s < i; s++) {
						if(course[k].course_id.equalsIgnoreCase(course[s].course_id) && course[s].choose != 1) {
							subject = 2;
							String hour2;
							hour2 = course[s].time;
							int time2, length2, firstTime2, lastTime2;
							length2 = hour2.length();
							time2 = Integer.parseInt(hour2);
							firstTime2 = time/(10*(length2 - 1));
							lastTime2 = time % 10;
							if(course[s].day.equalsIgnoreCase("월")) {
								if(monday[firstTime2] == 0 && monday[lastTime2] == 0) {	
									for(int j = firstTime2; j <= lastTime2; j++)
										monday[j] = 1;
									location = s;
								}
								else {
									course[k].choose = 0;
									checkPoint = 1;
									break;
								}
							}
							else if(course[s].day.equalsIgnoreCase("화")) {
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
							else if(course[s].day.equalsIgnoreCase("수")) {
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
							else if(course[s].day.equalsIgnoreCase("목")) {
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
							else if(course[s].day.equalsIgnoreCase("금")) {
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
					
					if(checkPoint == 1) {
						if(course[k].day.equalsIgnoreCase("월")) {	
							for(int j = firstTime; j <= lastTime; j++)
								monday[j] = 0;
						}
						else if(course[k].day.equalsIgnoreCase("화")) {	
							for(int j = firstTime; j <= lastTime; j++)
								tuesday[j] = 0;
						} 
						else if(course[k].day.equalsIgnoreCase("수")) {	
							for(int j = firstTime; j <= lastTime; j++)
								wednesday[j] = 0;
						}
						else if(course[k].day.equalsIgnoreCase("목")) {	
							for(int j = firstTime; j <= lastTime; j++)
								thursday[j] = 0;
						}
						else if(course[k].day.equalsIgnoreCase("금")) {	
							for(int j = firstTime; j <= lastTime; j++)
								friday[j] = 0;
						}
						subject = 0;
						continue;
					}
					
					if((totalCredit + course[k].credit) > 17)
						break;
					
					if(subject == 1) {
						totalCredit = totalCredit + course[k].credit;
						select[count] = course[k];
						count++;
						course[k].choose = 1;
					}
					else if(subject == 2) {
						totalCredit = totalCredit + course[k].credit;
						select[count] = course[k];
						count++;
						select[count] = course[location];
						count++;
						course[k].choose = 1;
						course[location].choose = 1;
					}
					for(int s = 0; s < i; s++) {
						if(course[k].title.equalsIgnoreCase(course[s].title))
							course[s].choose = 1;
					}
					location = 0;
					checkPoint = 0;
				}
			}
			
			for(int k = 0; k < i; k++) {
				String hour;
				int time, length, firstTime, lastTime;
				if(course[k].choose != 1) {			
					hour = course[k].time;
					length = hour.length();
					time = Integer.parseInt(hour);
					firstTime = time/(10*(length - 1));
					lastTime = time % 10;
					if(course[k].day.equalsIgnoreCase("월")) {
						if(monday[firstTime] == 0 && monday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								monday[j] = 1;
						}
						else
							continue;
					}
					else if(course[k].day.equalsIgnoreCase("화")) {
						if(tuesday[firstTime] == 0 && tuesday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								tuesday[j] = 1;
						}
						else
							continue;
					} 
					else if(course[k].day.equalsIgnoreCase("수")) {
						if(wednesday[firstTime] == 0 && wednesday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								wednesday[j] = 1;
						}
						else
							continue;
					}
					else if(course[k].day.equalsIgnoreCase("목")) {
						if(thursday[firstTime] == 0 && thursday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								thursday[j] = 1;
						}
						else
							continue;
					}
					else if(course[k].day.equalsIgnoreCase("금")) {
						if(friday[firstTime] == 0 && friday[lastTime] == 0) {	
							for(int j = firstTime; j <= lastTime; j++)
								friday[j] = 1;
						}
						else
							continue;
					}
					course[k].choose = 1;
					subject = 1;
					for(int s = 0; s < i; s++) {
						if(course[k].course_id.equalsIgnoreCase(course[s].course_id) && course[s].choose != 1) {
							subject = 2;
							String hour2;
							hour2 = course[s].time;
							int time2, length2, firstTime2, lastTime2;
							length2 = hour2.length();
							time2 = Integer.parseInt(hour2);
							firstTime2 = time/(10*(length2 - 1));
							lastTime2 = time % 10;
							if(course[s].day.equalsIgnoreCase("월")) {
								if(monday[firstTime2] == 0 && monday[lastTime2] == 0) {	
									for(int j = firstTime2; j <= lastTime2; j++)
										monday[j] = 1;
									location = s;
								}
								else {
									course[k].choose = 0;
									checkPoint = 1;
									break;
								}
							}
							else if(course[s].day.equalsIgnoreCase("화")) {
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
							else if(course[s].day.equalsIgnoreCase("수")) {
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
							else if(course[s].day.equalsIgnoreCase("목")) {
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
							else if(course[s].day.equalsIgnoreCase("금")) {
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
					
					if(checkPoint == 1) {
						if(course[k].day.equalsIgnoreCase("월")) {	
							for(int j = firstTime; j <= lastTime; j++)
								monday[j] = 0;
						}
						else if(course[k].day.equalsIgnoreCase("화")) {	
							for(int j = firstTime; j <= lastTime; j++)
								tuesday[j] = 0;
						} 
						else if(course[k].day.equalsIgnoreCase("수")) {	
							for(int j = firstTime; j <= lastTime; j++)
								wednesday[j] = 0;
						}
						else if(course[k].day.equalsIgnoreCase("목")) {	
							for(int j = firstTime; j <= lastTime; j++)
								thursday[j] = 0;
						}
						else if(course[k].day.equalsIgnoreCase("금")) {	
							for(int j = firstTime; j <= lastTime; j++)
								friday[j] = 0;
						}
						subject = 0;
						continue;
					}
					if((totalCredit + course[k].credit) > 17)
						break;
					
					if(subject == 1) {
						totalCredit = totalCredit + course[k].credit;
						select[count] = course[k];
						count++;
						course[k].choose = 1;
					}
					else if(subject == 2) {
						totalCredit = totalCredit + course[k].credit;
						select[count] = course[k];
						count++;
						select[count] = course[location];
						count++;
						course[k].choose = 1;
						course[location].choose = 1;
					}
					
					for(int s = 0; s < i; s++) {
						if(course[k].title.equalsIgnoreCase(course[s].title))
							course[s].choose = 1;
					}
					location = 0;
					checkPoint = 0;
				}
			}
		} catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
}