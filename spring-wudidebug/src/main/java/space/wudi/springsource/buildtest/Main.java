package space.wudi.springsource.buildtest;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		TestBean testBeanFromAnnotation = (TestBean) annotationContext.getBean("testBean");
		System.out.println(testBeanFromAnnotation.getStr());
		System.out.println("--------------------");
		ClassPathXmlApplicationContext xmlContext = new ClassPathXmlApplicationContext("testBean.xml");
		TestBean testBeanFromXml = (TestBean) xmlContext.getBean("testBean");
		System.out.println(testBeanFromXml.getStr());
	}
}
