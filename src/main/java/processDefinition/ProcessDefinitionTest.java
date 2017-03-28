package processDefinition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/** 
* @author 作者tkrwy: 
* @version 创建时间：2017年3月1日 上午11:17:34 
* 类说明 
*/
public class ProcessDefinitionTest {
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
	
	
	/**部署流程文件（从zip）*/
	@Test
	public void deployProcessDefinitionByZip(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/leaveProcess.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService() //与流程定义和部署对象相关的实例
					 .createDeployment() //创建一个部署对象
					 .name("从zip部署请假流程")
					 .addZipInputStream(zipInputStream)
					 .deploy();
		
		System.out.println("部署ID" + deployment.getId());
		System.out.println("部署名称" + deployment.getName());
					 
	}
	
	/**查询流程定义*/
	@Test
	public void findProcessDefinition(){
		long count = processEngine.getRepositoryService()
					 .createProcessDefinitionQuery()
					 /**指定查询条件*/
//					 .deploymentId(deploymentId)
					 //.processDefinitionId(processDefinitionId)
					 //.processDefinitionKey(processDefinitionKey)
					 //.processDefinitionNameLike(processDefinitionNameLike)
					 
					 /**排序*/
					 .orderByDeploymentId().asc()
					 /**返回结果集*/
					 //.list()
					 //.singleResult()
					 
					 //.listPage(firstResult, maxResults)
					 .count();
		System.out.println("processDefinition count:" + count);
					 
	}
	
	/**删除流程定义*/
	@Test
	public void deleteProcessDefinition(){
		String deploymentId = "1";
		processEngine.getRepositoryService()
					 .deleteDeployment(deploymentId);
		System.out.println("删除成功!");
	}
	
	/**查看流程图
	 * @throws IOException */
	@Test
	public void viewPic(){
		String deploymentId = "42501";
		
		/**获取图片资源名称*/
		List<String> list = processEngine.getRepositoryService()
					 .getDeploymentResourceNames(deploymentId);
		String resourceName = "";
		if(list != null && list.size() > 0){
			for(String name:list){
				if(name.indexOf(".png")>=0){
					resourceName = name;
				}
			}
		}
		/**获取图片的输入流*/
		InputStream in = processEngine.getRepositoryService()
					 .getResourceAsStream(deploymentId, resourceName);
		File file = new File("E:/" + resourceName);
		
		try {
			OutputStream out = new FileOutputStream(file);
			
			/**创建一个竹筒*/
			byte[] bbuf = new byte[1024];
			int hasread = 0;
			while((hasread = in.read(bbuf)) > 0){
				out.write(bbuf, 0, hasread);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
