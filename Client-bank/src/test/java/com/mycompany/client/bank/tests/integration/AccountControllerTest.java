package com.mycompany.client.bank.tests.integration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.client.bank.api.LibTransactionReply;
import com.mycompany.client.bank.api.LoginReply;
import com.mycompany.client.bank.api.PostRequstLibAuthorization;
import com.mycompany.client.bank.api.TransferRequest;

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
        rq.login = "admin";
        rq.password = "qwerty";
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
					.andExpect(content().string(containsString("\"credit_limit\":2000")))
					.andExpect(content().string(containsString("\"userId\":\"1\"")))
					.andExpect(content().string(containsString("\"bankId\":\"1\"")));
		} catch (Exception e) {
			fail("Exception while getting account info");
		}
    }
    
    @Test
    public void testTransfer() {
    	TransferRequest tr = new TransferRequest();
    	tr.toAccountId = "5";
    	tr.value = "100";
    	
    	ObjectMapper om = new ObjectMapper();
    	String content = "";
        try {
			content = om.writeValueAsString(tr);
		} catch (JsonProcessingException e) {
			fail("Can't turn transfer req to json");
		}
        MvcResult result = null;
        try {
			result = mockMvc.perform(post("/1/transfer")
			        .accept(MediaType.APPLICATION_JSON_UTF8)
			        .contentType(MediaType.APPLICATION_JSON_UTF8)
			        .header(AUTH_HTTP_HEADER, token)
			        .content(content))
			        .andExpect(status().isOk())
			        .andReturn();
		} catch (Exception e) {
			fail("Fail while performing post transfer req");
		}
        String reply = "";
		try {
			reply = result.getResponse().getContentAsString();
		} catch (UnsupportedEncodingException e) {
			fail("Exception while reading response");
		}
		LibTransactionReply ltr = null;
        try {
			ltr = om.readValue(reply, LibTransactionReply.class);
		} catch (IOException e) {
			fail("Fail while converting json to lib reply");
		}
        assertEquals("retcode is not 0", 0L, ltr.retcode.longValue());
    }
}
