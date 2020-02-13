package a8;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ConwayView extends JPanel implements ActionListener, MouseListener, ChangeListener {
	private JPanel board;
	private boolean[][] filled;

	private JPanel button_panel;
	private JLabel message;
	private int length = 10;
	private List<ConwayViewListener> listeners;

	// Color Scheme
	public static final Color a = new Color(255, 174, 188);
	public static final Color b = new Color(160, 231, 229);
	public static final Color c = new Color(180, 248, 200);
	public static final Color d = new Color(251, 231, 198);

	public ConwayView() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500, 606));

		board = new JPanel();
		board.setLayout(new GridLayout(length, length));
		board.setPreferredSize(new Dimension(500, 500));

		// Set up square board
		board.removeAll();
		board.revalidate();
		board.repaint();
		board.setBackground(Color.WHITE);

		// Reset filled
		filled = new boolean[length][length];
		for (int x = 0; x < length; x++) {
			for (int y = 0; y < length; y++) {
				filled[x][y] = false;
			}
		}
		for (int x = 0; x < length; x++) {
			for (int y = 0; y < length; y++) {
				if (filled[x][y]) {
					// Draw filled square
					double rand = Math.random();
					Color use = new Color(0, 0, 0);
					if (rand < .25) {
						use = a;
					} else if (rand < .50) {
						use = b;
					} else if (rand < .75) {
						use = c;
					} else {
						use = d;
					}

					JSquare element = new JSquare(500 / length, 500 / length, use, true);
					element.addMouseListener(this);
					board.add(element);
				} else {
					// Draw empty square
					JSquare element = new JSquare(500 / length, 500 / length,
							new Color(192, 192, 192), false);
					element.addMouseListener(this);
					board.add(element);
				}
			}
		}

		add(board, BorderLayout.CENTER);

		JPanel subpanel = new JPanel();
		subpanel.setLayout(new BorderLayout());

		// Message label
		message = new JLabel();
		message.setText("Welcome! Feel free to resize the window as you see fit. Click a square to readjust.");

		button_panel = new JPanel();
		button_panel.setLayout(new GridLayout(1, 5));

		// Settings buttons
		button_panel.add(new JButton("Clear all"));
		button_panel.add(new JButton("Random fill"));
		button_panel.add(new JButton("Torus"));
		button_panel.add(new JButton("Next"));
		button_panel.add(new JButton("Start/Stop"));

		// Slider settings
		JPanel slider_panel = new JPanel();
		slider_panel.setLayout(new GridLayout(4, 3));

		JSlider boardSize = new JSlider(10, 500, 10);
		boardSize.setName("Size");
		boardSize.addChangeListener(this);

		JSlider loBirth = new JSlider(0, 8, 3);
		loBirth.setName("loBirth");
		loBirth.addChangeListener(this);
		JSlider hiBirth = new JSlider(0, 8, 3);
		hiBirth.setName("hiBirth");
		hiBirth.addChangeListener(this);
		JSlider loSurvive = new JSlider(0, 8, 2);
		loSurvive.setName("loSurvive");
		loSurvive.addChangeListener(this);
		JSlider hiSurvive = new JSlider(0, 8, 3);
		hiSurvive.setName("hiSurvive");
		hiSurvive.addChangeListener(this);
		JSlider delay = new JSlider(10, 1000, 1000);
		delay.setName("delay");
		delay.addChangeListener(this);

		JLabel boardSizeLabel = new JLabel();
		boardSizeLabel.setText("Board size: ");
		JLabel loBirthLabel = new JLabel();
		loBirthLabel.setText("Birth low: ");
		JLabel hiBirthLabel = new JLabel();
		hiBirthLabel.setText("Birth high: ");
		JLabel loSurviveLabel = new JLabel();
		loSurviveLabel.setText("Survive low: ");
		JLabel hiSurviveLabel = new JLabel();
		hiSurviveLabel.setText("Survive high: ");
		JLabel delayLabel = new JLabel();
		delayLabel.setText("Update delay: ");

		// Sliders and their labels
		slider_panel.add(boardSizeLabel);
		slider_panel.add(loBirthLabel);
		slider_panel.add(hiBirthLabel);
		slider_panel.add(boardSize);
		slider_panel.add(loBirth);
		slider_panel.add(hiBirth);

		slider_panel.add(delayLabel);
		slider_panel.add(loSurviveLabel);
		slider_panel.add(hiSurviveLabel);
		slider_panel.add(delay);
		slider_panel.add(loSurvive);
		slider_panel.add(hiSurvive);

		subpanel.add(button_panel, BorderLayout.CENTER);
		subpanel.add(message, BorderLayout.SOUTH);

		add(slider_panel, BorderLayout.NORTH);
		add(subpanel, BorderLayout.SOUTH);

		for (Component c : button_panel.getComponents()) {
			JButton b = (JButton) c;
			b.setBackground(Color.WHITE);
			b.addActionListener(this);
		}
		for (Component c : slider_panel.getComponents()) {
			c.setBackground(Color.WHITE);
		}
		slider_panel.setBackground(Color.WHITE);
		subpanel.setBackground(Color.WHITE);

		listeners = new ArrayList<ConwayViewListener>();
	}

	public void setMessage(String s) {
		message.setText(s);
	}

	public JPanel getBoard() {
		return board;
	}

	public boolean[][] getFilled() {
		return filled;
	}

	public void setFilledAt(int x, int y, boolean b) {
		filled[x][y] = b;
	}

	public void setFilled(boolean[][] b) {
		filled = b;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JPanel square = (JPanel) e.getComponent();

		int xVal = square.getY() / (this.board.getHeight() / length); // X and Y reversed somehow, idk
		int yVal = square.getX() / (this.board.getWidth() / length);

		if (xVal < 0 || yVal < 0 || xVal >= length || yVal >= length) {
			return;
		}

		filled[xVal][yVal] = !filled[xVal][yVal];

		board.removeAll();
		board.revalidate();
		board.repaint();

		updateBoard();
		// Divided by 500/length to get actual coordinate
		// Remember coordinate starts at 0, so ex. board size 10 will go 0-9
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			if (source.getName() == "Size") {
				length = source.getValue();
				board.removeAll();
				board.setLayout(new GridLayout(length, length));
				add(board, BorderLayout.CENTER);
				board.revalidate();
				board.repaint();
				resetBoard();
				setMessage("The board size is now: " + length + "x" + length);
			}

			dispatchEventByText(source.getName(), source.getValue());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.getText().equals("Clear all")) {
			resetBoard();
		}
		if (button.getText().equals("Random fill")) {
			board.removeAll();
			board.revalidate();
			board.repaint();

			filled = new boolean[length][length];
			for (int x = 0; x < length; x++) {
				for (int y = 0; y < length; y++) {
					filled[x][y] = false;
				}
			}

			for (int x = 0; x < length; x++) {
				for (int y = 0; y < length; y++) {
					double temp = Math.random();
					if (temp < 0.15) {
						// Draw filled square
						double rand = Math.random();
						Color use = new Color(0, 0, 0);
						if (rand < .25) {
							use = a;
						} else if (rand < .50) {
							use = b;
						} else if (rand < .75) {
							use = c;
						} else {
							use = d;
						}

						filled[x][y] = true;
						JSquare element = new JSquare(this.getWidth() / length, this.board.getHeight() / length, use, true);
						element.addMouseListener(this);
						board.add(element);
					} else {
						// Draw empty square
						filled[x][y] = false;
						JSquare element = new JSquare(this.getWidth() / length, this.board.getHeight() / length,
								new Color(192, 192, 192), false);
						element.addMouseListener(this);
						board.add(element);
					}
				}
			}
		}

		dispatchEventByText(button.getText(), 0);
	}

	private void dispatchEventByText(String s, int i) {
		switch (s) {
		case "Next":
			fireEvent(new NextEvent());
			break;
		case "Torus":
			fireEvent(new TorusEvent());
			break;
		case "Start/Stop":
			fireEvent(new StartStopEvent(s));
			break;
		case "loBirth":
			fireEvent(new LoBirthEvent(i));
			break;
		case "hiBirth":
			fireEvent(new HiBirthEvent(i));
			break;
		case "loSurvive":
			fireEvent(new LoSurviveEvent(i));
			break;
		case "hiSurvive":
			fireEvent(new HiSurviveEvent(i));
			break;
		case "delay":
			fireEvent(new DelayEvent(i));
			break;
		}
	}

	public void addConwayViewListener(ConwayViewListener l) {
		listeners.add(l);
	}

	public void removeConwayViewListener(ConwayViewListener l) {
		listeners.remove(l);
	}

	public void fireEvent(ConwayViewEvent e) {
		for (ConwayViewListener l : listeners) {
			l.handleConwayViewEvent(e);
		}
	}

	// HELPER METHODS GALORE HELPER METHODS GALORE HELPER METHODS GALORE

	/* Updates empty board based on filled */
	void updateBoard() {
		for (int x = 0; x < length; x++) {
			for (int y = 0; y < length; y++) {
				if (filled[x][y]) {
					// Draw filled square
					double rand = Math.random();
					Color use = new Color(0, 0, 0);
					if (rand < .25) {
						use = a;
					} else if (rand < .50) {
						use = b;
					} else if (rand < .75) {
						use = c;
					} else {
						use = d;
					}
					JSquare element = new JSquare(this.getWidth() / length, this.board.getHeight() / length, use, true);
					element.addMouseListener(this);
					board.add(element);
				} else {
					// Draw empty square
					JSquare element = new JSquare(this.getWidth() / length, this.board.getHeight() / length,
							new Color(192, 192, 192), false);
					element.addMouseListener(this);
					board.add(element);
				}
			}
		}
	}

	/* Reset board to empty appearance */
	private void resetBoard() {
		board.removeAll();
		board.revalidate();
		board.repaint();
		board.setBackground(Color.WHITE);

		// Reset filled
		filled = new boolean[length][length];
		for (int x = 0; x < length; x++) {
			for (int y = 0; y < length; y++) {
				filled[x][y] = false;
			}
		}

		updateBoard();
	}

}
