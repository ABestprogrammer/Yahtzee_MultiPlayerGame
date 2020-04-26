package socket;

import java.util.Random;


//----------------------------------------------------------------------
public class DiceGenerate {
	int i;
	//Random rand;
	
	//----------------------------------------------------------------------	
	public void generate (int panel, int d) {
		
		try {
			//przytrzymuje watek zeby sie dobrze sie losowaly - random
			Thread.sleep(new Random().nextInt(100));
			//Thread.sleep(20);
			
			//generowanie liczby z zakresu 1 - 6
			i = new Random().nextInt(6) + 1;
			
			//rand = new Random();
			//i = rand.nextInt(6) + 1;
			
			//ustawia wartosc wylosowanej kostki
			socketServer.Dices[d] = i;
		}
		catch (InterruptedException e) {
			System.out.print("DiceGenerate thread error: " + e.toString());
		}
		
	}
}
//----------------------------------------------------------------------
