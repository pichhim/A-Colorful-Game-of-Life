package a8;

import javax.swing.SwingUtilities;

public class StartThread extends Thread {
	private boolean stop;
	private ConwayController c;
	
	public StartThread(ConwayController c) {
		stop = false;
		this.c = c;
	}
	
	public void done() {
		stop = true;
	}
	
	public boolean getDone() {
		return stop;
	}
	
	public void run() {		
		while (!stop) {
			try {
				Thread.sleep(c.getModel().getDelay());
			} catch (InterruptedException e) {
			}
			SwingUtilities.invokeLater(new RunnerHelper(c));
		}
	}

}

class RunnerHelper implements Runnable {
	private ConwayController c;
	
	public RunnerHelper(ConwayController c) {
		this.c = c;
	}

	@Override
	public void run() {
		c.next();
	}

}