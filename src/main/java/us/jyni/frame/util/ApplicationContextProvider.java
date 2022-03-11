/**
 * 
 */
package us.jyni.frame.util;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * @author jynius
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContextProvider instance;

	private ApplicationContext applicationContext;

	/**
	 * 
	 */
	private ApplicationContextProvider() {}
	
	private static ApplicationContextProvider getInstance() {
		
		if(instance==null) {
			instance = new ApplicationContextProvider();
		}
		
		return instance;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		getInstance().applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		return getInstance().applicationContext;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	
	public static Object getBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}

	public static ViewResolver getViewResolver() {
		return getApplicationContext().getBean(ViewResolver.class);
	}

	public static View getView(String viewName) throws Exception {
		return getViewResolver().resolveViewName(viewName, Locale.KOREAN);
	}
}
