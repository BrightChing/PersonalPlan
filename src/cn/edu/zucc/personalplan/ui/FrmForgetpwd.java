package cn.edu.zucc.personalplan.ui;

import cn.edu.zucc.personalplan.model.BeanUser;
import cn.edu.zucc.personalplan.util.BaseException;
import cn.edu.zucc.personalplan.util.PersonPlanUtil;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class FrmForgetpwd extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel toolBar1 = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");
	private JLabel labelpwdquestion = new JLabel("");
	private JLabel labelpwdanswer = new JLabel("密保答案：");
	private JLabel labelPwd = new JLabel("密码：");
	private JLabel labelPwd2 = new JLabel("密码：");

	private JTextField edtpwdanswer = new JTextField(50);
	private JPasswordField edtPwd = new JPasswordField(20);
	private JPasswordField edtPwd2 = new JPasswordField(20);
	public FrmForgetpwd(Dialog f, String s, boolean b) {
		super(f, s, b);
		this.labelpwdquestion.setText("密保问题："+ BeanUser.currentLoginUser.getPwdquestion());
		toolBar1.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar1.add(labelpwdquestion);
		this.getContentPane().add(toolBar1, BorderLayout.NORTH);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelpwdanswer);
		workPane.add(edtpwdanswer);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(labelPwd2);
		workPane.add(edtPwd2);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(650, 170);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if (e.getSource() == this.btnOk) {
			String pwdanswer = this.edtpwdanswer.getText();
			String pwd1 = new String(this.edtPwd.getPassword());
			String pwd2 = new String(this.edtPwd2.getPassword());
			try {
				PersonPlanUtil.userManager.changePwdtoquestion(BeanUser.currentLoginUser, pwdanswer, pwd1, pwd2);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
