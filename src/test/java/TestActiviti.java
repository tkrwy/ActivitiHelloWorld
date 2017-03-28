import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/** 
* @author 作者tkrwy: 
* @version 创建时间：2017年2月28日 下午4:40:59 
* 类说明 
*/
public class TestActiviti {

	/**
	 * 使用配置文件创建23张表
	 */
	@Test
	public void createTable(){
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine processEngine =processEngineConfiguration.buildProcessEngine();
		
		System.out.println(processEngine);
	}
}
