package ${package}.domain;

import ${package}.configuration.DataBaseITConfiguration;
import ${package}.domain.appdata.AppDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("integration-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DataBaseITConfiguration.class
})
public class AppDataRepositoryIT {

    @Autowired
    AppDataRepository appDataRepository;


    @Test
    public void test(){
        assertThat(appDataRepository.complicatedQuery())
                .isPositive();
    }

}
