package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.*;
import java.util.concurrent.locks.ReentrantLock;

import utils.*;

public class socketServer {

	private static ServerSocket server;
	private static int port = 7722;
	
	public static DBconnect dbConn;
	private static int[] Panel;	//tablica dwoch paneli - logika zajmowania
	private static int[] Sum;	//obliczanie wyniku kostek
	
	private static boolean fRun;  //flaga uruchomienia glownej petli serwera
	private static boolean fCom;  //flaga czy widoczne komentarze
	private static boolean fJoin; //flaga czy zastosowac joiny dla watkow(kostek)
	private static boolean twoPlayer; //flaga trybu gry
	
	private static Dice dice[];	//tabela obiektow typu Dice - kosci	
	public static int[] Dices;	//tabela wynikow
	public static boolean[] fDice;	//flaga czy utworzyl sie obiekt Dice

	
	private static String[] messageSplit; //rozdzielanie message po znaku |
	private static String message,Return,S,S1; //to co otrzymuje serwer od klienta, to co zwraca, pomoc.,pomoc.
	private static int i,j;
	
//----------------------------------------------------------------------
	private static void serverAction() {

		Return="";
		
		if (message.contains("|")) {
			for(i=0; i<10; i++){ messageSplit[i] = ""; }
			
			String[] t = message.split("\\|");
			//for(i=0; i<t.length; i++){ System.out.println("t["+i+"] = " + t[i]); }
			j = t.length; if(j>10){ j=10; }
			for(i=0; i<j; i++){ if(t[i] != null){messageSplit[i] = t[i];} }
		}
		else {
			messageSplit[0] = message;
		}
		
		// Actions
        if(messageSplit[0].equalsIgnoreCase("login")){
        	loginAction();
        }
        else if(messageSplit[0].equalsIgnoreCase("getPanel")){
        	getPanelAction();
        }
        else if(messageSplit[0].equalsIgnoreCase("releasePanel")){
        	releasePanelAction(messageSplit[1]);
        }
        else if(messageSplit[0].equalsIgnoreCase("if2Player")){
        	if2PlayerAction();
        }
        else if(messageSplit[0].equalsIgnoreCase("set2Player")){
        	set2PlayerAction();
        }
        else if(messageSplit[0].equalsIgnoreCase("unset2Player")){
        	unset2PlayerAction();
        }
        else if(messageSplit[0].equalsIgnoreCase("check2Player")){
        	check2PlayerAction();
        	waitAction(500);
        }
        else if(messageSplit[0].equalsIgnoreCase("saveSum")){
        	saveSumAction();
        }
        else if(messageSplit[0].equalsIgnoreCase("getSum")){
        	getSumAction();
        }
        else if(messageSplit[0].equalsIgnoreCase("loginGetInfo")){
        	loginGetInfoAction();
        	Generate6dicesTmpAction(); //tmp
        }
        else if(messageSplit[0].equalsIgnoreCase("generate6dices")){
        	//for(i=0; i<12; i++){ Dices[i]=0; }
        	//waitAction(2000);
        	Generate6dicesAction(messageSplit[1]);	//generacja 6 obiektów Dice
        	Get6dicesAction(messageSplit[1]);		//zbieranie wygenerowanych liczb
        	//System.out.println(" socketServer: generate6dices");
        	//S1=" ";
        	//for(i=0; i<12; i++){
			//	S1+= i + ":"+Integer.toString(Dices[i])+", ";
			//}
        	//System.out.println(S1);
        }
        else if(messageSplit[0].equalsIgnoreCase("generate6dicesAll")){
        	Generate6dicesAction(messageSplit[1]);
        	Get12dicesAction(messageSplit[1]);
        }
        else if(messageSplit[0].equalsIgnoreCase("threadJoin")){
        	     if(messageSplit[1].equalsIgnoreCase("on")) { fJoin = true; }
   	     	else if(messageSplit[1].equalsIgnoreCase("off")){ fJoin = false; }
        }
        else if(messageSplit[0].equalsIgnoreCase("comments")){
        	     if(messageSplit[1].equalsIgnoreCase("on")) { fCom = true;  System.out.println(" socketServer: comments|on"); }
        	else if(messageSplit[1].equalsIgnoreCase("off")){ fCom = false; }
        }
        else if(messageSplit[0].equalsIgnoreCase("exit")){
        	Return = "server_stop";
        }
        else if(messageSplit[0].equalsIgnoreCase("test")){
        	testAction();
        }
        else if(messageSplit[0].equalsIgnoreCase("test2")){
        	waitAction(200);
        	Return = "test2";
        }
        
	}

