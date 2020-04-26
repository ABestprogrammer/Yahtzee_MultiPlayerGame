package app;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.application.Platform;

public class myYahtzeeController {
	
	Random rand;
	private int panel;
	private int DiceNr;
	private String tab[];
	private String[] messageSplit;
	private int sum[];
	
	
	private String S,S1;
	private static String S2;
	private int i,j;
	private int Label_panel,Label_nr;
	private String Label_dice;
	
	private static boolean twoPlayer;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label LabInfo;
    @FXML
    private Pane Pane1;
    @FXML
    private Pane Pane2;
    @FXML
    private Pane PaneOptions;
    @FXML
    private Label LabUser1;
    @FXML
    private Label LabUser1b;
    @FXML
    private Label d10;
    @FXML
    private Label d11;
    @FXML
    private Label d12;
    @FXML
    private Label d13;
    @FXML
    private Label d14;
    @FXML
    private Label d15;
    @FXML
    private Button BtnPlay1;
    @FXML
    private Label LabUser2;
    @FXML
    private Label LabUser2b;
    @FXML
    private Label d20;
    @FXML
    private Label d21;
    @FXML
    private Label d22;
    @FXML
    private Label d23;
    @FXML
    private Label d24;
    @FXML
    private Label d25;
    @FXML
    private Button BtnPlay2;
    @FXML
    private Label LabelSum1;
    @FXML
    private Label LabelSum2;
    @FXML
    private Hyperlink Link_hide_options;
    @FXML
    private Hyperlink Link_show_options;
    @FXML
    private Hyperlink LinkNewGame;
    @FXML
    private Button BtnExit;
    @FXML
    private CheckBox Checkbox_com;
    @FXML
    private CheckBox Checkbox_join;
    
    //----------------------------------------------------------------------
    void runAction(String ac) {
    	if(ac.equals("dices_zero")) {
    		if(panel == 1){
    			d10.setText("0"); d11.setText("0"); d12.setText("0"); d13.setText("0"); d14.setText("0"); d15.setText("0");
    		}
    		else if(panel == 2){
    			d20.setText("0"); d21.setText("0"); d22.setText("0"); d23.setText("0"); d24.setText("0"); d25.setText("0");
    		}
    	}
    	else if(ac.equals("dices_plain")) {
    		if(panel == 1){
    			d10.setText(""); d11.setText(""); d12.setText(""); d13.setText(""); d14.setText(""); d15.setText("");
    		}
    		else if(panel == 2){
    			d20.setText(""); d21.setText(""); d22.setText(""); d23.setText(""); d24.setText(""); d25.setText("");
    		}
    		else if(ac.equals("dices_plain_all")) {
    			d10.setText(""); d11.setText(""); d12.setText(""); d13.setText(""); d14.setText(""); d15.setText("");
    			d20.setText(""); d21.setText(""); d22.setText(""); d23.setText(""); d24.setText(""); d25.setText("");
    		}
    	}
    }
    //----------------------------------------------------------------------
    //nie uzywane
    void runDelayAction(String act, int delay) {
    	// https://stackoverflow.com/questions/9165251/execute-task-in-background-in-javafx
    	// JavaFX has Event Dispatch Thread which it uses for UI events.
    	// All work with UI should happen on this thread.
    	// And non-UI calculations shouldn't happen there to avoid lags in UI.
        // separate non-FX thread
    	new Thread() {
    		@Override
            public void run() {
            	
            	try { Thread.sleep(delay); }
                catch (InterruptedException e) { e.printStackTrace(); }
            	
            	Platform.runLater( new Runnable() {
            		@Override
            		public void run() {
            				 if(act.equals("BtnPlay1_disable")){ BtnPlay1.setDisable(true); }
            			else if(act.equals("BtnPlay1_enable")) { BtnPlay1.setDisable(false); }
            			else if(act.equals("x")){  }
            			//System.out.println("thread FX stop");
            		}
            	});
            }
        }.start();
    }

