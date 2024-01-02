package controller;

import static org.mockito.Mockito.when;

import com.example.Dinner.Review.API.controller.userController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Dinner.Review.API.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.Dinner.Review.API.repository.UserRepositery;

@ContextConfiguration(classes = {userController.class})
@ExtendWith(SpringExtension.class)
class userControllerDiffblueTest {
    @Autowired
    private userController userController;

    @MockBean
    private UserRepositery userRepositery;

    /**
     * Method under test: {@link userController#createUser(User)}
     */
    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setCity("Oxford");
        user.setId(1L);
        user.setIsDairyAllergic(true);
        user.setIsEggAllergic(true);
        user.setIsPeanutAllergic(true);
        user.setName("Name");
        user.setState("MD");
        user.setZipcode("21654");
        when(userRepositery.existsUserByName(Mockito.<String>any())).thenReturn(true);
        when(userRepositery.save(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setCity("Oxford");
        user2.setId(1L);
        user2.setIsDairyAllergic(true);
        user2.setIsEggAllergic(true);
        user2.setIsPeanutAllergic(true);
        user2.setName("Name");
        user2.setState("MD");
        user2.setZipcode("21654");
        String content = (new ObjectMapper()).writeValueAsString(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/new/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(409));
    }

    /**
     * Method under test: {@link userController#createUser(User)}
     */
    @Test
    void testCreateUser2() throws Exception {
        User user = new User();
        user.setCity("Oxford");
        user.setId(1L);
        user.setIsDairyAllergic(true);
        user.setIsEggAllergic(true);
        user.setIsPeanutAllergic(true);
        user.setName("Name");
        user.setState("MD");
        user.setZipcode("21654");
        when(userRepositery.existsUserByName(Mockito.<String>any())).thenReturn(false);
        when(userRepositery.save(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setCity("Oxford");
        user2.setId(1L);
        user2.setIsDairyAllergic(true);
        user2.setIsEggAllergic(true);
        user2.setIsPeanutAllergic(true);
        user2.setName("Name");
        user2.setState("MD");
        user2.setZipcode("21654");
        String content = (new ObjectMapper()).writeValueAsString(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/new/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"city\":\"Oxford\",\"state\":\"MD\",\"zipcode\":\"21654\",\"isPeanutAllergic\":true,\"isEggAllergic"
                                        + "\":true,\"isDairyAllergic\":true}"));
    }
}
