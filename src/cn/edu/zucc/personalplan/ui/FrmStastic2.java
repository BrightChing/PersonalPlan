package cn.edu.zucc.personalplan.ui;

import cn.edu.zucc.personalplan.model.BeanPlan;
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



public class FrmStastic2 extends JDialog {
	
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable= new JTable(tablmod);
	private Object tblPlanTitle[] = BeanPlan.tableTitles;
	private Object tblPlanData[][];
	List<BeanPlan> allPlan = null;
	void reloadPlanTable() {// 这是测试数据，需要用实际数替换
		try {
			allPlan = PersonPlanUtil.planManager.loadfinish2();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblPlanData = new Object[allPlan.size()][BeanPlan.tableTitles.length];
		for (int i = 0; i < allPlan.size(); i++) {
			tblPlanData[i][0] = i + 1;
			for (int j = 1; j < BeanPlan.tableTitles.length; j++)
				tblPlanData[i][j] = allPlan.get(i).getCell(j);
		}
		tablmod.setDataVector(tblPlanData,tblPlanTitle);
		this.dataTable.validate();
		this.dataTable.repaint();
	}
		
	public FrmStastic2(Frame f, String s, boolean b) {
		super(f, s, b);
		//提取现有数据
		this.reloadPlanTable();
		this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.validate();
		this.dataTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = FrmStastic2.this.dataTable.getSelectedRow();
				if (i < 0) {
					return;
				}
				JFrame JF = new JFrame();
				BeanPlan plan = allPlan.get(i);
				FrmListstep dlg =  new FrmListstep(JF,plan.getPlanname()+"按时完成计划统计", true, plan);
				dlg.setVisible(true);
			}
		});
	}
}