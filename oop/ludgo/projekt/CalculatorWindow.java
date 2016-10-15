package oop.ludgo.projekt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import oop.ludgo.projekt.Calculator.CalcMode;
import oop.ludgo.projekt.util.Constants;
import oop.ludgo.projekt.util.InputParser;
import oop.ludgo.projekt.util.InvalidInputException;
import oop.ludgo.projekt.util.Utilities;

/**
 * A class to provide a GUI window
 */
public class CalculatorWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private Calculator mCalculator;
	private MyCustomizedPanel panel;
	
	public CalculatorWindow() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1300, 700);
		
		panel = new MyCustomizedPanel();
		add(panel);
		
		setTitle(Constants.TITLE_WINDOW);
		setVisible(true);

		// Different action buttons for the first and second pillar
		panel.getmButtonCalcFirst().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				panel.getOutputArea().setText("");

				doCalculation(CalcMode.First);
			}
		});
		panel.getmButtonCalcSecond().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				panel.getOutputArea().setText("");

				if (panel.getButtonAAAbond().isSelected()) {
					doCalculation(CalcMode.SecondAAAbond);
				} else if (panel.getButtonAAAshare().isSelected()) {
					doCalculation(CalcMode.SecondAAAshare);
				} else if (panel.getButtonAAAindex().isSelected()) {
					doCalculation(CalcMode.SecondAAAindex);
				} else if (panel.getButtonBBBbond().isSelected()) {
					doCalculation(CalcMode.SecondBBBbond);
				} else if (panel.getButtonBBBshare().isSelected()) {
					doCalculation(CalcMode.SecondBBBshare);
				} else if (panel.getButtonCCCbond().isSelected()) {
					doCalculation(CalcMode.SecondCCCbond);
				} else if (panel.getButtonCCCindex().isSelected()) {
					doCalculation(CalcMode.SecondCCCindex);
				}
			}
		});
	}

	/**
	 * Pass input data from the view to the controller
	 * 
	 * @param mode
	 *            {@link Calculator.CalcMode}
	 */
	private void doCalculation(CalcMode mode) {
		mCalculator.beginCalculation(determineGrossWages(), determineNumChildren(), mode, determineBalance());
	}
	
	public void setCalculator(Calculator calculator) {
		mCalculator = calculator;
	}
	
	public MyCustomizedPanel getPanel() {
		return panel;
	}

	/**
	 * Get double from field, 0.0 default
	 * 
	 * @param field
	 *            A field to get double from
	 * @return A value typed inside field
	 */
	private double getDoubleFromField(JTextField field) {
		try {
			InputParser parser = new InputParser();
			return parser.getInputDouble(field);
		} catch (InvalidInputException e) {
			// No positive double at input
		}
		// Remove unacceptable value, empty text field
		field.setText("0.0");
		// If its format is not acceptable, return default value
		return 0.0;
	}

	/**
	 * Get integer from field, 0 default
	 * 
	 * @param field
	 *            A field to get integer from
	 * @return A value typed inside field
	 */
	private int getIntFromField(JTextField field) {
		try {
			InputParser parser = new InputParser();
			return parser.getInputInt(field);
		} catch (InvalidInputException e) {
			// No positive integer at input
		}
		// Remove unacceptable value, empty text field
		field.setText("0");
		// If its format is not acceptable, return default value
		return 0;
	}

	/**
	 * Construct a double array based on monthly gross wages provided by the
	 * user
	 * 
	 * @return A demanded array of the gross wages
	 */
	private double[] determineGrossWages() {
		double[] monthWages = new double[Constants.NUM_MONTHS];

		// Gross wages are supposed to be typed within fields
		for (int i = 0; i < Constants.NUM_MONTHS; i++) {
			monthWages[i] = getDoubleFromField(panel.getMonthField(i + 1));
		}
		return monthWages;
	}

	/**
	 * Determine a person's balance provided by the user
	 * 
	 * @return A demanded balance
	 */
	private double determineBalance() {
		// Balance is supposed to be typed within a field
		return getDoubleFromField(panel.getBalanceField());
	}

	/**
	 * Determine a person's number of children provided by the user
	 * 
	 * @return A demanded number of children
	 */
	private int determineNumChildren() {
		// Number of children is supposed to be typed within a field
		return getIntFromField(panel.getNumChildrenField());
	}

	/**
	 * A customized {@link JPanel} to suit needs of the GUI of the calculator
	 */
	private class MyCustomizedPanel extends JPanel implements Observer {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Input fields and labels
		 */
		private final static int TEXT_FIELD_NUM_COLUMNS = 10;
		// Months
		private JLabel[] mMonthLabels;
		private JTextField[] mMonthFields;
		// Number of children
		private JLabel mNumChildrenLabel = new JLabel(Constants.TEXT_NUM_CHILDREN);
		private JTextField mNumChildrenField = new JTextField(TEXT_FIELD_NUM_COLUMNS);
		// First pillar
		private JButton mButtonCalcFirst = new JButton(Constants.BUTTON_PILLAR_FIRST);
		// Second pillar
		private JLabel mBalanceLabel = new JLabel(Constants.TEXT_BALANCE);
		private JTextField mBalanceField = new JTextField(TEXT_FIELD_NUM_COLUMNS);
		private ButtonGroup mButtonGroup = new ButtonGroup();
		private JRadioButton mButtonAAAbond = new JRadioButton(Constants.TEXT_FUND_AAA_BOND);
		private JRadioButton mButtonAAAshare = new JRadioButton(Constants.TEXT_FUND_AAA_SHARE);
		private JRadioButton mButtonAAAindex = new JRadioButton(Constants.TEXT_FUND_AAA_INDEX);
		private JRadioButton mButtonBBBbond = new JRadioButton(Constants.TEXT_FUND_BBB_BOND);
		private JRadioButton mButtonBBBshare = new JRadioButton(Constants.TEXT_FUND_BBB_SHARE);
		private JRadioButton mButtonCCCbond = new JRadioButton(Constants.TEXT_FUND_CCC_BOND);
		private JRadioButton mButtonCCCindex = new JRadioButton(Constants.TEXT_FUND_CCC_INDEX);
		private JButton mButtonCalcSecond = new JButton(Constants.BUTTON_PILLAR_SECOND);
		// Output
		private JTextArea mOutputArea = new JTextArea(30, 60);
		private JScrollPane mScrollOutput = new JScrollPane(mOutputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		public MyCustomizedPanel() {
			
			setLayout(null);
			
			// Months
			mMonthLabels = new JLabel[Constants.NUM_MONTHS];
			mMonthFields = new JTextField[Constants.NUM_MONTHS];
			for (int i = 0; i < Constants.NUM_MONTHS; i++) {
				mMonthLabels[i] = new JLabel(Utilities.getMonthName(i + 1) + ", gross wage");
				mMonthLabels[i].setBounds(50, 50 * i, 150, 50);
				add(mMonthLabels[i]);
				mMonthFields[i] = new JTextField(TEXT_FIELD_NUM_COLUMNS);
				mMonthFields[i].setBounds(200, 50 * i, 100, 40);
				add(mMonthFields[i]);
			}
			// Number of children
			mNumChildrenLabel.setBounds(350, 0, 150, 50);
			add(mNumChildrenLabel);
			mNumChildrenField.setBounds(500, 0, 150, 50);
			add(mNumChildrenField);
			// First Pillar
			mButtonCalcFirst.setBounds(350, 50, 300, 50);
			add(mButtonCalcFirst);
			// Button group
			mButtonGroup.add(mButtonAAAbond);
			mButtonGroup.add(mButtonAAAshare);
			mButtonGroup.add(mButtonAAAindex);
			mButtonGroup.add(mButtonBBBbond);
			mButtonGroup.add(mButtonBBBshare);
			mButtonGroup.add(mButtonCCCbond);
			mButtonGroup.add(mButtonCCCindex);
			// Second Pillar
			mBalanceLabel.setBounds(350, 350, 150, 50);
			add(mBalanceLabel);
			mBalanceField.setBounds(500, 350, 150, 50);
			add(mBalanceField);
			mButtonAAAbond.setBounds(350, 120, 300, 30);
			add(mButtonAAAbond);
			mButtonAAAbond.setSelected(true); // default
			mButtonAAAshare.setBounds(350, 150, 300, 30);
			add(mButtonAAAshare);
			mButtonAAAindex.setBounds(350, 180, 300, 30);
			add(mButtonAAAindex);
			mButtonBBBbond.setBounds(350, 210, 300, 30);
			add(mButtonBBBbond);
			mButtonBBBshare.setBounds(350, 240, 300, 30);
			add(mButtonBBBshare);
			mButtonCCCbond.setBounds(350, 270, 300, 30);
			add(mButtonCCCbond);
			mButtonCCCindex.setBounds(350, 300, 300, 30);
			add(mButtonCCCindex);
			mButtonCalcSecond.setBounds(350, 400, 300, 50);
			add(mButtonCalcSecond);
			// Output
			mScrollOutput.setBounds(700, 0, 500, 500);
			add(mScrollOutput);
		}
		
		public JTextField getMonthField(int month) {
			if (month >= 1 && month <= Constants.NUM_MONTHS) {
				return mMonthFields[month - 1];
			}
			return null;
		}
		
		public JTextField getNumChildrenField() {
			return mNumChildrenField;
		}
		
		public JTextField getBalanceField() {
			return mBalanceField;
		}
		
		public JRadioButton getButtonAAAbond() {
			return mButtonAAAbond;
		}
		
		public JRadioButton getButtonAAAshare() {
			return mButtonAAAshare;
		}
		
		public JRadioButton getButtonAAAindex() {
			return mButtonAAAindex;
		}
		
		public JRadioButton getButtonBBBbond() {
			return mButtonBBBbond;
		}
		
		public JRadioButton getButtonBBBshare() {
			return mButtonBBBshare;
		}
		
		public JRadioButton getButtonCCCbond() {
			return mButtonCCCbond;
		}
		
		public JRadioButton getButtonCCCindex() {
			return mButtonCCCindex;
		}
		
		public JButton getmButtonCalcFirst() {
			return mButtonCalcFirst;
		}
		
		public JButton getmButtonCalcSecond() {
			return mButtonCalcSecond;
		}
		
		public JTextArea getOutputArea() {
			return mOutputArea;
		}

		@Override
		public void update(Observable arg0, Object arg1) {
			if (arg1 instanceof String) {
				mOutputArea.append((String) arg1);
			}
		}
	}
}
