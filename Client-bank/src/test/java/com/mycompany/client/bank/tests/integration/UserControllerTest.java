package com.mycompany.client.bank.tests.integration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alex
 */
import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.client.bank.api.AddUserRequest;
import com.mycompany.client.bank.api.LibAppUserAndUserDetails;
import com.mycompany.client.bank.api.LibAppUserAndUserDetailsReply;
import com.mycompany.client.bank.api.LoginReply;
import com.mycompany.client.bank.api.PostRequstLibAuthorization;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 *
 * @author al
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
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
    public void findUserTest() throws Exception {
        this.mockMvc.perform(get("/users/byid/1")
                               .header(AUTH_HTTP_HEADER, token)
                 )
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("admin")));
    }

    @Test
    public void addUserTest() throws Exception {
        AddUserRequest rq = new AddUserRequest();
        rq.user = new LibAppUserAndUserDetails();
        rq.user.firstName = "Test1First";
        rq.user.lastName = "Test1Last";
        rq.user.login = "test_user_1";
        rq.user.password = "qwerty";
        rq.user.email = "test@test.com";
	rq.user.adress ="Puhova";
		rq.user.wallet=0D;
		rq.user.phone="432432";
		rq.user.role_id=1L;

        ObjectMapper om = new ObjectMapper();
        String content = om.writeValueAsString(rq);

        MvcResult result = mockMvc.perform(post("/users/add")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(AUTH_HTTP_HEADER, token)
                .content(content)
        )
                .andExpect(status().isOk())
                .andReturn();

        String reply = result.getResponse().getContentAsString();
        LibAppUserAndUserDetailsReply ur = om.readValue(reply, LibAppUserAndUserDetailsReply.class);
        assertEquals("Return code in not 0", 0L, ur.retcode.longValue());
        /*if (ur.retcode == 0) {
            mockMvc.perform(get("/users/del/" + ur.users.get(0).user_id)
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .header(AUTH_HTTP_HEADER, token)
            )
                    .andExpect(status().isOk());

        }*/
    }
}
