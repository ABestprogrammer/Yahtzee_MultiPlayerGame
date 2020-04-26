package app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.scene.paint.Color;


public class AppController {

	private String S,S1,S2;
	
	@FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label LabInfo;
    @FXML
    private TextField LoginField;
    @FXML
    private PasswordField PassField;
    @FXML
    private Button OK_btn;
    @FXML
    private Button Quit_btn;
    @FXML
    private Hyperlink stop_server;
    @FXML
    private Hyperlink tmp;


	//----------------------------------------------------------------------
    @FXML
    void OK_btn_action(ActionEvent event) {
		S1 = LoginField.getText();
		S2 = PassField.getText();
		
		//S = App.dbConn.loginCheck(S1,S2);
		S = App.socketClient.send("login|"+S1+"|"+S2);
		
		if(S.equals("login_ok")) {

			App.login = S1;
			
			// ustawienie panelu App.myPanel
			S = App.socketClient.send("getPanel");
			     if(S.equals("1")){ App.myPanel = 1; }
			else if(S.equals("2")){ App.myPanel = 2; }
			else {
				S = "Brak ju¿ wolnego panelu do gry. Najpierw ktoœ musi siê wylogowaæ.";
				LabInfo.setText(S);
				LabInfo.setTextFill(Color.RED);
				
				App.util.showAlert("warn", "App.socketClient.send(getPanel)", S);
			}
			
			if(App.myPanel == 1 || App.myPanel == 2){
				App.setRoot("myYahtzee.fxml");
			}
			
		} else {
			LabInfo.setText(S + " - try again !");
			LabInfo.setTextFill(Color.RED);
		}
		
    }
    
    //----------------------------------------------------------------------
    @FXML
    void Quit_btn_action(ActionEvent event) {
    	Platform.exit();
    }

    //----------------------------------------------------------------------
    @FXML
    void stop_server_action(ActionEvent event) {
    	S = App.socketClient.send("exit");
    	if(S.equals("server_stop")) {
			LabInfo.setText("socketServer stopped !");
			LabInfo.setTextFill(Color.RED);
    	}
    }
	//----------------------------------------------------------------------
    @FXML
    void initialize() {
    	S=""; S1=""; S2="";
    	LabInfo.setText("Zaloguj siê jeœli jest wolny panel do gry.");
    	LabInfo.setTextFill(Color.BLACK);
    	
    	LoginField.setText("");
    	PassField.setText("");
    	
    }
  //----------------------------------------------------------------------
  //----------------------------------------------------------------------
  //----------------------------------------------------------------------
}
