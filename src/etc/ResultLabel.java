package etc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class ResultLabel extends JTextArea{
	
	public ResultLabel(){
		this.setOpaque(false);
		this.setBounds(420, 570, 400, 200);
		this.setVisible(true);
		this.setForeground(Color.yellow);
		this.setBackground(null);
		this.setFont(new Font("Serif", Font.BOLD, 60));
		this.setText("");
	}
	
	public void setResult(int id) {
		if(id==0) {
			this.setLocation(460, 610);
			this.setText("DRAW!");
		}
		else this.setText("Player "+id+"\nWin!!");
	}
	
}
