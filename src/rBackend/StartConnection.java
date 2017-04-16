package rBackend;

import java.io.IOException;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class StartConnection {
	private RConnection c;
	
	public StartConnection(RConnection c) {
		this.c = c;
	}
	
	public void startRConnection() {
		try {
			Process pro = null;
			ProcessBuilder pb = new ProcessBuilder("RScript","assets/InitRserve.txt");
			
			pro = pb.start();
			pro.waitFor();
			pro.destroy();
		}
		catch(IOException io) {
			System.out.println("IOException starting Rserve");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interrupted starting Rserve");
		}
		while(c == null) {
			try {
				c = new RConnection("localhost");
				
				System.out.println("Connected to Rserve");
			} catch (RserveException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Ui Rserve Exception");
			} 
		}
	}
}