	//----------------------------------------------------------------------
	private static void loginAction() {

		Return = dbConn.loginCheck(messageSplit[1], messageSplit[2]);
		
		if(fCom){ System.out.println(" socketServer: login("+messageSplit[1]+","+messageSplit[2]+"): " + Return); }
	}
	//----------------------------------------------------------------------
	private static void getPanelAction() {
		     if(Panel[0] == 0){ Panel[0]=1; Return = "1"; }
		else if(Panel[0] == 1 && Panel[1] == 0){ Panel[1]=1; Return = "2"; }
		else { Return = "0"; }
	}
	//----------------------------------------------------------------------
	private static void releasePanelAction(String x) {
		     if(x.equals("1")){ Panel[0]=0; }
		else if(x.equals("2")){ Panel[1]=0; }
	}
	//----------------------------------------------------------------------
	private static void if2PlayerAction() {
		int x = 0;
		if(Panel[0] == 1){ x++; }
		if(Panel[1] == 1){ x++; }
		Return = Integer.toString(x);
	}
	//----------------------------------------------------------------------
	private static void set2PlayerAction() {
		twoPlayer = true;
		// wyzerowanie wyników w Dices[]
		//for(i=0; i<12; i++){
		//	Dices[i] = 0;
		//}
	}
	//----------------------------------------------------------------------
	private static void unset2PlayerAction() {
		twoPlayer = false;
	}
	//----------------------------------------------------------------------
	private static void check2PlayerAction() {
		
	}
	//----------------------------------------------------------------------
	private static void saveSumAction() {
		i=0;
		     if(messageSplit[1].equals("1")){ i=0; }
		else if(messageSplit[1].equals("2")){ i=1; }
		Sum[i] = Integer.parseInt(messageSplit[2]);
	}
	//----------------------------------------------------------------------
	private static void getSumAction() {
		i=0;
	     	  if(messageSplit[1].equals("1")){ i=0; }
	     else if(messageSplit[1].equals("2")){ i=1; }
		Return = Integer.toString(Sum[i]);
	}
	//----------------------------------------------------------------------
	private static void loginGetInfoAction() {
		Return = dbConn.loginGetInfo(messageSplit[1]);
	}
	//----------------------------------------------------------------------
	private static void Generate6dicesTmpAction() {
		ResourceClass resource = new ResourceClass();
		// tymczasowe wygenerowanie Dice
		for(i=0; i<6; i++){
			dice[i] = new Dice(1,i,resource);
		}
		for(i=6; i<12; i++){
			dice[i] = new Dice(2,i,resource);
		}
		// wyzerowanie wyników w Dices[]
		for(i=0; i<12; i++){
			Dices[i] = 0;
		}
	}
	//----------------------------------------------------------------------
	// !!!W¥TKI!!!
	private static void Generate6dicesAction(String panel) {
		ResourceClass resource = new ResourceClass();
		
		if(panel.equals("1")){
			//countDicesPanel1 = 0;
			
			for(i=0; i<6; i++){
				dice[i] = new Dice(1,i,resource);
				fDice[i] = true;
				//System.out.println(" socketServer: new Dice[" + Integer.toString(i) + "]");
				//waitAction(50);
			}
			
			if(fJoin){
				for(i=0; i<6; i++){
					if(fDice[i]){
						try {
							dice[i].t.join();
							fDice[i] = false;
						} catch (InterruptedException e){
							if(fCom){ System.out.println(" socketServer: Generate6dicesAction() error: " + e.toString()); }
						}
					}
				}
			}
			
		}
		else if(panel.equals("2")){
			//countDicesPanel2 = 0;
			for(i=6; i<12; i++){
				dice[i] = new Dice(2,i,resource);
				fDice[i] = true;
				//waitAction(50);
			}
			
			if(fJoin){
				for(i=6; i<12; i++){
					if(fDice[i]){
						try {
							dice[i].t.join();
							fDice[i] = false;
						} catch (InterruptedException e){
							if(fCom){ System.out.println(" socketServer: Generate6dicesAction() error: " + e.toString()); }
						}
					}
				}
			}

		}

	}
	
	//----------------------------------------------------------------------
	//zrobienie Stringa zeby przeslac do klienta
	private static void Get6dicesAction(String panel){
		//'2:0:3|2:1:6|2:2:1|2:3:3|2:4:3|2:5:1'
		if(panel.equals("1")){
				S1="";
			for(i=0; i<6; i++){
					if(i>0){ S1+= "|"; }
				S1+= "1:" + i + ":"+Integer.toString(Dices[i]);		//nr panelu|nr kostki|wyrzucona liczba
			}
		}
		else if(panel.equals("2")){
				S1="";
			for(i=6,j=0; i<12; i++,j++){
					if(j>0){ S1+= "|"; }
				S1+= "2:" + j + ":"+Integer.toString(Dices[i]);
			}
		}
		Return = S1;
	}
	//----------------------------------------------------------------------
	private static void Get12dicesAction(String panel){
		//'1:0:3|1:1:4|1:2:4|1:3:5|1:4:5|1:5:5|2:0:3|2:1:4|2:2:5|2:3:2|2:4:5|2:5:3'
			S1="";
		for(i=0; i<6; i++){
			if(i>0){ S1+= "|"; }
			S1+= "1:" + i + ":"+Integer.toString(Dices[i]);
		}
			S1+= "|";
		for(i=6,j=0; i<12; i++,j++){
			if(j>0){ S1+= "|"; }
			S1+= "2:" + j + ":"+Integer.toString(Dices[i]);
		}
		Return = S1;
	}
	//----------------------------------------------------------------------
	private static void waitAction(int delayTime) {
		
    	try {
			Thread.sleep(delayTime);
		} catch (InterruptedException e) {
			System.out.print("waitAction() thread error: " + e.toString());
		}
	}
	
