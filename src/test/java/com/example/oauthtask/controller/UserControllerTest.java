package com.example.oauthtask.controller;

import com.example.oauthtask.dto.EmployeeDTO;
import com.example.oauthtask.entity.User;
import com.example.oauthtask.service.EmployeeServiceImpl;
import com.example.oauthtask.service.UserService;
import com.example.oauthtask.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;



    @InjectMocks
    private UserController userController;



    @Test
    void testListUser() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("dummy");
        user1.setPassword("pass");
        user1.setRoles(new HashSet<>(Arrays.asList("ADMIN")));
        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("dummy");
        user2.setPassword("pass");
        user2.setRoles(new HashSet<>(Arrays.asList("ADMIN")));
        List<User> userList = Arrays.asList(user1, user2);
        when(userService.findAll()).thenReturn(userList);
        List<User> response = userController.listUser();
        assertNotNull(response);
        assertEquals(userList, response);

    }

    @Test
    @Order(1)
    @DisplayName("Test Should Create User")
    void testCreateUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("dummy1");
        user.setPassword("pass");
        user.setRoles(new HashSet<>(Arrays.asList("ADMIN")));
        when(userService.save(any(User.class))).thenReturn(user);
        User response = userController.create(user);
        assertNotNull(response);
        assertEquals(user, response);
    }

    @Test
    @Order(3)
    public void testDelete() throws Exception {

        Long id = 1L;
        String response = userController.delete(id);
        assertNotNull(response);
        assertEquals("success", response.toString());

    }




}