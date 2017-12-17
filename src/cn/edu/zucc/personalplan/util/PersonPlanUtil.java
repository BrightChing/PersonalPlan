package cn.edu.zucc.personalplan.util;


import cn.edu.zucc.personalplan.control.PlanManager;
import cn.edu.zucc.personalplan.control.StepManager;
import cn.edu.zucc.personalplan.control.UserManager;
import cn.edu.zucc.personalplan.itf.IPlanManager;
import cn.edu.zucc.personalplan.itf.IStepManager;
import cn.edu.zucc.personalplan.itf.IUserManager;

public class PersonPlanUtil {
    public static IPlanManager planManager = new PlanManager();//需要换成自行设计的实现类
    public static IStepManager stepManager = new StepManager();//需要换成自行设计的实现类
    public static IUserManager userManager = new UserManager();//需要换成自行设计的实现类

}
