
package com.callcenter.controller;
import com.callcenter.domain.Dic;
import com.callcenter.domain.common.Message;
import com.callcenter.domain.common.Page;
import com.callcenter.service.DicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.util.Date;

/**
 *dic controller层
 * @author wangyue-ds6
 * @since 2014-02-28
 */
@Controller
@RequestMapping(value = "/dic")
public class DicController{
	private static final Logger LOGGER = LoggerFactory.getLogger(DicController.class);
	@Resource private DicService dicService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		//binder.registerCustomEditor(Date.class, new CustomDateEditor(true));
	}
	
	/**
	 * 列表展示
	 * @param dic 实体对象
	 * @param page 分页对象
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String list(Dic dic,Page<Dic> page,Model view) throws Exception{
		try {
			view.addAttribute("dic",dic);
			view.addAttribute("page",dicService.selectPage(dic,page));			
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}	
		return "dic/list";
	}
	
	/**
	 * 响应新增(修改)页面
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String edit(@PathVariable Long id,Model view) throws Exception{
		try {
			if(id != null && id > 0) {
				Dic dic = dicService.selectEntry(id);
				if(dic == null) {
//					return toJSON(Message.failure("您要修改的数据不存在或者已被删除!"));
					return null;
				}
				view.addAttribute("dic",dic);
			}			
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "dic/edit";
	}
	
	/**
	 * 通过编号删除对象
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public @ResponseBody Message del(@PathVariable Long id,Model view) throws Exception{
    	Message msg = null;
    	try {
			int res = dicService.deleteByKey(id);
			msg  = res > 0 ? Message.success() : Message.failure();
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			msg = Message.failure();
		}finally{
		}

		return msg;
	}
	
	/**
	 * 通过编号查看对象
	 * @param id 对象编号
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String view(@PathVariable Long id,Model view) throws Exception{
		try {
			Dic dic = dicService.selectEntry(id);
			if(dic == null) {
				return null;
			}
			view.addAttribute("dic",dic);
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "dic/view";
	}
	
	/**
	 * 保存方法
	 * @param dic 实体对象
	 * @return
	 */
	@RequestMapping(value="/save",method = {RequestMethod.POST,RequestMethod.GET},produces="application/json")
	public @ResponseBody Message save(Dic dic,Model view) throws Exception{
    	Message msg= null;
    	try {
			int res = dicService.saveOrUpdate(dic);
			msg  = res > 0 ? Message.success() : Message.failure();
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			msg = Message.failure();
		}finally{
		}
		return msg;
	}
	
}