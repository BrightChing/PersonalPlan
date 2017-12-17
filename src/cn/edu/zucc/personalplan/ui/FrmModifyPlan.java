package cn.edu.zucc.personalplan.ui;

import cn.edu.zucc.personalplan.model.BeanPlan;
import cn.edu.zucc.personalplan.util.BaseException;
import cn.edu.zucc.personalplan.util.DataboolUtil;
import cn.edu.zucc.personalplan.util.PersonPlanUtil;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class FrmModifyPlan extends JDialog implements ActionListener {
	public BeanPlan plan = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");

	private JLabel labelName = new JLabel("计划名：");
	private JTextField edtName = new JTextField(20);
	private JLabel labelDeadline = new JLabel("截止时间：");
	private JTextField edtDeadline = new JTextField(20);

	public FrmModifyPlan(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelDeadline);
		workPane.add(edtDeadline);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 180);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);
		// 事件监听
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if (e.getSource() == this.btnOk) {
			if ("".equals(this.edtName.getText()))
				plan.setPlanname(this.edtName.getText());
			try {
				if (!"".equals(this.edtDeadline.getText()))
					plan.setDeadline(new DataboolUtil().Datebool(this.edtDeadline.getText()));
				PersonPlanUtil.planManager.modifyPlan(plan);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
