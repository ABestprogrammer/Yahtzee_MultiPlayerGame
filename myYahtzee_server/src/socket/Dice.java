package socket;


//----------------------------------------------------------------------
public class Dice implements Runnable {
	public boolean fSync;
	int panel;
	int d;
	DiceGenerate diceGenerate;
	Thread t;
	ResourceClass resource;
	
	Dice(int panel, int d, ResourceClass resource) {
		this.panel = panel;
		this.d = d;
		this.resource=resource;	//referencja na wspolny zasob
				
		fSync = true;
		diceGenerate = new DiceGenerate();
    	t = new Thread(this);
    	t.start();
	}
	
	@Override
	public void run() {
		resource.write(String.valueOf(d));	//zapisuje nr kostki
		if(fSync) {
			synchronized(diceGenerate){
				diceGenerate.generate(panel,d);
			}
		} else {
			diceGenerate.generate(panel,d);
		}
		System.out.println(resource.read());
	}

}
