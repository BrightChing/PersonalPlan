package cn.edu.zucc.personalplan.itf;

import cn.edu.zucc.personalplan.model.BeanPlan;
import cn.edu.zucc.personalplan.model.BeanStep;
import cn.edu.zucc.personalplan.util.BaseException;
import java.util.List;

public interface IStepManager {
	/**
	 * 添加步骤
	 * 
	 * @param plan
	 * @param name
	 * @param planstartdate
	 * @param planfinishdate
	 * @throws BaseException
	 */
	public void add(BeanPlan plan, String name, String planstartdate, String planfinishdate)throws BaseException;

	public List<BeanStep> loadSteps(BeanPlan plan)throws BaseException;

	public void deleteStep(BeanStep step)throws BaseException;

	public void startStep(BeanStep step) throws BaseException;

	public void finishStep(BeanStep step)throws BaseException;

	public	List<BeanStep> loadSteps(BeanPlan plan, Boolean flag) throws BaseException;

	public void modifyStep(BeanStep step, String planstartdate, String planfinishdate) throws BaseException;

	


}
