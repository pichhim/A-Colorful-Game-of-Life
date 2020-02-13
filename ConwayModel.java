package a8;

import java.util.ArrayList;
import java.util.List;

public class ConwayModel {
	private List<ConwayObserver> observers;

	private int delay;
	private boolean torus;
	private boolean start;
	
	// Thresholds
	private int lowBirth;
	private int highBirth;
	private int lowSurvive;
	private int highSurvive;

	public ConwayModel() {
		observers = new ArrayList<ConwayObserver>();

		delay = 1000;
		torus = false;
		start = false;
		
		lowBirth = 3;
		highBirth = 3;
		lowSurvive = 2;
		highSurvive = 3;
	}

	public int getLowBirth() {
		return lowBirth;
	}
	
	public void setLowBirth(int b) {
		lowBirth = b;
	}

	public int getHighBirth() {
		return highBirth;
	}
	
	public void setHighBirth(int b) {
		highBirth = b;
	}

	public int getLowSurvive() {
		return lowSurvive;
	}
	
	public void setLowSurvive(int b) {
		lowSurvive = b;
	}

	public int getHighSurvive() {
		return highSurvive;
	}
	
	public void setHighSurvive(int b) {
		highSurvive = b;
	}

	public int getDelay() {
		return delay;
	}
	
	public void setDelay(int b) {
		delay = b;
	}

	public boolean getTorus() {
		return torus;
	}

	public void setTorus(boolean b) {
		torus = b;
	}
	
	public boolean getStart() {
		return start;
	}
	
	public void setStart(boolean b) {
		start = b;
	}
	
	public void reset() {
		lowBirth = 3;
		highBirth = 3;
		lowSurvive = 2;
		highSurvive = 3;

		delay = 10;
		torus = false;
	}
	
	public void change() {
		notifyObservers();
	}
	
	public void addObserver(ConwayObserver o) {
		observers.add(o);
	}
	
	public void removeObserver(ConwayObserver o) {
		observers.remove(o);
	}
	
	private void notifyObservers() {
		for (ConwayObserver o : observers) {
			o.update(this);
		}
	}
	
}
