/**
 * 
 */
package us.jyni.frame.util;

import javax.servlet.DispatcherType;

import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.servlet.View;

/**
 * @author jynius
 *
 */
public class MockRenderer {

	public static String render(String viewName, Model model) throws Exception {
		MockRenderer renderer = new MockRenderer(viewName, model);
		return renderer.render();
	}
	
	private MockHttpServletRequest request;
	
	private MockHttpServletResponse response;
	
	private String viewName;
	
	private Model model;
	
	public MockRenderer() {}
	
	public MockRenderer(String viewName, Model model) {
		this.viewName = viewName;
		this.model = model;
	}
	
	public String render() throws Exception {
		
		View view = ApplicationContextProvider.getView(viewName);
		view.render(model.asMap(), getRequest(), getResponse());
		
		return getResponse().getContentAsString();
	}

	private MockHttpServletRequest getRequest() {
		
		if(request==null) {

			request = new MockHttpServletRequest() {
				
				{
					setMethod(HttpMethod.GET.name());
				}
				
				@Override
				public DispatcherType getDispatcherType() {
					return DispatcherType.INCLUDE;
				}
				
				@Override
				public boolean isAsyncSupported() {
					return false;
				}
			};
		}
		
		return request;
	}
	
	private MockHttpServletResponse getResponse() {
		
		if(response==null) {
			response = new MockHttpServletResponse(); 
		}
		
		return response;
	}
}
