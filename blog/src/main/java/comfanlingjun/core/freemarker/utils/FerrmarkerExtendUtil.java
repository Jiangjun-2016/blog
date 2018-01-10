package comfanlingjun.core.freemarker.utils;

import comfanlingjun.code.utils.LoggerUtils;
import comfanlingjun.code.utils.SpringContextUtil;
import comfanlingjun.code.utils.StringUtils;
import comfanlingjun.code.utils.UtilPath;
import comfanlingjun.core.freemarker.core.FreeMarkerExtendConfig;
import comfanlingjun.core.freemarker.customTag.CustomTemplateModelImpl;
import freemarker.template.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * freemarker 工具类
 */
public class FerrmarkerExtendUtil {

	private static Configuration cfg = null;
	public static Map<String, Object> initMap;
	//HTML输出目录
	public static String HTML_PATH = UtilPath.getHTMLPath();
	//FTL输入目录
	public static String FTL_PATH = UtilPath.getFTLPath();

	static {
		initMap = new LinkedHashMap<String, Object>();
		//1、创建Cfg
		cfg = new Configuration();
		//2、设置编码
		cfg.setLocale(Locale.getDefault());
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		//添加自定义标签
		CustomTemplateModelImpl api = SpringContextUtil.getBean("api", CustomTemplateModelImpl.class);
		cfg.setSharedVariable("api", api);

		FreeMarkerExtendConfig ext = SpringContextUtil.getBean("freemarkerConfig", FreeMarkerExtendConfig.class);

		Configuration vcfg = ext.getConfiguration();
		Set<String> keys = vcfg.getSharedVariableNames();
		for (String key : keys) {
			TemplateModel value = vcfg.getSharedVariable(key);
			cfg.setSharedVariable(key, value);
		}
		try {
			//添加shiro标签
			FreeMarkerExtendConfig.putShiroTag(cfg);
		} catch (TemplateModelException e) {
			LoggerUtils.fmtError(FerrmarkerExtendUtil.class, e, "添加Freemarker自定义标签失败!");
		}
	}


	/**
	 * @param path    模版路径
	 * @param inFile  模版文件
	 * @param outPath 输出html路径
	 * @param outFile 输出html NAME+后缀
	 * @param outMap  只是一个传值的对象，可以为空
	 */
	public void outHtml(String path, String inFile, String outPath, String outFile, Map<String, Object> outMap) throws IOException, TemplateException {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			//3、加载模板目录
			File filex = new File(path);
			cfg.setDirectoryForTemplateLoading(filex);
			//4、加载模板文件
			Template temp = cfg.getTemplate(inFile);
			//设置编码
			temp.setEncoding("UTF-8");
			//5、构建一个File对象输出 
			File file = new File(outPath + outFile);
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "UTF-8");
			bw = new BufferedWriter(osw);
			//6、准备数据模型
			// 模版方法模式,子类实现
			Map<String, Object> resultMap = doOutMap(outMap);
			resultMap.putAll(initMap);
			//7、调用Template对象的process方法来输出文件
			temp.process(resultMap, bw);
		} finally {
			try {
				if (bw != null) bw.flush();
				if (fos != null) fos.close();
				if (osw != null) osw.close();
				if (bw != null) bw.close();
			} catch (IOException e) {
				LoggerUtils.fmtError(FerrmarkerExtendUtil.class, e, "创建 [" + outFile + "] . io close exception");
			}
		}
	}

	/**
	 * 交给子类实现
	 */
	protected Map<String, Object> doOutMap(Map<String, Object> outMap) {
		return new HashMap<String, Object>();
	}

	/**
	 * 备份原来HTML
	 */
	public static void bakFile(String fileName) {
		if (StringUtils.isBlank(fileName))
			return;
		File[] files = UtilPath.getFiles(HTML_PATH);
		for (File file : files) {
			//备份原来文件
			if ((fileName).equals(file.getName())) {
				String newName = fileName + "-" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()) + ".html";
				String parentPath = file.getParent();
				File xfile = new File(parentPath + "/" + newName);
				if (xfile.exists()) {
					break;
				} else {
					file.renameTo(xfile);
					break;
				}
			}
		}
	}
}