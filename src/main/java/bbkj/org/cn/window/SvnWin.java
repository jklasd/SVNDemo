package bbkj.org.cn.window;

import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import bbkj.org.cn.service.MainService;
import bbkj.org.cn.util.Config;

public class SvnWin {

	private JFrame frame;
	private TextField textField;
	private MainService service;
	/**
	 * Create the application.
	 */
	public SvnWin() {
		initialize();
		service = new MainService();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		
		getFrame().setTitle("增量版本助手");
		
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
		JButton button = new JButton("运行");
		button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.setSWtip(textField);
				service.setSVN_WIN(SvnWin.this);
				service.init();
				service.loadSvnData();
			}
		});
		button.setBounds(33, 10, 93, 24);
		getFrame().getContentPane().add(button);
		
		textField = new TextField();
		
		textField.setText("准备状态");
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setBounds(153, 10, 186, 24);
		frame.getContentPane().add(textField);
//		addItem(1,"app");
		String[] items = Config.value("items").split(";");
		itemConfig = new HashMap<String, Map<String,JTextField>>();
		for(int i=0;i<items.length;i++){
			itemConfig.put(items[i], new HashMap<String, JTextField>());
			addItem(i+1,items[i]);
		}
		getFrame().setResizable(false);
		getFrame().setBounds(100, 100, 573, 130*items.length+50);
	}
	private Map<String,OperateJPanel> jpanel = new HashMap<String, OperateJPanel>();
	private Map<String,Map<String,JTextField>> itemConfig = new HashMap<String, Map<String,JTextField>>();

	private void addItem(int y,String item) {
		OperateJPanel panel = new OperateJPanel(this,item);
		panel.setBounds(33, 100*y, 495, 98);
		frame.getContentPane().add(panel);
		jpanel.put(item, panel);
		panel.setLayout(null);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public TextField getTextField() {
		return textField;
	}

	public void setTextField(TextField textField) {
		this.textField = textField;
	}

	public OperateJPanel getJpanel(String item) {
		return jpanel.get(item);
	}
}
