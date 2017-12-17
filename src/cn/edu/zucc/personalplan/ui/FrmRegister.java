package cn.edu.zucc.personalplan.ui;

import cn.edu.zucc.personalplan.util.BaseException;
import cn.edu.zucc.personalplan.util.PersonPlanUtil;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class FrmRegister extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("注册");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelUser = new JLabel("用户：");
    private JLabel labelPwd = new JLabel("密码：");
    private JLabel labelPwd2 = new JLabel("密码：");
    private JLabel labelMaxlognum = new JLabel("最大同时登陆数：");
    private JTextField edtUserId = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);
    private JPasswordField edtPwd2 = new JPasswordField(20);
    private JComboBox cmbMaxlognum = null;

    public FrmRegister(Dialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        workPane.add(labelMaxlognum);
        String[] maxlognum = {"", "1", "2", "3", "4", "5"};
        cmbMaxlognum = new JComboBox(maxlognum);
        workPane.add(cmbMaxlognum);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 180);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel)
            this.setVisible(false);
        else if (e.getSource() == this.btnOk) {
            if (this.cmbMaxlognum.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(null, "请选择最大同时登陆数", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String userid = this.edtUserId.getText();
            String pwd1 = new String(this.edtPwd.getPassword());
            String pwd2 = new String(this.edtPwd2.getPassword());
            try {
                PersonPlanUtil.userManager.reg(userid, pwd1, pwd2, this.cmbMaxlognum.getSelectedIndex());
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