	//----------------------------------------------------------------------
	private static void testAction() {
    	i=100;
    	try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			//e.printStackTrace();
        	if(fCom){ System.out.println(" socketServer error Thread.sleep: " + e.toString()); }
		}
    	
    	Return = "test_delay_milisec_" + Integer.toString(i);
		if(fCom){ System.out.println(" socketServer: test(): " + Return); }
	}
	
	//----------------------------------------------------------------------
	public static void main(String[] args) {
		
		Panel = new int[2]; Panel[0]=0; Panel[1]=0;
		Sum = new int[2];   Sum[0]=0;   Sum[1]=0;
		twoPlayer = false;
		messageSplit = new String[10];  for(i=0; i<10; i++){ messageSplit[i] = ""; }
		i=0; j=0;
		
		//diceMax = 12;
		dice = new Dice[12];	//tablica obiektow Dice-watki
		Dices = new int[12];	//tablica wyników losowania
		fDice = new boolean[12];//flaga czy obiekt dzialajacy
		for(i=0; i<12; i++){ Dices[i]=0; fDice[i]=false; }
		
		//countDicesPanel1=0; countDicesPanel2=0;		
		message=""; Return=""; S=""; S1="";
		
		//nie uruchamiaj glownej petli serwera
		fRun = true;
		
		// pokazuj komunikaty serwera (show Comments)
		fCom = true;
		
		// uruchom join dla watkow (czekaj na zakonczenie watku)
		fJoin = true;
		
		
		if(fCom){ System.out.println(" socketServer myYahtzee: comments on"); }
		else    { System.out.println(" socketServer myYahtzee: comments off"); }
		
		// connect do mysql
		dbConn = new DBconnect("localhost","yahtzee","yahtzee","1234");
		S = dbConn.Connect();
		if(S.startsWith("DB connect")){
			if(fCom){ System.out.println(" socketServer: " + S); }
		}
		else {
			if(fCom){ System.out.println(" socketServer: connect to DB error: " + S); }
			return;
		}
		
		try {
			server = new ServerSocket(port);
			if(fCom){ System.out.println(" socketServer: start listening on localhost, port " + port); }
			
			if(fRun) {
		        while(true) {
		            //System.out.println(" socketServer: waiting for the client request");
		            
		            //creating socket and waiting for client connection
		            Socket socket = server.accept();
		            
		            // InputStream - read from socket
		            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		            //convert ObjectInputStream object to String
					try {
						//message = (String) ois.readObject();
						message = ois.readObject().toString();
						
					} catch (ClassNotFoundException e) {
						S = e.toString();  //e.printStackTrace();
						if(fCom){ System.out.println(" socketServer error: " + S); }
					}
					
					if(fCom){ System.out.println(" socketServer: received '" + message + "'"); }

					
					
		            // odczyt i analiza message  wynik zapisywany do Return
		            serverAction();
		            
		            
		            
		            // OutputStream
		            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		            
		            oos.writeObject(Return);
		            if(fCom && !Return.isEmpty()){ System.out.println(" socketServer: sent '" + Return + "'"); }
	
		            //close resources
		            ois.close();
		            oos.close();
		            socket.close();
		            
		            //terminate the server if client sends exit request
		            if(message.equalsIgnoreCase("exit") || message.equalsIgnoreCase("quit")) {
		            	break;
		            }
		        } //while
		        
			} else { //fRun
				i=4;
				if(fCom){ System.out.println(" socketServer: test by "+i+" sec ..."); }
		    	try {
					Thread.sleep(i*1000);
				} catch (InterruptedException e) { e.printStackTrace(); }
				
			}
			
			// close connect do mysql
	        S = dbConn.Disconnect();
			if(S.startsWith("DB disconnect")){
				if(fCom){ System.out.println(" socketServer: " + S); }
			}
			else {
				if(fCom){ System.out.println(" socketServer: disconnect to DB error: " + S); }
				return;
			}
			
	        //close the ServerSocket object
	        server.close();
	        if(fCom){ System.out.println(" socketServer: shut down !"); }
			
		} catch (IOException e) {
			S = e.toString();  //e.printStackTrace();
			if(fCom){ System.out.println(" socketServer error: " + S); }
		} 
		
	} //main
//----------------------------------------------------------------------
}
//jak ktos zapisuje to nikt nie moze odczytac w tym samym momencie i na odwrot
class ResourceClass {
	private ReentrantLock lock = new ReentrantLock();
	private String ss=""; //wspolny zasob
	public void write(String s){
		lock.lock();
		
		try{
			ss+=s;		//przed zapisem blokujemy
		}
		finally{lock.unlock();}
	}

	public String read(){
		lock.lock();
		
		try{
			return ss;		//przed odczytem blokujemy
		}
		finally{lock.unlock();}
	}
};



