package cn.edu.zucc.personalplan.model;

import java.util.Date;

public class BeanStep {
	public static final String[] tblStepTitle = { "编号", "名称", "计划开始时间", "计划完成时间", "实际开始时间", "实际完成时间", " 步骤创建时间" };
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	private int stepid; // 步骤编号
	private int planid; // 计划编号
	private String stepname;// 步骤名称
	private Date planstardate;// 计划开始时间
	private Date planfinishdate;// 计划完成时间
	private Date actualstardate;// 实际开始时间
	private Date actualfinishdate;// 实际完成时间
	private Date stepcreatetime;// 步骤创建时间

	public int getStepid() {
		return stepid;
	}

	public void setStepid(int stepid) {
		this.stepid = stepid;
	}

	public int getPlanid() {
		return planid;
	}

	public void setPlanid(int planid) {
		this.planid = planid;
	}

	public String getStepname() {
		return stepname;
	}

	public void setStepname(String stepname) {
		this.stepname = stepname;
	}

	public Date getPlanstardate() {
		return planstardate;
	}

	public void setPlanstardate(Date planstardate) {
		this.planstardate = planstardate;
	}

	public Date getPlanfinishdate() {
		return planfinishdate;
	}

	public void setPlanfinishdate(Date planfinishdate) {
		this.planfinishdate = planfinishdate;
	}

	public Date getActualstardate() {
		return actualstardate;
	}

	public void setActualstardate(Date actualstardate) {
		this.actualstardate = actualstardate;
	}

	public Date getActualfinishdate() {
		return actualfinishdate;
	}

	public void setActualfinishdate(Date actualfinishdate) {
		this.actualfinishdate = actualfinishdate;
	}

	public Date getStepcreatetime() {
		return stepcreatetime;
	}

	public void setStepcreatetime(Date stepcreatetime) {
		this.stepcreatetime = stepcreatetime;
	}

	// "编号", "名称", "计划开始时间", "计划完成时间", "实际开始时间", "实际完成时间"
	public String getCell(int col) {
		if (col == 0)
			return String.valueOf(this.getStepid());
		else if (col == 1)
			return this.getStepname();
		else if (col == 2)
			return String.valueOf(this.getPlanstardate());
		else if (col == 3)
			return String.valueOf(this.getPlanfinishdate());
		else if (col == 4)
			return String.valueOf(this.getActualstardate());
		else if (col == 5)
			return String.valueOf(this.getActualfinishdate());
		else if (col == 6)
			return String.valueOf(this.getStepcreatetime());
		else
			return "";
	}
}
