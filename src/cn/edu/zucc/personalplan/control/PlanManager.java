package cn.edu.zucc.personalplan.control;

import cn.edu.zucc.personalplan.itf.IPlanManager;
import cn.edu.zucc.personalplan.model.BeanPlan;
import cn.edu.zucc.personalplan.model.BeanUser;
import cn.edu.zucc.personalplan.util.BaseException;
import cn.edu.zucc.personalplan.util.DBUtil;
import cn.edu.zucc.personalplan.util.DataboolUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class PlanManager implements IPlanManager {
	@Override
	public BeanPlan addPlan(String name, String deadline) throws BaseException {
		if (name.length() < 1 || name.length() > 128)
			throw new BaseException("计划名应在1~128个字符之间");
		new DataboolUtil().Datebool(deadline);
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "Select * from Beanplan where planname = ? and userid = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, BeanUser.currentLoginUser.getUserid());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BaseException("该任务已存在");
			pst.close();
			rs.close();
			sql = "insert into Beanplan(planname,userid,plancreatetime,deadline) values(?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			pst.setString(2, BeanUser.currentLoginUser.getUserid());
			pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setDate(4, Date.valueOf(deadline));
			pst.execute();
			pst.close();
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
		return null;
	}

	@Override
	public List<BeanPlan> loadAll() throws BaseException {
		List<BeanPlan> result = new ArrayList<BeanPlan>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select planid,planname,stepnum,finishnum,userid ,starfininum , plancreatetime ,deadline "
					+ "from Beanplan where userid = ? ";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUserid());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BeanPlan p = new BeanPlan();
				p.setPlanid(rs.getInt(1));
				p.setPlanname(rs.getString(2));
				p.setStepnum(rs.getInt(3));
				p.setFinishnum(rs.getInt(4));
				p.setUserid(rs.getString(5));
				p.setStarfininum(rs.getInt(6));
				p.setPlancreatetime(rs.getDate(7));
				p.setDeadline(rs.getDate(8));
				result.add(p);
			}
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
		return result;
	}

	@Override
	public List<BeanPlan> loadfinish1() throws BaseException {
		List<BeanPlan> result = new ArrayList<BeanPlan>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select planid,planname,stepnum,finishnum,userid ,starfininum , plancreatetime ,deadline "
					+ "from beanplan p where p.userid = ? and  exists(SELECT * FROM beanstep s "
					+ "where s.planfinishtime < s.actualfinishtime and p.planid = s.planid )";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUserid());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BeanPlan p = new BeanPlan();
				p.setPlanid(rs.getInt(1));
				p.setPlanname(rs.getString(2));
				p.setStepnum(rs.getInt(3));
				p.setFinishnum(rs.getInt(4));
				p.setUserid(rs.getString(5));
				p.setStarfininum(rs.getInt(6));
				p.setPlancreatetime(rs.getDate(7));
				p.setDeadline(rs.getDate(8));
				result.add(p);
			}
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
		return result;
	}

	@Override
	public List<BeanPlan> loadfinish2() throws BaseException {
		List<BeanPlan> result = new ArrayList<BeanPlan>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select planid,planname,stepnum,finishnum,userid ,starfininum , plancreatetime ,deadline "
					+ "from beanplan p where p.userid = ? and stepnum = finishnum and not exists"
					+ "(SELECT * FROM beanstep s where s.planfinishtime < s.actualfinishtime "
					+ "and p.planid = s.planid )";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUserid());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BeanPlan p = new BeanPlan();
				p.setPlanid(rs.getInt(1));
				p.setPlanname(rs.getString(2));
				p.setStepnum(rs.getInt(3));
				p.setFinishnum(rs.getInt(4));
				p.setUserid(rs.getString(5));
				p.setStarfininum(rs.getInt(6));
				p.setPlancreatetime(rs.getDate(7));
				p.setDeadline(rs.getDate(8));
				result.add(p);
			}
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
		return result;
	}

	@Override
	public void deletePlan(BeanPlan plan) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select planid from beanstep where planid = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlanid());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BaseException("计划中已存在步骤不能删除");
			pst.close();
			rs.close();
			sql = "delete  from Beanplan where planid = ? ";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlanid());
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
	public void modifyPlan(BeanPlan plan) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "Select * from Beanplan where planname = ? and userid = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, plan.getPlanname());
			pst.setString(2, BeanUser.currentLoginUser.getUserid());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BaseException("计划名已存在");
			pst.close();
			rs.close();
			if (!"".equals(plan.getPlanname())) {
				sql = "update Beanplan set planname = ?  where planid = ?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, plan.getPlanname());
				pst.setInt(2, plan.getPlanid());
				pst.execute();
				pst.close();
			}
			if (plan.getDeadline() != null) {
				sql = "update Beanplan set deadline = ?  where planid = ?";
				pst = conn.prepareStatement(sql);
				pst.setDate(1, new Date(plan.getDeadline().getTime()));
				pst.setInt(2, plan.getPlanid());
				pst.execute();
				pst.close();
			}
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
}
