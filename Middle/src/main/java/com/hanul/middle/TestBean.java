package com.hanul.middle;

import org.springframework.stereotype.Component;

// null x =>메모리에 올리는 방법 

@Component(value = "bean")
public class TestBean {
	public TestBean() {
		System.out.println("@Autowired가 되었다. 나는 null 아니다.");
	}
}
