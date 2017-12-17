package cn.edu.zucc.personalplan.itf;


import cn.edu.zucc.personalplan.model.BeanUser;
import cn.edu.zucc.personalplan.util.BaseException;

@SuppressWarnings("JavadocReference")
public interface IUserManager {
	/**
	 * 注册： 要求用户名不能重复，不能为空 两次输入的密码必须一致，密码不能为空 如果注册失败，则抛出异常
	 * 
	 * @param userid
	 * @param pwd
	 *            密码
	 * @param pwd2
	 *            重复输入的密码
	 * @param maxlognum
	 * @return
	 * @throws BaseException
	 */
	public void reg(String userid, String pwd, String pwd2, int maxlognum) throws BaseException;

	/**
	 * 登陆 1、如果用户不存在或者密码错误，抛出一个异常 2、如果认证成功，则返回当前用户信息
	 * 
	 * @param userid
	 * @param pwd
	 * @return
	 * @throws BaseException
	 */
	public BeanUser login(String userid, String pwd) throws BaseException;

	/**
	 * 修改密码 如果没有成功修改，则抛出异常
	 * 
	 * @param user
	 *            当前用户
	 * @param oldPwd
	 *            原密码
	 * @param newPwd
	 *            新密码
	 * @param newPwd2
	 *            重复输入的新密码
	 */
	public void changePwd(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException;

	public void logout(BeanUser user) throws BaseException;

	public void setPwdquestion(BeanUser user, String pwdquestoin, String pwdanswer) throws BaseException;

	public void changePwdtoquestion(BeanUser user, String pwdanswer, String newPwd, String newPwd2)
			throws BaseException;

	public BeanUser load(String userid) throws BaseException;

	public void changePwdquestion(BeanUser user, String pwdquestoin, String pwdanswer, String oldpwdanswer)
			throws BaseException;
}
