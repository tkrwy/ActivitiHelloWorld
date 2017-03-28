package helloworld;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/** 
* @author 作者tkrwy: 
* @version 创建时间：2017年2月28日 下午5:48:18 
* 类说明 
*/
public class HelloWorld {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deployProcessDefinition(){
		Deployment deployment = processEngine.getRepositoryService() //与流程定义和部署对象相关的实例
					 .createDeployment() //创建一个部署对象
					 .name("部署请假流程")
					 .addClasspathResource("diagrams/helloworld.bpmn")
					 .addClasspathResource("diagrams/helloworld.png")
					 .deploy();
		
		System.out.println("部署ID" + deployment.getId());
		System.out.println("部署名称" + deployment.getName());
					 
	}
	
	
	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcess(){
		String processDefinitionKey = "helloWorld";
		ProcessInstance pi = processEngine.getRuntimeService() //与正在执行的流程实例和执行对象相关的service
					 					  .startProcessInstanceByKey(processDefinitionKey); //使用流程定义的key启动流程实例，key对应于流程文件中的id的属性值
		
		System.out.println("流程实例ID"+pi.getId());
		System.out.println("流程定义"+pi.getProcessDefinitionId());
	}
	
	/**查找当前人的个人任务*/
	@Test
	public void findMyTasks(){
		String assignee = "刘彤";
		List<Task> list = processEngine.getTaskService()
					 .createTaskQuery()
					 .taskAssignee(assignee)
					 .list();
		if(list != null && list.size() > 0){
			for(Task task: list){
				System.out.println(task.getId());
				System.out.println(task.getName());
				System.out.println(task.getAssignee());
				System.out.println(task.getCreateTime());
				System.out.println("流程实例ID:" + task.getProcessInstanceId());
				System.out.println("执行对象ID:" + task.getExecutionId());
				System.out.println("流程定义ID:" + task.getProcessDefinitionId());
			}
		}
	}
	
	/**完成我的任务*/
	@Test
	public void completeMyTask(){
		String taskId = "7502";
		processEngine.getTaskService()
					 .complete(taskId);
		
		System.out.println("完成任务：任务ID" + taskId);
	}
	
}
