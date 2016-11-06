

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import rafgfxlib.GameHost;

public class Dialog extends JDialog {
	
	private GameHost mHost;
	private JTextField mName;
	private int mScore;
	
	public Dialog(GameHost host, int score) {
		mHost = host;
		mScore = score;
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(Strings.DIALOG);
		
		initPanel();

		pack();
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setVisible(true);
	}
	
	private void initPanel() {
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BorderLayout());
		
		
		JPanel north = new JPanel(new FlowLayout());
		
		JLabel name = new JLabel(Strings.NAME);
		name.setFont(Const.MENU_FONT);
		name.setSize(30, 30);
		north.add(name);
		
		mName = new JTextField(20);
		mName.setSize(50, 30);
		mName.setFont(Const.MENU_FONT);
		north.add(mName);
		
		JPanel temp = new JPanel();
		north.add(temp);
		
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		JLabel score = new JLabel(Strings.SCORE);
		score.setSize(100, 30);
		score.setFont(Const.MENU_FONT);
		center.add(score);
		
		JLabel sc = new JLabel(String.valueOf(mScore));
		sc.setFont(Const.MENU_FONT);
		center.add(sc);
		
		JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton ok = new JButton("OK");
		ok.setFont(Const.MENU_FONT);
		ok.addActionListener(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((Leaderboard) mHost.getState(Strings.LEADERBOARD)).checkList(mScore, mName.getText());
				dispose();
				
			}
		});
		south.add(ok);
		
		dialogPanel.add(north, BorderLayout.NORTH);
		dialogPanel.add(center, BorderLayout.CENTER);
		dialogPanel.add(south, BorderLayout.SOUTH);
		
		setContentPane(dialogPanel);
	}

}
