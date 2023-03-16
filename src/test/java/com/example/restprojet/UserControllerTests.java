package com.example.restprojet;

import com.example.restprojet.controller.UserController;
import com.example.restprojet.exceptions.UserNotFoundException;
import com.example.restprojet.model.User;
import com.example.restprojet.service.IServiceUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private IServiceUser userService;

    @Test
    public void contextLoads() throws Exception{

    }
    @Test
    public void testUserList() throws Exception {
        User user1 =new User(1L, "saad", "saad@mail.com",19);
        User user2 = new User(2L, "ilyass", "ilyass@mail.com",23);
        List<User> userList = Arrays.asList(user1, user2);
        Mockito.when(userService.getAllUsers()).thenReturn(userList);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nom").value("saad"));
    }

    @Test
    public void testGetUser() throws Exception {
        User user =new User(1L, "saad", "saad@mail.com",19);
        Mockito.when(userService.getUserById(1L)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nom").value("saad"))
                .andExpect(jsonPath("$.email").value("saad@mail.com"));
    }

    @Test
    public void testSaveUser() throws Exception {
        User user =new User(9L, "zoubida", "zou@mail.com",23);
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect( jsonPath("$.nom").value("zoubida"))
                .andExpect( jsonPath("$.email").value("zou@mail.com"));
        verify(userService, times(1)).createUser(user);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user =new User(9L, "zoubida", "zou@mail.com",23);
        Mockito.when(userService.update(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect( jsonPath("$.nom").value("zoubida"))
                .andExpect( jsonPath("$.email").value("zou@mail.com"));
        verify(userService, times(1)).update(user);

    }

    @Test
    public void testGetUserById_thenThrowException() throws Exception {
        Mockito.when(userService.getUserById(6L)).thenThrow(new UserNotFoundException ("user not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("user not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("uri=/api/users/6"));
    }



}