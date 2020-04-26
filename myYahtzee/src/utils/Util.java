package utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Util {

	public boolean fC;		//flaga do comments w kliencie
	public String err;
	private String S;

	//----------------------------------------------------------------------
	public Util () {
		fC = false; //show communicates
		err = "";
		S="";
	}

	//----------------------------------------------------------------------
	
	
	
	//----------------------------------------------------------------------
//	window.alert("Hello! I am an alert box!");
//
//	public void showAlert() { 
//    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//    alert.setTitle("Message Here...");
//    alert.setHeaderText("Look, an Information Dialog");
//    alert.setContentText("I have a great message for you!");
//    alert.showAndWait();
//	}

	//----------------------------------------------------------------------
	public void showAlert(String type, String headerText, String contentText) {
		Platform.runLater(new Runnable() {
			public void run() {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				//INFORMATION, CONFIRMATION, WARNING, ERROR, NONE
				     if(type.equals("none")){ alert.setAlertType(Alert.AlertType.NONE); }
				else if(type.equals("info")){  }
			    else if(type.equals("err")) { alert.setAlertType(Alert.AlertType.ERROR); }
				else if(type.equals("warn")){ alert.setAlertType(Alert.AlertType.WARNING); }
				else if(type.equals("conf")){ alert.setAlertType(Alert.AlertType.CONFIRMATION); }
			
			    alert.setTitle("myYahtzee");
				alert.setHeaderText(headerText);
				alert.setContentText(contentText);
				alert.showAndWait();
			
			}
		});
	}

	//----------------------------------------------------------------------
	public void pause(int ms) {
		//int sec = ms * 1000;
	    try {
	        Thread.sleep(ms);
	    } catch (InterruptedException ex) {
	        System.err.format("IOException: %s%n", ex);
	    }
	}
	
	//----------------------------------------------------------------------
	public void Test() {
		S = getClass().getName();
		System.out.println("I'm object " + S);
	}
	//----------------------------------------------------------------------	
}
