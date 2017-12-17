package cn.edu.zucc.personalplan.ui;

import cn.edu.zucc.personalplan.model.BeanStep;
import cn.edu.zucc.personalplan.util.BaseException;
import cn.edu.zucc.personalplan.util.PersonPlanUtil;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class FrmModifyStep extends JDialog implements ActionListener {
	public BeanStep step = null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnOk = new JButton("确定");
	private JButton btnCancel = new JButton("取消");

	private JLabel labelName = new JLabel("步    骤    名：");
	private JTextField edtName = new JTextField(20);
	private JLabel labelPStime = new JLabel("计划开始时间：");
	private JTextField edtPStime = new JTextField(20);
	private JLabel labelPFtime = new JLabel("计划完成时间：");
	private JTextField edtPFtime = new JTextField(20);
		
	public FrmModifyStep(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(this.btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelPStime);
		workPane.add(edtPStime);
		workPane.add(labelPFtime);
		workPane.add(edtPFtime);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 280);
		this.btnCancel.addActionListener(this);
		this.btnOk.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel)
			this.setVisible(false);
		else if (e.getSource() == this.btnOk) {
			String name = this.edtName.getText();
			if(!"".equals(name))
			 step.setStepname(name);
			try {
				PersonPlanUtil.stepManager.modifyStep(step, String.valueOf(this.edtPStime.getText()),
						String.valueOf(this.edtPFtime.getText()));
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
}
