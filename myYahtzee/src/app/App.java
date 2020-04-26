package app;
	
import utils.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class App extends Application {

	public static Util util;
	public static SocketClient socketClient;
	
	private static Scene scene;
	//FXMLLoader fxmlLoader;

	public static int myPanel;	//panel 1 lub 2 do gry
	public static String login;

	
    //----------------------------------------------------------------------
	@Override
	public void start(Stage stage) throws IOException {
		try {
			scene = new Scene(loadFXML("App.fxml"));
			stage.setScene(scene);
			stage.show();
            if(util.fC){ String S = "change to " + "App.fxml"; System.out.println(S); }
            
		} catch(IOException e) {
			//e.printStackTrace();
			System.out.println("start() error: " + e.toString());
		}
	}

    //----------------------------------------------------------------------
	public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
            if(util.fC){ String S = "change to " + fxml; System.out.println(S); }
            
        } catch (IOException e) {
        	//e.printStackTrace();
            System.out.println("setRoot() error: " + e.toString());
        }
    }
    //----------------------------------------------------------------------
    private static Parent loadFXML(String fxml) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
    	//fxmlLoader.getController();
        return fxmlLoader.load();
    }
	
    //----------------------------------------------------------------------
	public static void main(String[] args) {
		
		util = new Util();
		util.fC = true;  //show communicates
		
		// (dbserver,db,user,pass, util)
		//dbConn = new DBconnect("localhost","yahtzee","yahtzee","1234", util);

		//utworzenie klienta do komumikacji z serwerem
		socketClient = new SocketClient(util);
		
		launch(args);
		
		//dbConn.Disconnect();
	}
    //----------------------------------------------------------------------
    //----------------------------------------------------------------------
    //----------------------------------------------------------------------
}
