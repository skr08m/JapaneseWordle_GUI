package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.HintButtonController;
import controller.ResetButtonController;
import controller.SubmitController;
import model.WordleModel;

public class WordleView extends JFrame {
	private WordleModel model;
	private JLabel[][] letterLabels;
	private DefaultTableModel hintTableModel;
	private JTextField inputField;
	private JScrollPane hintScroll;
	private JPanel gridPanel;
	private JLabel statusLabel;
	private JButton submitButton, resetButton, hintButton;
	public final int ROWS = 6;
	final int COLS = 5;

	public WordleView(WordleModel model) {
		this.model = model;
		initComponents();
	}

	private void initComponents() {
		setTitle("ヒント付き 日本語Wordle");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		JPanel centerPanel = new JPanel(new BorderLayout());

		//履歴パネル
		gridPanel = new JPanel(new GridLayout(ROWS, COLS, 5, 5));
		letterLabels = new JLabel[ROWS][COLS];
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				JLabel label = new JLabel("　", SwingConstants.CENTER);
				label.setOpaque(true);
				label.setBackground(Color.LIGHT_GRAY);
				label.setFont(new Font("SansSerif", Font.BOLD, 24));
				gridPanel.add(label);
				letterLabels[r][c] = label;
			}
		}
		gridPanel.setBorder(BorderFactory.createTitledBorder("履歴"));

		//ヒントパネル
		String[] hintColumns = {"ヒント"};
		hintTableModel = new DefaultTableModel(null, hintColumns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;//編集不可にする
			}
		};
		JTable hintTable = new JTable(hintTableModel);
		hintScroll = new JScrollPane(hintTable);
		hintScroll.setVisible(false);
		hintScroll.setPreferredSize(new Dimension(250, 0));

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.setBorder(BorderFactory.createTitledBorder("答えになるかも？"));
		rightPanel.add(hintScroll, BorderLayout.CENTER);

		//全体パネルへ
		centerPanel.add(gridPanel, BorderLayout.CENTER);
		centerPanel.add(rightPanel, BorderLayout.EAST);
		add(centerPanel, BorderLayout.CENTER);

		//入力＆ボタン
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

		inputField = new JTextField();
		inputField.setFont(new Font("SansSerif", Font.PLAIN, 20));
		inputField.setHorizontalAlignment(JTextField.CENTER);
		inputField.setMaximumSize(new Dimension(200, 40));

		JPanel buttonPanel = new JPanel();
		submitButton = new JButton("決定");
		resetButton = new JButton("リセット");
		hintButton = new JButton("ヒント");

		buttonPanel.add(submitButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(hintButton);

		inputPanel.add(inputField);
		inputPanel.add(Box.createVerticalStrut(10));
		inputPanel.add(buttonPanel);

		add(inputPanel, BorderLayout.SOUTH);

		//ステータス
		statusLabel = new JLabel("５文字を入力して「決定」を押してください", SwingConstants.CENTER);
		statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		add(statusLabel, BorderLayout.NORTH);

		//ボタン処理
		submitButton.addActionListener(e -> new SubmitController().handleSubmit(this));
		resetButton.addActionListener(e -> new ResetButtonController().handleReset(this));
		hintButton.addActionListener(e -> new HintButtonController().handleHint(this));
	}


	public void switchHintVisible() {
		boolean isVisible = hintScroll.isVisible();
		hintScroll.setVisible(!isVisible);

		hintScroll.getParent().revalidate();// JScrollPaneの親を再レイアウト
		hintScroll.getParent().repaint();// 再描画
	}

	public WordleModel getModel() {
		return model;
	}

	public String getAnswer() {
		return inputField.getText();
	}

	public void setStatusMessage(String msg) {
		statusLabel.setText(msg);
	}

	public void setInputField(String text) {
		inputField.setText(text);
	}

	public void updateHintTable(List<String> hints) {
		hintTableModel.setRowCount(0);
		for (String hint : hints) {
			hintTableModel.addRow(new Object[]{hint});
		}
	}

	public void updateGrid(int row, String answer, List<Color> colors) {
		try {
			for (int c = 0; c < answer.length(); c++) {
				char ch = answer.charAt(c);
				JLabel label = letterLabels[row][c];
				label.setText(String.valueOf(ch));
				label.setBackground(colors.get(c));
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("index error");
		}
	}

	public void initGrid() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				letterLabels[r][c].setText("　");
				letterLabels[r][c].setBackground(Color.LIGHT_GRAY);
			}
		}
	}
}
