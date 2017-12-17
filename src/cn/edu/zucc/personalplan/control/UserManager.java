package cn.edu.zucc.personalplan.control;

import cn.edu.zucc.personalplan.itf.IUserManager;
import cn.edu.zucc.personalplan.model.BeanUser;
import cn.edu.zucc.personalplan.util.BaseException;
import cn.edu.zucc.personalplan.util.DBUtil;


import java.sql.Connection;
import java.sql.SQLException;

public class UserManager implements IUserManager {

	@Override
	public void reg(String userid, String pwd, String pwd2, int maxlognum) throws BaseException {
		if (!pwd.equals(pwd2))
			throw new BaseException("两次输入的密码不相同");
		if (pwd.length() < 1 || pwd.length() > 20)
			throw new BaseException("密码应为1~20个字符");
		if (pwd2.length() < 1 || pwd2.length() > 20)
			throw new BaseException("确认密码应为1~20个字符");
		if (userid.length() < 1 || userid.length() > 20)
			throw new BaseException("用户名应为1~20个字符");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from beanuser where userid=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				throw new BaseException("此用户名已被占用");
			}
			pst.close();
			rs.close();
			sql = "insert into beanuser (userid ,pwd,maxlognum) values(?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, pwd);
			pst.setInt(3, maxlognum);
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public BeanUser login(String userid, String pwd) throws BaseException {
		if (pwd.length() < 1 || pwd.length() > 20)
			throw new BaseException("密码应为1~20个字符");
		if (userid.length() < 1 || userid.length() > 20)
			throw new BaseException("用户名应为1~20个字符");
		BeanUser user = new BeanUser();
		user.setUserid(userid);
		user.setPwd(pwd);
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select pwd,maxlognum,maxerrorpwdnum ,pwdquestion ,pwdanswer from beanuser where userid=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next()) {
				throw new BaseException("用户名输入错误");
			}
			if (!pwd.equals(rs.getString("pwd"))) {
				int num = rs.getInt("maxerrorpwdnum") - 1;
				if (num > 0) {
					sql = "update beanuser set maxerrorpwdnum = maxerrorpwdnum -1 where userid = ?";
					java.sql.PreparedStatement pst1 = conn.prepareStatement(sql);
					pst1.setString(1, userid);
					pst1.execute();
					throw new BaseException("密码输入错误,您还有" + num + "机会");
				}
				throw new BaseException("密码输入错误,请找回密码");
			}
			if (rs.getInt(2) < 1)
				throw new BaseException("登陆用户数超过最大允许登陆数");
			user.setPwdquestion(rs.getString("pwdquestion"));
			user.setPwdanswer(rs.getString("pwdanswer"));
			pst.close();
			rs.close();
			sql = "update beanuser set maxlognum = maxlognum -1 , maxerrorpwdnum = 3 where userid = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserid());
			pst.execute();
			pst.close();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public BeanUser load(String userid) throws BaseException {
		BeanUser user = new BeanUser();
		user.setUserid(userid);
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select pwd,maxlognum,maxerrorpwdnum ,pwdquestion,pwdanswer from beanuser where userid=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next()) {
				throw new BaseException("没有这个用户");
			}
			user.setUserid(userid);
			user.setPwd(rs.getString("pwd"));
			user.setPwdquestion(rs.getString("pwdquestion"));
			user.setPwdanswer(rs.getString("pwdanswer"));
			pst.close();
			rs.close();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void changePwd(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException {
		if (oldPwd.length() < 1 || oldPwd.length() > 20)
			throw new BaseException("密码应为1~20个字符");
		if (newPwd.length() < 1 || newPwd.length() > 20)
			throw new BaseException("密码应为1~20个字符");
		if (newPwd2.length() < 1 || newPwd2.length() > 20)
			throw new BaseException("密码应为1~20个字符");
		if (!newPwd2.equals(newPwd))
			throw new BaseException("新密码不一致");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select pwd from beanuser where userid=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserid());
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next()) {
				throw new BaseException("用户名输入错误");
			}
			if (!oldPwd.equals(rs.getString(1)))
				throw new BaseException("原密码输入错误");
			if (newPwd.equals(rs.getString(1)))
				throw new BaseException("新密码与原密码相同");
			pst.close();
			rs.close();
			sql = "update beanuser set pwd =? where userid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, newPwd);
			pst.setString(2, user.getUserid());
			pst.execute();
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void changePwdtoquestion(BeanUser user, String pwdanswer, String newPwd, String newPwd2)
			throws BaseException {
		if (pwdanswer.length() < 1 || pwdanswer.length() > 50)
			if (newPwd.length() < 1 || newPwd.length() > 20)
				throw new BaseException("密码应为1~20个字符");
		if (newPwd2.length() < 1 || newPwd2.length() > 20)
			throw new BaseException("密码应为1~20个字符");
		if (!newPwd2.equals(newPwd))
			throw new BaseException("密码不一致");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select pwdanswer from beanuser where userid=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserid());
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next()) {
				throw new BaseException("用户名输入错误");
			}
			if (!pwdanswer.equals(rs.getString(1)))
				throw new BaseException("密保答案输入错误");
			pst.close();
			rs.close();
			sql = "update beanuser set pwd =? where userid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, newPwd);
			pst.setString(2, user.getUserid());
			pst.execute();
			pst.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setPwdquestion(BeanUser user, String pwdquestion, String pwdanswer) throws BaseException {
		if (pwdquestion.length() < 1 || pwdquestion.length() > 50)
			throw new BaseException("密保问题应为1~50个字符");
		if (pwdanswer.length() < 1 || pwdanswer.length() > 50)
			throw new BaseException("密保答案应为1~50个字符");
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update beanuser set pwdquestion =?,pwdanswer = ? where userid = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, pwdquestion);
			pst.setString(2, pwdanswer);
			pst.setString(3, user.getUserid());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void changePwdquestion(BeanUser user, String pwdquestion, String pwdanswer, String oldpwdanswer)
			throws BaseException {
		if (pwdquestion.length() > 50)
			throw new BaseException("密保问题不能多余50个字符");
		if (pwdquestion.length() > 0)
			user.setPwdquestion(pwdquestion);
		if (oldpwdanswer.length() < 1 || oldpwdanswer.length() > 50)
			throw new BaseException("旧密保答案应为1~50个字符");
		if (pwdanswer.length() < 1 || pwdanswer.length() > 50)
			throw new BaseException("密保答案应为1~50个字符");
		
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select pwdanswer from beanuser  where userid = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserid());
			java.sql.ResultSet rs = pst.executeQuery();
			rs.next();
			if (!oldpwdanswer.equals(rs.getString(1)))
				throw new BaseException("旧密保答案错误");
			pst.close();
			rs.close();
			sql = "update beanuser set pwdquestion =?,pwdanswer = ? where userid = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getPwdquestion());
			pst.setString(2, pwdanswer);
			pst.setString(3, user.getUserid());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void logout(BeanUser user) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update beanuser set maxlognum = maxlognum + 1 where userid = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserid());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
