package bbkj.org.cn.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import bbkj.org.cn.service.MainService;

public class OperateJPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6827312954393097070L;
	private SvnWin win;
	private String item;
	private JProgressBar progressBar;
	private JLabel progressLable;
	private JButton run;
	private JTextField startV;
	private JTextField endV;
	private JTextField versions;
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	public JLabel getProgressLable() {
		return progressLable;
	}
	public JButton getRun() {
		return run;
	}
	public JTextField getStartV() {
		return startV;
	}
	public JTextField getEndV() {
		return endV;
	}
	public JTextField getVersions() {
		return versions;
	}
	public OperateJPanel(SvnWin win, String item){
		this.item = item;
		this.win = win;
		progressBar = new JProgressBar();
		progressBar.setBounds(0, 74, 434, 14);
		this.add(progressBar);

		JLabel lblNewLabel = new JLabel(item);
		lblNewLabel.setBounds(0, 10, 60, 15);
		this.add(lblNewLabel);
		
		progressLable = new JLabel("0%");
		progressLable.setBounds(444, 73, 41, 15);
		this.add(progressLable);
		
		
		startV = new JTextField();
		startV.setToolTipText("起始版本");
		startV.setBounds(162, 11, 68, 21);
		this.add(startV);
		startV.setColumns(10);
		
		endV = new JTextField();
		endV.setToolTipText("结束版本");
		endV.setBounds(318, 11, 66, 21);
		this.add(endV);
		endV.setColumns(10);
		
		run = new JButton("运行");
		run.setBounds(392, 10, 93, 23);
		run.addActionListener(new ActionRun(item));
		this.add(run);
		
		versions = new JTextField();
		versions.setEditable(false);
		versions.setText("如：123;456");
		versions.setBounds(160, 39, 325, 21);
		this.add(versions);
		versions.setColumns(10);
		
		JLabel label_1 = new JLabel("指定版本号:");
		label_1.setBounds(87, 42, 74, 15);
		this.add(label_1);
		
		JLabel label_2 = new JLabel("结束版本号:");
		label_2.setBounds(240, 14, 68, 15);
		this.add(label_2);
		
		JLabel label_3 = new JLabel("起始版本号:");
		label_3.setBounds(87, 14, 68, 15);
		this.add(label_3);
		
		Radio radioAction = new Radio(item);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("");
		rdbtnNewRadioButton.setBounds(60, 39, 21, 23);
		this.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.addActionListener(radioAction);
		
		JRadioButton radioButton = new JRadioButton("");
		radioButton.setSelected(true);
		radioButton.setBounds(60, 10, 21, 23);
		this.add(radioButton);
		radioButton.addActionListener(radioAction);
	}
	public String getItem() {
		return item;
	}
	class Radio implements ActionListener{
		private String item;
		public Radio(String item){
			this.item = item;
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getID());
		}
	}
	
	class ActionRun implements ActionListener{
		private String item;
		private MainService ms;
		public ActionRun(String item){
			this.item = item;
			ms = new MainService(item);
		}
		public void actionPerformed(ActionEvent e) {
			ms.setSWtip(win.getTextField());
			ms.setSVN_WIN(win);
			ms.loadSvnData();
		}
	}
}
