package comfanlingjun.code.controller.commons;

import comfanlingjun.code.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Map.Entry;

/**
 * 基础controler 设置默认值
 */
public class BaseController {

	public final static Logger logger = Logger.getLogger(BaseController.class);

	//默认Controller返回@ResponseBody对象用此Map装载
	public Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
	//默认显示第一页
	public static int pageNo = 1;
	//默认每页显示10条
	public static int pageSize = 10;
	//页面传入字符串 key
	public final static String PARAM_PAGE_NO = "pageNo";
	public final static String PAGE_SIZE_NAME = "pageSize";
	//跳转404页面
	public final static String URL404 = "/404.html";

	/**
	 * 跳转传入页面
	 */
	public ModelAndView redirect(String redirectUrl, Map<String, Object>... parament) {
		ModelAndView view = new ModelAndView(new RedirectView(redirectUrl));
		if (null != parament && parament.length > 0) {
			view.addAllObjects(parament[0]);
		}
		return view;
	}

	/**
	 * 跳转404页面
	 */
	public ModelAndView redirect404() {
		return new ModelAndView(new RedirectView(URL404));
	}

	/**
	 * 装载页面request传入参数
	 */
	protected Map<String, Object> prepareParams(Object obj, HttpServletRequest request) throws Exception {
		if (request != null) {
			String pageNoStr = (String) request.getParameter(PARAM_PAGE_NO);
			String pageSizeStr = (String) request.getParameter(PAGE_SIZE_NAME);
			if (StringUtils.isNotBlank(pageNoStr)) {
				pageNo = Integer.parseInt(pageNoStr);
			}
			if (StringUtils.isNotBlank(pageSizeStr)) {
				pageSize = Integer.parseInt(pageSizeStr);
			}
		}
		//传入参数 转 Map
		Map<String, Object> params = new HashMap<String, Object>();
		params = BeanUtils.describe(obj);
		params = handleParams(params);
		// 回填值项
		//BeanUtils.populate(obj, params);
		return params;
	}

	/**
	 * Request设置属性
	 */
	protected static void setValue2Request(HttpServletRequest request, String key, Object value) {
		request.setAttribute(key, value);
	}

	/**
	 * 获取session
	 */
	public static HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

	/**
	 * 页面request传入参数 转 Map
	 */
	private Map<String, Object> handleParams(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null != params) {
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Iterator<Entry<String, Object>> it = entrySet.iterator(); it.hasNext(); ) {
				Entry<String, Object> entry = it.next();
				if (entry.getValue() != null) {
					result.put(entry.getKey(), StringUtils.trimToEmpty((String) entry.getValue()));
				}
			}
		}
		return result;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		BaseController.pageSize = pageSize;
	}
}