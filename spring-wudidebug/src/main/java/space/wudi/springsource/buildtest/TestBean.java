package space.wudi.springsource.buildtest;


import org.springframework.stereotype.Component;

@Component
public class TestBean {
	private String str = "TestBeanStr";
	{
		Exception e = new Exception();
		StackTraceElement[] stackTrace = e.getStackTrace();
		String className = stackTrace[stackTrace.length-2].getClassName();
		System.out.println("init by "+className);
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
