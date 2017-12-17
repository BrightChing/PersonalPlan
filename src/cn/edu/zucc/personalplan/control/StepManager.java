package cn.edu.zucc.personalplan.control;

import cn.edu.zucc.personalplan.itf.IStepManager;
import cn.edu.zucc.personalplan.model.BeanPlan;
import cn.edu.zucc.personalplan.model.BeanStep;
import cn.edu.zucc.personalplan.util.BaseException;
import cn.edu.zucc.personalplan.util.DBUtil;
import cn.edu.zucc.personalplan.util.DataboolUtil;
import cn.edu.zucc.personalplan.util.DbException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StepManager implements IStepManager {
    @Override
    public void add(BeanPlan plan, String name, String planstartdate, String planfinishdate) throws BaseException {
        if (name.length() < 1 || name.length() > 128)
            throw new BaseException("计划名应在1~128个字符之间");
        if ("".equals(planfinishdate))
            throw new BaseException("计划完成时间不能为空");
        if ("".equals(planstartdate))
            throw new BaseException("计划开始时间不能为空");
        java.util.Date Stime = new DataboolUtil().Datebool(planstartdate, "计划开始时间");
        java.util.Date Ftime = new DataboolUtil().Datebool(planstartdate, "计划完成时间");
        if (Stime.compareTo(Ftime) > 0)
            throw new BaseException("计划开始时间晚于计划完成时间");
        if (plan.getDeadline().getTime() < Ftime.getTime())
            throw new BaseException("计划完成时间晚于截止时间\n 或许您需要修改计划截至日期");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from BeanStep where planid  = ? and stepname = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, plan.getPlanid());
            pst.setString(2, name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                throw new BaseException("该计划步骤已存在");
            }
            pst.close();
            rs.close();
            conn.setAutoCommit(false);
            sql = "insert into BeanStep(planid,stepname,planstartime,planfinishtime,stepcreatetime) values(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, plan.getPlanid());
            pst.setString(2, name);
            pst.setDate(3, Date.valueOf(planstartdate));
            pst.setDate(4, Date.valueOf(planfinishdate));
            pst.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.execute();
            pst.close();
            sql = "update Beanplan set stepnum = stepnum + 1 where planid = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, plan.getPlanid());
            pst.execute();
            conn.commit();
            pst.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new DbException(e);
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
    public List<BeanStep> loadSteps(BeanPlan plan) throws BaseException {
        List<BeanStep> result = new ArrayList<BeanStep>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select stepid,stepname,planid,planstartime,planfinishtime,"
                    + "actualstartime,actualfinishtime,stepcreatetime " + "from Beanstep where planid = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, plan.getPlanid());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanStep s = new BeanStep();
                s.setStepid(rs.getInt(1));
                s.setStepname(rs.getString(2));
                s.setPlanid(rs.getInt(3));
                s.setPlanstardate(rs.getDate(4));
                s.setPlanfinishdate(rs.getDate(5));
                s.setActualstardate(rs.getDate(6));
                s.setActualfinishdate(rs.getDate(7));
                s.setStepcreatetime(rs.getDate(8));
                result.add(s);
            }
            pst.close();
            rs.close();
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
        return result;
    }

    @Override
    public List<BeanStep> loadSteps(BeanPlan plan, Boolean flag) throws BaseException {
        List<BeanStep> result = new ArrayList<BeanStep>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select stepid,stepname,planid,planstartime,planfinishtime,"
                    + "actualstartime,actualfinishtime,stepcreatetime from Beanstep where planid = ? ";
            if (flag == true)
                sql += "and actualfinishtime > planfinishtime";
            else
                sql += "and actualfinishtime < planfinishtime";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, plan.getPlanid());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BeanStep s = new BeanStep();
                s.setStepid(rs.getInt(1));
                s.setStepname(rs.getString(2));
                s.setPlanid(rs.getInt(3));
                s.setPlanstardate(rs.getDate(4));
                s.setPlanfinishdate(rs.getDate(5));
                s.setActualstardate(rs.getDate(6));
                s.setActualfinishdate(rs.getDate(7));
                s.setStepcreatetime(rs.getDate(8));
                result.add(s);
            }
            pst.close();
            rs.close();
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
        return result;
    }

    @Override
    public void deleteStep(BeanStep step) throws BaseException {
        // TODO Auto-generated method stub
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "select planid from BeanStep where stepid = ? ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, step.getStepid());
            java.sql.ResultSet rs = pst.executeQuery();
            int num = 0;
            if (rs.next()) {
                num = rs.getInt(1);
            }
            pst.close();
            rs.close();
            sql = "update Beanplan set stepnum = stepnum -1 where planid = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, num);
            pst.execute();
            pst.close();
            if (step.getActualfinishdate() != null) {
                sql = "update Beanplan set finishnum = finishnum -1 where planid = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, num);
                pst.execute();
                pst.close();
            }
            sql = "delete from Beanstep where stepid = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, step.getStepid());
            pst.execute();
            pst.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new DbException(e);
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
    public void startStep(BeanStep step) throws BaseException {
        // TODO Auto-generated method stub
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select actualstartime,actualfinishtime from Beanstep  where stepid = ?";
            java.sql.PreparedStatement pst = null;

            pst = conn.prepareStatement(sql);
            pst.setInt(1, step.getStepid());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getDate(2) != null)
                    throw new BaseException("步骤已经完成,错误操作");
                if (rs.getDate(1) != null)
                    throw new BaseException("步骤已经开始了,错误操作");
            }
            pst.close();
            rs.close();

            conn.setAutoCommit(false);
            sql = "update Beanstep set actualstartime = ? where stepid = ?";
            pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setInt(2, step.getStepid());
            pst.execute();
            pst.close();
            sql = "update Beanplan set starfininum = starfininum + 1 where  planid = "
                    + "(select planid from BeanStep where actualfinishtime = null and stepid = ?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, step.getStepid());
            pst.execute();
            pst.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void finishStep(BeanStep step) throws BaseException {
        // TODO Auto-generated method stub
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();

            String sql = "select actualstartime,actualfinishtime from Beanstep  where stepid = ?";
            java.sql.PreparedStatement pst = null;

            pst = conn.prepareStatement(sql);
            pst.setInt(1, step.getStepid());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getDate(1) == null)
                    throw new BaseException("步骤还未开始，错误操作");
                if (rs.getDate(2) != null)
                    throw new BaseException("步骤早已已经完成，错误操作");
            }
            pst.close();
            rs.close();

            conn.setAutoCommit(false);
            sql = "update Beanstep set actualfinishtime = ? where stepid = ?";
            pst = conn.prepareStatement(sql);
            pst.setDate(1, new Date(new java.util.Date(System.currentTimeMillis()).getTime()));
            pst.setInt(2, step.getStepid());
            pst.execute();
            pst.close();
            sql = "update Beanplan set finishnum = finishnum + 1,starfininum = starfininum - 1 ";
            sql += "where planid = (select planid from BeanStep where stepid = ?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, step.getStepid());
            pst.execute();
            pst.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new DbException(e);
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
    public void modifyStep(BeanStep step, String planstartdate, String planfinishdate) throws BaseException {
        if (!"".equals(planstartdate))
            step.setPlanstardate(new DataboolUtil().Datebool(planstartdate, "计划开始时间"));
        if (!"".equals(planfinishdate))
            step.setPlanfinishdate(new DataboolUtil().Datebool(planfinishdate, "计划完成时间"));

        if (step.getPlanstardate().compareTo(step.getPlanfinishdate()) > 0)
            throw new BaseException("计划开始时间晚于计划完成时间");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "Select * from Beanstep where stepname = ? and planid = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, step.getStepname());
            pst.setInt(2, step.getPlanid());
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next() && step.getStepid() != (rs.getInt("stepid")))
                throw new BaseException("步骤名已存在");
            pst.close();
            rs.close();
            sql = "update Beanstep set stepname = ? ,planstartime = ? ,planfinishtime = ? where stepid = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, step.getStepname());
            pst.setDate(2, new Date(step.getPlanstardate().getTime()));
            pst.setDate(3, new Date(step.getPlanfinishdate().getTime()));
            pst.setInt(4, step.getStepid());
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
    }
}