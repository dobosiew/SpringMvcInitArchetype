package ${package}.controllers;

import ${package}.configuration.MockDBConfiguration;
import ${package}.configuration.SpringSecurityConfiguration;
import ${package}.domain.appdata.AppData;
import ${package}.domain.appdata.AppDataRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        SpringSecurityConfiguration.class,
        MockDBConfiguration.class,
})
@WebAppConfiguration
public class DataControllerIT {

    private MockMvc mockMvc;

    @Autowired
    AppDataRepository appDataRepository;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    private AppDataController dataController;

    @Before
    public void setUp() {
        setUpAppDataRepository();
        setUpDataController();
        setUpMockMvc();
    }

    private void setUpAppDataRepository() {
        when(appDataRepository.getAppDataById(anyLong()))
                .thenReturn(new AppData());
    }

    private void setUpMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(dataController)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    private void setUpDataController() {
        dataController = new AppDataController(appDataRepository);
    }

    @Test
    public void getDataNotAuthorize() throws Exception {
        mockMvc.perform(get("/data/1"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser
    public void getDataAuthorize() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/data/1"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andExpect(request().asyncResult(instanceOf(AppData.class)))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("id")));
    }
}
