package cn.edu.zucc.personalplan.model;

public class BeanUser {
	public static BeanUser currentLoginUser = null;
	private String userid;
	private String pwd;
	private int maxerrorpwdnum;
	private String pwdquestion;
	private String pwdanswer;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getMaxerrorpwdnum() {
		return maxerrorpwdnum;
	}

	public void setMaxerrorpwdnum(int maxerrorpwdnum) {
		this.maxerrorpwdnum = maxerrorpwdnum;
	}

	public String getPwdquestion() {
		return pwdquestion;
	}

	public void setPwdquestion(String pwdquestion) {
		this.pwdquestion = pwdquestion;
	}

	public String getPwdanswer() {
		return pwdanswer;
	}

	public void setPwdanswer(String pwdanswer) {
		this.pwdanswer = pwdanswer;
	}
}