    //----------------------------------------------------------------------
    //do wyswietlania labeli kostek
    void BtnPlay_delay_action4(int Label_panel,int Label_nr,String Label_dice) {
    	
    	new Thread() {
    		@Override
            public void run() {
            	
            	try {
            		//Thread.sleep(200);
            		Thread.sleep(new Random().nextInt(200));
            	}
                catch (InterruptedException e){
                	System.out.println("BtnPlay_delay_action4() error: " + e.toString());
                }
            	
            	Platform.runLater( new Runnable() {
            		@Override
            		public void run() {
            	    	if(Label_panel == 1){
        	    			        if(Label_nr == 0){ d10.setText(Label_dice); }
        	    			   else if(Label_nr == 1){ d11.setText(Label_dice); }
        	    			   else if(Label_nr == 2){ d12.setText(Label_dice); }
        	    			   else if(Label_nr == 3){ d13.setText(Label_dice); }
        	    			   else if(Label_nr == 4){ d14.setText(Label_dice); }
        	    			   else if(Label_nr == 5){ d15.setText(Label_dice); }
            	    	}
            	    	else if(Label_panel == 2){
            	    				if(Label_nr == 0){ d20.setText(Label_dice); }
           		               else if(Label_nr == 1){ d21.setText(Label_dice); }
           		               else if(Label_nr == 2){ d22.setText(Label_dice); }
           		               else if(Label_nr == 3){ d23.setText(Label_dice); }
           		               else if(Label_nr == 4){ d24.setText(Label_dice); }
           		               else if(Label_nr == 5){ d25.setText(Label_dice); }
            	    	}
            		}
            	});
            }
        }.start();
    }
    //----------------------------------------------------------------------
    //nie uzywana
//    void delay_action() {
//       	new Thread() {
//    		@Override
//            public void run() {
//    			
//            	Platform.runLater( new Runnable() {
//            		@Override
//            		public void run() {
//            			for(int m=0; m<40; m++){
//                        	try {
//                        		Thread.sleep(500);
//                        		//Thread.sleep(new Random().nextInt(200));
//                        	}
//                            catch (InterruptedException e){
//                            	System.out.println("delay_action() error: " + e.toString());
//                            }
//                        	
//            				System.out.println("delay_action() " + Integer.toString(m));
//            				//S = App.socketClient.send("check2Player");
//            			}
//            		}
//            	});
//            }
//        }.start();	
//
//    }
    //----------------------------------------------------------------------
    //nie uzywana
    void BtnPlay_action4() {
    	if(Label_panel == 1){
    		     if(Label_nr == 0){ d10.setText(Label_dice); }
    		else if(Label_nr == 1){ d11.setText(Label_dice); }
    		else if(Label_nr == 2){ d12.setText(Label_dice); }
    		else if(Label_nr == 3){ d13.setText(Label_dice); }
    		else if(Label_nr == 4){ d14.setText(Label_dice); }
    		else if(Label_nr == 5){ d15.setText(Label_dice); }
    	}
    	else if(Label_panel == 2){
		          if(Label_nr == 0){ d20.setText(Label_dice); }
		     else if(Label_nr == 1){ d21.setText(Label_dice); }
		     else if(Label_nr == 2){ d22.setText(Label_dice); }
		     else if(Label_nr == 3){ d23.setText(Label_dice); }
		     else if(Label_nr == 4){ d24.setText(Label_dice); }
		     else if(Label_nr == 5){ d25.setText(Label_dice); }
    	}
    }
    //----------------------------------------------------------------------
    void BtnPlay_action3(String message) {
    	int i=0,j=0;
    	//panel: ktora kostka: wartosc kostki
    	//1:0:2
		for(i=0; i<10; i++){ tab[i] = ""; }
		
		String[] t = message.split(":");
			j = t.length; if(j>10){ j=10; }
		for(i=0; i<j; i++){ if(t[i] != null){tab[i] = t[i];} }
		
		if(!tab[0].equals("")){
			Label_panel = Integer.parseInt(tab[0]);
			Label_nr = Integer.parseInt(tab[1]);
			Label_dice = tab[2];
			
			     if(Label_panel == 1){ sum[0] += Integer.parseInt(Label_dice); }
			else if(Label_panel == 2){ sum[1] += Integer.parseInt(Label_dice); }
			
			//BtnPlay_action4();
			BtnPlay_delay_action4(Label_panel,Label_nr,Label_dice);
		}
		for(i=0; i<10; i++){ tab[i] = ""; }
		Label_panel=0; Label_nr=0; Label_dice=""; 
    }
    //----------------------------------------------------------------------
    //funkcja rozdziel po znaku |
    void BtnPlay_action2(String message) {
    	int i=0,j=0;
    	if (message.contains("|")){
    		for(i=0; i<16; i++){ messageSplit[i] = ""; }
    		
    		String[] t = message.split("\\|");
    			j = t.length; if(j>16){ j=16; }
    			//System.out.println("BtnPlay_action2: t.length=" + j);
    		for(i=0; i<j; i++){ if(t[i] != null){messageSplit[i] = t[i];} }
    		
    		for(i=0; i<16; i++){
    			if(!messageSplit[i].equals("")){
    				//System.out.println("BtnPlay_action2: " + messageSplit[i]);
    				BtnPlay_action3(messageSplit[i]);
    			}
    		}
    		for(i=0; i<10; i++){ messageSplit[i] = ""; }
    	}
    	else {
			//System.out.println("BtnPlay_action2_: " + message);
			BtnPlay_action3(message);
    	}
    }

