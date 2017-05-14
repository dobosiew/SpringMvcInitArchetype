package ${package}.configuration;

import ${package}.domain.appdata.AppDataRepository;
import ${package}.domain.appuser.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockDBConfiguration {

    @Bean
    public AppDataRepository getAppDataRepository() {
        return org.mockito.Mockito.mock(AppDataRepository.class);
    }

    @Bean
    public AppUserRepository getAppUserRepository(){
        return org.mockito.Mockito.mock(AppUserRepository.class);
    }
}
