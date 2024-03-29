package com.my.pro.action;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.my.pro.utils.Pager;
import com.opensymphony.xwork2.ModelDriven;
import java.util.*;

import com.my.pro.model.*;
import com.my.pro.dao.*;
import com.my.pro.service.*;

/**
 * @ClassName:  
 * @Description: 
 * @author administrator
 * 
 */

@Controller("teacherAction")
@Scope("prototype")
public class TeacherAction extends BaseAction implements ModelDriven<Teacher>{
	
	private static final long serialVersionUID = 1L;


	
	//==========model==============
	  private Teacher teacher;
		@Override
		public Teacher getModel() {
			if(teacher==null) teacher = new Teacher();
			return teacher;	
		}
		//==========model==============
	/**
	 * 依赖注入 start dao/service/===
	 */
	@Autowired
	private TeacherService teacherService;
	
	//依赖注入 end  dao/service/===
	
	//-------------------------华丽分割线---------------------------------------------
	
	//============自定义参数start=============
	
	//============自定义参数end=============

	
	//-------------------------华丽分割线---------------------------------------------
	
	//============文件上传start=======================================================
	@Autowired
	private XyService xyService;
	
	private File file;
	//提交过来的file的名字
    private String fileFileName;
    //提交过来的file的MIME类型
    private String fileContentType;
    public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	 //============文件上传end=========================================================
	public String jsonAction() {
		  // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		  jsonMap.clear();
		  jsonMap.put("success", true);
		  return JSON_TYPE;
	}
	 //-------------------------华丽分割线---------------------------------------------//
	
	 //=============公=======共=======方=======法==========区=========start============//
	/**
	 * 列表分页查询
	 */
	public String teacher(){
	    Map<String,Object> alias = new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from Teacher where 1=1 and isDelete = 0 ");
		if(teacher!=null && teacher.getRealName() !=null && !"".equals(teacher.getRealName())){
			sb.append("  and realName like :realName ");
			alias.put("realName", "%" +teacher.getRealName()+ "%" );
		}
		sb = sb.append("order by id desc");
		Pager<Teacher> pagers = teacherService.findByAlias(sb.toString(),alias);
		ActionContext.getContext().put("pagers", pagers);
		ActionContext.getContext().put("Obj", teacher);
		return SUCCESS;
    }
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	public String add(){
		List<Xy> listByAlias = xyService.listByAlias("from Xy where 1=1 and isDelete = 0", null);
		ActionContext.getContext().put("xys", listByAlias);
		return SUCCESS;
	}
	
	/**
	 * 执行添加
	 * @return
	 */
	public String exAdd(){
		teacher.setIsDelete(0);
		teacherService.save(teacher);
		ActionContext.getContext().put("url", "/teacher_teacher.do");
		return "redirect";
	}
	
	/**
	 * 查看详情页面
	 * @return
	 */
	public String view(){
		Teacher n = teacherService.getById(teacher.getId());
		ActionContext.getContext().put("Obj", n);
		return SUCCESS;
	}
	
	/**
	 * 跳转修改页面
	 * @return
	 */
	public String update(){
		List<Xy> listByAlias = xyService.listByAlias("from Xy where 1=1 and isDelete = 0", null);
		ActionContext.getContext().put("xys", listByAlias);
		Teacher n = teacherService.getById(teacher.getId());
		ActionContext.getContext().put("Obj", n);
		return SUCCESS;
	}
    
	/**
	 * 执行修改
	 * @return
	 */
	public String exUpdate(){
		Teacher n = teacherService.getById(teacher.getId());
		n.setPassWord(teacher.getPassWord());
		n.setPhone(teacher.getPhone());
		n.setRealName(teacher.getRealName());
		n.setUserName(teacher.getUserName());
		n.setXy(teacher.getXy());
		n.setSex(teacher.getSex());
		teacherService.update(n);
		ActionContext.getContext().put("url", "/teacher_teacher.do");
		return "redirect";
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		Teacher n = teacherService.getById(teacher.getId());
		n.setIsDelete(1);
		teacherService.update(n);
		ActionContext.getContext().put("url", "/teacher_teacher.do");
		return "redirect";
	}
	
	//=============公=======共=======方=======法==========区=========end============//
	
	 //-------------------------华丽分割线---------------------------------------------//
	
	 //=============自=======定=======义=========方=======法==========区=========start============//
	
	
	
	
	//=============自=======定=======义=========方=======法==========区=========end============//
		
	
	
}
