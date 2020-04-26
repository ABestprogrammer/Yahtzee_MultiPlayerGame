package utils;
import java.sql.*;

public class DBconnect {
	
	private String dbserver;
	private String db;
	private String user;
	private String pass;

	private boolean fConn;
	private Connection conn;
	private Statement st;
	private ResultSet rs;

	private String sql;
	private String Return,S;
	private boolean fC; //czy komunikaty
	

	//----------------------------------------------------------------------
	public DBconnect (String dbserver, String db, String user, String pass) {
		this.dbserver = dbserver;
		this.db = db;
		this.user = user;
		this.pass = pass;
		
		fConn=false;
		sql=""; S=""; Return="";
		//show local comments
		fC=false;
		//fC=true;
	}

	//----------------------------------------------------------------------
	public String Connect () {
		Return="";
		if(fConn){ return Return; }
		
		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName("org.mariadb.jdbc.Driver");
			// jdbc:mysql://localhost:3306/yahtzee
			
			
			conn = DriverManager.getConnection("jdbc:mysql://"+dbserver+":3306/"+db, user, pass); 
			st = conn.createStatement();
			fConn=true;
			
			Return = "DB connect to jdbc:mysql://"+dbserver+":3306/"+db;
			if(fC){ System.out.println(Return); }
		}
		catch (Exception e) {
				Return = "can't connect to jdbc:mysql://"+dbserver+":3306/"+db + "\n" + e.toString();
			if(fC){ System.out.println(Return); }
			//util.showAlert("err","Error DB connection",Return);
		}
		
		return Return;
	}
	//----------------------------------------------------------------------
	//sprawdzanie w bazie czy jest uzytkownik
	public String loginCheck (String user, String pass) {
		Return = "";
		if(!fConn) {
			S = "loginCheck() error - problem with DB connecton";
			if(fC){System.out.println(S);}
			return S;
		}

		if(user.equals(""))  { Return = "type username, paswword"; return Return; }
		
		try {
			sql = "SELECT pass FROM `users` WHERE login='"+user+"'";
			S="";
			rs = st.executeQuery(sql);
			while(rs.next()){
				S = rs.getString("pass");
				break;
			}
			     if(S.equals(""))  { Return = "user '"+user+"' hasn't account in myYahtzee"; }
			else if(pass.equals(S)){ Return = "login_ok"; }
			else                   { Return = "bad password for user '"+user+"'"; }
			
		}
		catch (Exception ex){
				S = "loginCheck() error - problem with DB connecton\n" + ex.toString();
			if(fC){ System.out.println(S); }
			//util.showAlert("err","Error DB connection",S);
		}
		
		return Return;
	}
	
	//----------------------------------------------------------------------
	//wyciaganie info o uzytkownikach
	public String loginGetInfo (String user) {
		String Return = "";

		if(!fConn) {
			if(fC){System.out.println("loginGetInfo() error - problem with DB connecton");}
			return Return;
		}

		try {
			sql = "SELECT name,surname,description FROM `users` WHERE login='"+user+"'";
				S="";
			rs = st.executeQuery(sql);
			while(rs.next()){
				Return += rs.getString("name");
				Return += "|";
				Return += rs.getString("surname");
				Return += "|";
				Return += rs.getString("description");
				break;
			}
		}
		catch (Exception e) {
				S = "loginGet() error - problem with DB connecton\n" + e.toString();
			if(fC){ System.out.println(S); }
			//util.showAlert("err","Error DB connection",S);
			}
		
		return Return;
	}
	//----------------------------------------------------------------------
	//tmp,czy dostep do bazy- nie uzywane
	public void showTable () {
		if(!fConn) {
				S = "showTable() error - problem with DB connecton";
			if(fC){ System.out.println(S); }
			return;
		}
		String id,login,name,surname,description;
		
		try {
			//sql = "SELECT id,login,name,surname,description FROM users WHERE 1";
			sql = "SELECT * FROM `users` WHERE 1";
			rs = st.executeQuery(sql);
			
			System.out.println("query = " + sql);
			while(rs.next()){
				id = rs.getString("id");
				login = rs.getString("login");
				name = rs.getString("name");
				surname = rs.getString("surname");
				description = rs.getString("description");
				
				System.out.println(" "+id+", "+login+", "+name+", "+surname+", "+description);
			}
		}
		catch (Exception ex){
				S = "showTable() error - problem with DB connecton\n" + ex.toString();
			if(fC){ System.out.println(S); }
			//util.showAlert("err","Error DB connection",S);
		}
	}

	//----------------------------------------------------------------------
	public String Disconnect() {
		Return="";
		if(!fConn) {
			if(fC){ Return = "Error disconnect() - problem with DB connecton"; System.out.println(Return); }
			return Return;
		}
		try {
			conn.close();
			fConn=false;
			Return = "DB disconnect";
			if(fC){ System.out.println(Return); }
		}
		catch (Exception e) {
			Return = "disconnect() error - problem with DB connecton\n" + e.toString();
			if(fC){ System.out.println(Return); }
			//util.showAlert("err","Error DB connection",S);
		}
		
		return Return;
	}
	
	//----------------------------------------------------------------------
	public void Test() {
		S = getClass().getName();
		System.out.println("I'm object " + S);
	}
	//----------------------------------------------------------------------
}