    //----------------------------------------------------------------------
    //play dla 1. panelu
    @FXML
    void BtnPlay1_action(ActionEvent event) {
    	LabInfo.setText("");
    	runAction("dices_plain");
    	
    	sum[0] = 0;
    	
    	if(twoPlayer){ S = App.socketClient.send("generate6dicesAll|1"); }
    	else         { S = App.socketClient.send("generate6dices|1"); }
    	
    	BtnPlay_action2(S);
    	
    	if(twoPlayer){ 
    		LabelSum1.setText("suma: " + Integer.toString(sum[0]));
    		App.socketClient.send("saveSum|1|" + Integer.toString(sum[0]));
    		
    		S = App.socketClient.send("getSum|2");
    		sum[1] = Integer.parseInt(S);
    		LabelSum2.setText("suma: " + Integer.toString(sum[1]));
    		
    	}
    	else { LabelSum1.setText("suma: " + Integer.toString(sum[0])); }
    }

    //----------------------------------------------------------------------
    @FXML
    void BtnPlay2_action(ActionEvent event) {
    	LabInfo.setText("");
    	runAction("dices_plain");
    	
    	sum[1] = 0;
    	
    	if(twoPlayer){ S = App.socketClient.send("generate6dicesAll|2"); }
    	else         { S = App.socketClient.send("generate6dices|2"); }
    	
    	BtnPlay_action2(S);
    	
    	if(twoPlayer){
    		LabelSum2.setText("suma: " + Integer.toString(sum[1]));
    		App.socketClient.send("saveSum|2|" + Integer.toString(sum[1]));
    		
    		S = App.socketClient.send("getSum|1");
    		sum[0] = Integer.parseInt(S);
    		LabelSum1.setText("suma: " + Integer.toString(sum[0]));
    	}
    	else { LabelSum2.setText("suma: " + Integer.toString(sum[1])); }
    }
    //----------------------------------------------------------------------
    //do wspólnej gry + ew. Alert-tango
    @FXML
    void LinkNewGame_action(ActionEvent event) {
    	
    	if(!twoPlayer){
    		S = App.socketClient.send("if2Player");
    		j = Integer.parseInt(S);
    	
    		if(j == 1){
    			S = "Do tanga trzeba dwojga ;-) Jest tylko jeden gracz. Nie mo¿na rozpocz¹æ gry ale mo¿esz sobie losowaæ kostki na próbê.";
    			LabInfo.setText(S); LabInfo.setTextFill(Color.BLACK);
    			App.util.showAlert("info", "App.socketClient.send(if2Player)", S);
    		}
    		else if(j == 2){
    			App.socketClient.send("set2Player");
    			twoPlayer = true;
    			S = "Rozpoczynamy grê ...";
    			LabInfo.setText(S); LabInfo.setTextFill(Color.BLACK);
    			LinkNewGame.setText("koniec gry");
    		}
    		else {
    			LabInfo.setText("coœ tu nie gra?");
    			LabInfo.setTextFill(Color.RED);
    		}
    	}
    	else {
    		LinkNewGame.setText("nowa gra");
    		twoPlayer = false;
    		App.socketClient.send("unset2Player");
    	}
    }
    //----------------------------------------------------------------------
    @FXML
    void Link_show_options_action(ActionEvent event) {
    	PaneOptions.setVisible(true);
    	Link_show_options.setVisible(false);
    }
    //----------------------------------------------------------------------
    @FXML
    void Link_hide_options_action(ActionEvent event) {
    	PaneOptions.setVisible(false);
    	Link_show_options.setVisible(true);
    }
    //----------------------------------------------------------------------
    @FXML
    void Checkbox_com_action(ActionEvent event) {
    	if(Checkbox_com.isSelected()){ S = App.socketClient.send("comments|on"); }
    	else                         { S = App.socketClient.send("comments|off"); }
    }
    //----------------------------------------------------------------------
    @FXML
    void Checkbox_join_action(ActionEvent event) {
    	if(Checkbox_join.isSelected()){ S = App.socketClient.send("threadJoin|on"); }
    	else                          { S = App.socketClient.send("threadJoin|off"); }
    }
    //----------------------------------------------------------------------
    @FXML
    void BtnExit_action(ActionEvent event) {
		App.socketClient.send("unset2Player");
		twoPlayer = false;
    	S = App.socketClient.send("releasePanel|" + Integer.toString(panel));
    	App.setRoot("App.fxml");
    }

    
    //----------------------------------------------------------------------
    @FXML
    void initialize() {
    	panel = App.myPanel;
    	twoPlayer = false;
    	sum = new int[2]; sum[0]=0; sum[1]=0;
    	S=""; S1=""; S2=""; i=0; j=0;
    	tab = new String[10];           for(i=0; i<10; i++){ tab[i]=""; }
    	messageSplit = new String[16];  for(i=0; i<16; i++){ messageSplit[i] = ""; }
    	
    	PaneOptions.setVisible(false);
    	Link_show_options.setVisible(true);
    	Checkbox_com.setSelected(true);
    	Checkbox_join.setSelected(true);
    	
    	LabInfo.setText("Witam w grze myYahtzee, aby rozpocz¹æ kliknij 'nowa gra'. Teraz mo¿esz polosowaæ sobie kostki na próbê ...");
    	LabInfo.setTextFill(Color.BLACK);
    	//LabUser1.setText("user - brak");
    	//LabUser1b.setText("");
    	//LabUser2.setText("user - brak");
    	//LabUser2b.setText("");
    	LabelSum1.setText("");
    	LabelSum2.setText("");
    	
    	runAction("dices_plain_all");
    	//System.out.println(" initialize(): panel=" + Integer.toString(panel) + "");

    	
    	// imie|nazwisko|opis
    	S = App.socketClient.send("loginGetInfo|" + App.login);
    	
    	String[] t = S.split("\\|");
    	j = t.length; if(j>10){ j=10; }
    	for(i=0; i<j; i++){ if(t[i] != null){tab[i] = t[i];} }
    	
    	if(panel == 1) {
    		LabUser1.setText(tab[0]+" "+tab[1]);
    		LabUser1b.setText(tab[2]);
    		BtnPlay1.setDisable(false);
    		BtnPlay2.setDisable(true);
    	}
        else if(panel == 2) {
    		LabUser2.setText(tab[0]+" "+tab[1]);
    		LabUser2b.setText(tab[2]);
    		BtnPlay1.setDisable(true);
    		BtnPlay2.setDisable(false);
        }
    }
	//----------------------------------------------------------------------
    public void shutdown() {
    	System.out.println("STOP");
    }

    //----------------------------------------------------------------------
    //----------------------------------------------------------------------
    //----------------------------------------------------------------------
}
