package a8;

public class ConwayController implements ConwayObserver, ConwayViewListener {

	private ConwayModel model;
	private ConwayView view;
	private StartThread thread;

	public ConwayController(ConwayModel model, ConwayView view) {
		this.model = model;
		this.view = view;

		view.addConwayViewListener(this);
		model.addObserver(this);
	}

	@Override
	public void handleConwayViewEvent(ConwayViewEvent e) {
		// Button Click for: Torus, Next, Start/Stop
		if (e.isNextEvent()) {
			// Find which spots to add or remove; temp array stores changes
			next();
			view.setMessage("We went onwards to the next stage!");
		}
		if (e.isTorusEvent()) {
			model.setTorus(!model.getTorus());
			String set = model.getTorus() ? "Torus Mode Activated." : "Torus Mode Disabled.";
			view.setMessage(set);
		}
		if (e.isStartStopEvent()) {
			// Implement the new thread
			model.setStart(!model.getStart());
			String set = model.getStart() ? "The Game of Life Simulation has begun."
					: "The Game of Life Simulation has come to a pause.";
			view.setMessage(set);
			if (model.getStart()) {
				thread = new StartThread(this);
				thread.start();
			} else if (!model.getStart() || blankBoard()) {
				thread.done();
				try {
					thread.join();
				} catch (InterruptedException ex) {

				}
			}
		}

		// JSliders for: Birth high, Birth low, Death high, Death low, Updates delay
		if (e.isHiBirthEvent()) {
			HiBirthEvent hiBirth = (HiBirthEvent) e;
			model.setHighBirth(hiBirth.getValue());
			view.setMessage("High birth threshold: " + model.getHighBirth());
		}
		if (e.isLoBirthEvent()) {
			LoBirthEvent loBirth = (LoBirthEvent) e;
			model.setLowBirth(loBirth.getValue());
			view.setMessage("Low birth threshold: " + model.getLowBirth());
		}
		if (e.isLoSurviveEvent()) {
			LoSurviveEvent loSurvive = (LoSurviveEvent) e;
			model.setLowSurvive(loSurvive.getValue());
			view.setMessage("Low survive threshold: " + model.getLowSurvive());
		}
		if (e.isHiSurviveEvent()) {
			HiSurviveEvent hiSurvive = (HiSurviveEvent) e;
			model.setHighSurvive(hiSurvive.getValue());
			view.setMessage("High survive threshold: " + model.getHighSurvive());
		}
		if (e.isDelayEvent()) {
			DelayEvent delay = (DelayEvent) e;
			model.setDelay(delay.getValue());
			view.setMessage("Update delay is now: " + model.getDelay() + " ms.");
		}

	}

	@Override
	public void update(ConwayModel model) {
	}

	public ConwayModel getModel() {
		return model;
	}

	// HELPER METHODS

	/* Implements next button click */
	void next() {
		view.getBoard().removeAll();
		view.getBoard().revalidate();
		view.getBoard().repaint();

		boolean[][] temp = new boolean[view.getFilled().length][view.getFilled().length];
		for (int i = 0; i < view.getFilled().length; i++) {
			for (int j = 0; j < view.getFilled().length; j++) {
				temp[i][j] = view.getFilled()[i][j];
			}
		}

		for (int x = 0; x < view.getFilled().length; x++) {
			for (int y = 0; y < view.getFilled().length; y++) {
				if (view.getFilled()[x][y] == false) {
					// Sets dead cell to be alive
					if (neighborAmt(x, y) >= model.getLowBirth() && neighborAmt(x, y) <= model.getHighBirth()) {
						temp[x][y] = true;
					}
				} else {
					// Sets alive cell to be dead
					if (neighborAmt(x, y) < model.getLowSurvive() || neighborAmt(x, y) > model.getHighSurvive()) {
						temp[x][y] = false;
					}
				}
			}
		}

		view.setFilled(temp.clone());
		view.updateBoard(); // Update based on the new filled array
		view.getBoard().revalidate();
		view.getBoard().repaint();
	}

	private boolean inBounds(int x, int y) {
		if (x >= 0 && x < view.getFilled().length && y >= 0 && y < view.getFilled().length) {
			return true;
		}
		return false;
	}

	private boolean singleInBounds(int a) {
		if (a >= 0 && a < view.getFilled().length) {
			return true;
		}
		return false;
	}

	private int neighborAmt(int arrx, int arry) {
		int neighbors = 0;
		for (int x = arrx - 1; x <= arrx + 1; x++) {
			for (int y = arry - 1; y <= arry + 1; y++) {
				if (inBounds(x, y)) {
					if (view.getFilled()[x][y]) {
						if (x != arrx || y != arry) {
							neighbors += 1;
						}
					}
				} else {
					// If torus mode is on, implement it
					if (model.getTorus()) {
						int tempx = 0;
						int tempy = 0;
						// Corner-to-corner torus
						if (!singleInBounds(x) && !singleInBounds(y)) {
							if (x == -1) {
								if (y == -1) { // Top left
									tempx = view.getFilled().length - 1;
									tempy = view.getFilled().length - 1;
								} else if (y == view.getFilled().length) { // Bottom left
									tempx = view.getFilled().length - 1;
									tempy = 0;
								}
							} else if (x == view.getFilled().length) { // Top right
								if (y == -1) {
									tempx = 0;
									tempy = view.getFilled().length - 1;
								} else if (y == view.getFilled().length) { // Bottom right
									tempx = 0;
									tempy = 0;
								}
							}
							if (view.getFilled()[tempx][tempy]) { // Add neighbor
								if (tempx != arrx || tempy != arry) {
									neighbors += 1;
								}
							}
						}
						// Side to side torus
						if (!singleInBounds(x) && singleInBounds(y)) {
							if (x == -1) {
								tempx = view.getFilled().length - 1;
								tempy = y;
							} else if (x == view.getFilled().length) {
								tempx = 0;
								tempy = y;
							}
							if (view.getFilled()[tempx][tempy]) { // Add neighbor
								if (tempx != arrx || tempy != arry) {
									neighbors += 1;
								}
							}
						}
						// Top to bottom torus
						if (!singleInBounds(y) && singleInBounds(x)) {
							if (y == -1) {
								tempx = x;
								tempy = view.getFilled().length - 1;
							} else if (y == view.getFilled().length) {
								tempx = x;
								tempy = 0;
							}
							if (view.getFilled()[tempx][tempy]) { // Add neighbor
								if (tempx != arrx || tempy != arry) {
									neighbors += 1;
								}
							}
						}
					}
				}
			}
		}
		return neighbors;
	}

	/*
	 * Determines if board is empty. Used with View listener -> if empty, stops the
	 * Start button
	 */
	private boolean blankBoard() {
		for (int i = 0; i < view.getFilled().length; i++) {
			for (int j = 0; j < view.getFilled().length; j++) {
				if (view.getFilled()[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
}