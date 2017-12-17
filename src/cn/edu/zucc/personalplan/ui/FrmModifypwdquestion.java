package cn.edu.zucc.personalplan.ui;

import cn.edu.zucc.personalplan.model.BeanPlan;
import cn.edu.zucc.personalplan.model.BeanUser;
import cn.edu.zucc.personalplan.util.BaseException;
import cn.edu.zucc.personalplan.util.PersonPlanUtil;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;




public class FrmModifypwdquestion extends JDialog implements ActionListener {
	public BeanPlan plan=null;
	private JPanel toolBar = new JPanel();
	private JPanel toolBar1 = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	private JLabel labelpwdquestion = new JLabel("");
	private JLabel labeloldPwdanswer = new JLabel("原密保答案：");
	private JLabel labelPwdquestoin = new JLabel("密保问题：");
	private JLabel labelPwdanswer = new JLabel("密保答案：");
	
	private JTextField edtoldPwdanswer = new JTextField(50);
	private JTextField edtPwdquestoin = new JTextField(50);
	private JTextField edtPwdanswer = new JTextField(50);

	public FrmModifypwdquestion(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.labelpwdquestion.setText("密保问题："+ BeanUser.currentLoginUser.getPwdquestion());
		toolBar1.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar1.add(labelpwdquestion);
		this.getContentPane().add(toolBar1, BorderLayout.NORTH);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labeloldPwdanswer);
		workPane.add(edtoldPwdanswer);
		workPane.add(labelPwdquestoin);
		workPane.add(edtPwdquestoin);
		workPane.add(labelPwdanswer);
		workPane.add(edtPwdanswer);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(680, 200);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			String pwdquestoin=this.edtPwdquestoin.getText();
			String pwdanswer=this.edtPwdanswer.getText();
			String oldpwdanswer=this.edtoldPwdanswer.getText();
			BeanUser user = BeanUser.currentLoginUser;
			try {
				PersonPlanUtil.userManager.changePwdquestion(user, pwdquestoin, pwdanswer,oldpwdanswer);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
