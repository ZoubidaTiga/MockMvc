package com.example.restprojet;

import com.example.restprojet.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void contextLoads() {    }
    @Test
    public void shouldGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("sara"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("sara@gmail.com"));
    }
    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value("mouna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("mouna@gmail.com"));
    }
    @Test
    public void testDeleteUserById() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/users/{id}", "6")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }


    @Test
    public void testAddUser()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .content(asJsonString(new User (7L,"Zoubida","zoubida@gmail.com",23)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper ().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users")
                        .content(asJsonString(new User (7L,"nouhaila","noha@gmail.com",23)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void testGetUserById_thenTrowException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("user not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("uri=/api/users/6"));
    }
}