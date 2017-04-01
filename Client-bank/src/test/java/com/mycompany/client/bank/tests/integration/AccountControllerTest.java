package com.mycompany.client.bank.tests.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.client.bank.api.LoginReply;
import com.mycompany.client.bank.api.PostRequstLibAuthorization;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccountControllerTest {
	
	public final static String AUTH_HTTP_HEADER ="X-Authorization";
	
    private static String token = null;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Before
    public void login() throws Exception {
        if(token!=null){
            return;
        }
        PostRequstLibAuthorization rq= new PostRequstLibAuthorization();
        rq.login = "artem7902";
        rq.password = "335790";
        ObjectMapper om = new ObjectMapper();
        String content = om.writeValueAsString(rq);
        MvcResult result = mockMvc.perform(post("/auth")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
        )
                .andExpect(status().isOk())
                .andReturn();
        String reply = result.getResponse().getContentAsString();
        LoginReply lr = om.readValue(reply, LoginReply.class);
        token = lr.token;
    }
    
    @Test
    public void testAccountInfoAndTransactions() {
    	try {
			this.mockMvc.perform(get("/account=1")
			        .header(AUTH_HTTP_HEADER, token))
					.andDo(print()).andExpect(status().isOk())
					.andExpect(content().string(containsString("Sveta Novikova")));
		} catch (Exception e) {
			fail("Exception while getting account info");
		}
    }
}
