package cn.edu.zucc.personalplan.util;

public class DbException extends BaseException {
	public DbException(Throwable ex){
		super("数据库操作错误："+ex.getMessage());
	}
}
