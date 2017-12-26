package comfanlingjun.commons.controller;


import comfanlingjun.commons.utils.LoggerUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Scope(value = "prototype")
@RequestMapping("open")
public class CommonController extends BaseController {

	/**
	 * 404错误
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("404")
	public ModelAndView _404(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("common/404");
		return view;
	}

	/**
	 * 500错误
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("500")
	public ModelAndView _500(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("common/500");
		Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");
		String defaultMessage = "未知";
		if (null == t) {
			view.addObject("line", defaultMessage);
			view.addObject("clazz", defaultMessage);
			view.addObject("methodName", defaultMessage);
			return view;
		}
		String message = t.getMessage();//错误信息
		StackTraceElement[] stack = t.getStackTrace();
		view.addObject("message", message);
		if (null != stack && stack.length != 0) {
			StackTraceElement element = stack[0];
			int line = element.getLineNumber();//错误行号
			String clazz = element.getClassName();//错误java类
			String fileName = element.getFileName();
			String methodName = element.getMethodName();//错误方法
			view.addObject("line", line);
			view.addObject("clazz", clazz);
			view.addObject("methodName", methodName);
			LoggerUtils.fmtError(getClass(), "line:%s,clazz:%s,fileName:%s,methodName:%s()",
					line, clazz, fileName, methodName);
		}
		return view;
	}
}
