package com.niit.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer
{
	   public  WebAppInitializer() {
		      System.out.println("WEBAPPINITIALIZER is Instantiated");
		}
			@Override
			protected Class<?>[] getRootConfigClasses() {
				return new Class[]{DBConfiguration.class};
			}

			@Override
			protected Class<?>[] getServletConfigClasses() {
				return new Class[]{WebConfig.class};
			}

			@Override
			protected String[] getServletMappings() {
				return new String[]{"/"};
			}

}
