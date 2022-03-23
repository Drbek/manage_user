package com.example.demo;



import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class DemoApplicationTests {


/* 	private MockMvc mockMvc; */
	
	@Test
    public void testSetOfCreateUser() throws Exception {
		
       /*  testCreateUser("bekono","roland","bekono@gmail.com",19,this.getfileMultiPartFile());
        testCreateUser("bekono","roland","bekono@gmail.com",19,this.getfileMultiPartFile());
        testCreateUser("bekono","roland","bekono@gmail.com",19,this.getfileMultiPartFile());
        testCreateUser("bekono","roland","bekono@gmail.com",19,this.getfileMultiPartFile());
        testCreateUser("bekono","roland","bekono@gmail.com",19,this.getfileMultiPartFile()); */
    }
	public void testCreateUser(String name,String surname,String email,Integer age/* ,MultipartFile file */) throws Exception {
	/* 	mockMvc.perform(MockMvcRequestBuilders.post("/users"))
                .param("name",name)
				.param("surname",surname)
				.param("email",email)
				.param("age",age)
				.param("file",file)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .andExpect(status().isOk())
                .andReturn(); */
		

	}
	void getfileMultiPartFile(){
		/* Path path = Paths.get(System.getProperty("user.dir")+"/src/main/resources/images/logo.jpg");
		String name = "logo.jpg";
		String originalFileName = "logo.jpg";
		String contentType = "images/jpg";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		MultipartFile result = new MockMultipartFile(name,
							originalFileName, contentType, content);
	    return result; */
	}

}
