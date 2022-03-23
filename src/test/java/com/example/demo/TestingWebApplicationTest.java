package com.example.demo;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getAllUsersShouldReturnokStatus() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/users/")).andDo(print()).andExpect(status().isOk());
	}
    @Test
	public void createUserShouldReturnokStatus() throws Exception {
         MockMultipartFile file=this.getfileMultiPartFile();
         String name=new String("CEDRIC");
         String surname=new String("CEDRIC");
         String email=new String("CEDRIC@gmail.com");
         String age="30";
		this.mockMvc.perform(multipart("http://localhost:8080/api/users/")
        .file(file)
        .param("name",name)
        .param("surname",surname)
        .param("email",email)
        .param("age",age)
        )
        .andDo(print()).andExpect(status().isOk());
	}
   //return the Mock file for request
    MockMultipartFile getfileMultiPartFile(){
		Path path = Paths.get(System.getProperty("user.dir")+"/src/main/resources/images/logo.jpg");
		String name = "file";
		String originalFileName = "logo.jpg";
		String contentType = "image/jpeg";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		MockMultipartFile result = new MockMultipartFile(name,
							originalFileName, contentType, content);
	    return result; 
	}
}