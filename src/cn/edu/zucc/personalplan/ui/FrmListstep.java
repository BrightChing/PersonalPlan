package cn.edu.zucc.personalplan.ui;

import cn.edu.zucc.personalplan.model.BeanPlan;
import cn.edu.zucc.personalplan.model.BeanStep;
import cn.edu.zucc.personalplan.util.BaseException;
import cn.edu.zucc.personalplan.util.PersonPlanUtil;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class FrmListstep extends JDialog {

	DefaultTableModel tabStepModel = new DefaultTableModel();
	private JTable dataTableStep = new JTable(tabStepModel);
	private Object tblStepTitle[] = BeanStep.tblStepTitle;
	private Object tblStepData[][];
	List<BeanStep> planSteps = null;
	BeanPlan plan = null;

	private void reloadPlanStepTabel(BeanPlan curPlan, Boolean flag) {
		try {
			planSteps = PersonPlanUtil.stepManager.loadSteps(curPlan, flag);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblStepData = new Object[planSteps.size()][BeanStep.tblStepTitle.length];
		for (int i = 0; i < planSteps.size(); i++) {
			tblStepData[i][0] = i + 1;
			for (int j = 1; j < BeanStep.tblStepTitle.length; j++)
				tblStepData[i][j] = planSteps.get(i).getCell(j);
		}
		tabStepModel.setDataVector(tblStepData, tblStepTitle);
		this.dataTableStep.validate();
		this.dataTableStep.repaint();
	}

	private void reloadPlanStepTabel(BeanPlan curPlan) {
		try {
			planSteps = PersonPlanUtil.stepManager.loadSteps(curPlan);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblStepData = new Object[planSteps.size()][BeanStep.tblStepTitle.length];
		for (int i = 0; i < planSteps.size(); i++) {
			tblStepData[i][0] = i + 1;
			for (int j = 1; j < BeanStep.tblStepTitle.length; j++)
				tblStepData[i][j] = planSteps.get(i).getCell(j);
		}
		tabStepModel.setDataVector(tblStepData, tblStepTitle);
		this.dataTableStep.validate();
		this.dataTableStep.repaint();
	}

	public FrmListstep(JFrame jF, String s, boolean b, BeanPlan plan) {
		super(jF, s, b);
		// 提取现有数据
		this.plan = plan;
		if (s.endsWith("超时完成步骤统计"))
			this.reloadPlanStepTabel(this.plan, true);
		else if (s.endsWith("按时完成步骤统计"))
			this.reloadPlanStepTabel(this.plan, false);
		else
			this.reloadPlanStepTabel(this.plan);
		this.getContentPane().add(new JScrollPane(this.dataTableStep), BorderLayout.CENTER);
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);
		this.validate();
	}
}