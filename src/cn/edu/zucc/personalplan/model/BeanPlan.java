package cn.edu.zucc.personalplan.model;

import java.util.Date;

public class BeanPlan {
	public static final String[] tableTitles = { "编号", "名称",	"步骤数", "完成数", "已开始未完成数", "截至时间" };
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	private int planid; // 编号
	private String userid;// 所属用户编号
	private String planname;// 名称
	private int stepnum;// 步骤数
	private int finishnum;// 已完成数
	private int starfininum; // 已开始未完成数
	private Date plancreatetime;// 计划创建时间
	private Date deadline;

	public int getPlanid() {
		return planid;
	}

	public void setPlanid(int planid) {
		this.planid = planid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPlanname() {
		return planname;
	}

	public void setPlanname(String planname) {
		this.planname = planname;
	}

	public int getStepnum() {
		return stepnum;
	}

	public void setStepnum(int stepnum) {
		this.stepnum = stepnum;
	}

	public int getFinishnum() {
		return finishnum;
	}

	public void setFinishnum(int finishnum) {
		this.finishnum = finishnum;
	}

	public int getStarfininum() {
		return starfininum;
	}

	public void setStarfininum(int starfininum) {
		this.starfininum = starfininum;
	}

	public Date getPlancreatetime() {
		return plancreatetime;
	}

	public void setPlancreatetime(Date plancreatetime) {
		this.plancreatetime = plancreatetime;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getCell(int col) {
		if (col == 0)
			return String.valueOf(this.getPlanid());
		else if (col == 1)
			return this.getPlanname();
		else if (col == 2)
			return String.valueOf(this.getStepnum());
		else if (col == 3)
			return String.valueOf(this.getFinishnum());
		else if (col == 4)
			return String.valueOf(this.getStarfininum());
		if (col == 5)
			return String.valueOf(this.getDeadline());
		else
			return "";
	}

}
