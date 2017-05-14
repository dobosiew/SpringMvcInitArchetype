package ${package}.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HomeControllerIT {


    private MockMvc mockMvc;

    private HomeController homeController;

    @Before
    public void setUp() {
        setUpHomeController();
        setUpMockMvc();
    }

    private void setUpHomeController() {
        homeController = new HomeController();
    }

    private void setUpMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void testMainPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("text", containsString("Hello World")))
                .andExpect(view().name("index"));
    }
}
