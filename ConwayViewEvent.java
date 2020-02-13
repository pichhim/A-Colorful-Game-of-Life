package a8;

abstract public class ConwayViewEvent {
	public boolean isNextEvent() {
		return false;
	}

	public boolean isTorusEvent() {
		return false;
	}

	public boolean isStartStopEvent() {
		return false;
	}

	public boolean isLoBirthEvent() {
		return false;
	}

	public boolean isHiBirthEvent() {
		return false;
	}

	public boolean isLoSurviveEvent() {
		return false;
	}

	public boolean isHiSurviveEvent() {
		return false;
	}

	public boolean isDelayEvent() {
		return false;
	}
}

class NextEvent extends ConwayViewEvent {
	public boolean isNextEvent() {
		return true;
	}
}

class TorusEvent extends ConwayViewEvent {
	public boolean isTorusEvent() {
		return true;
	}
}

class StartStopEvent extends ConwayViewEvent {
	private String text;
	
	public StartStopEvent(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public boolean isStartStopEvent() {
		return true;
	}
}

class LoBirthEvent extends ConwayViewEvent {
	private int value;

	public LoBirthEvent(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public boolean isLoBirthEvent() {
		return true;
	}
}

class HiBirthEvent extends ConwayViewEvent {
	private int value;

	public HiBirthEvent(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public boolean isHiBirthEvent() {
		return true;
	}
}

class LoSurviveEvent extends ConwayViewEvent {
	private int value;

	public LoSurviveEvent(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public boolean isLoSurviveEvent() {
		return true;
	}
}

class HiSurviveEvent extends ConwayViewEvent {
	private int value;

	public HiSurviveEvent(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public boolean isHiSurviveEvent() {
		return true;
	}
}

class DelayEvent extends ConwayViewEvent {
	private int value;

	public DelayEvent(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public boolean isDelayEvent() {
		return true;
	}
}