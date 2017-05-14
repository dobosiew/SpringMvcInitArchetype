package ${package}.security;

import ${package}.domain.appuser.AppUser;
import ${package}.domain.appuser.AppUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class AppUserServiceTest {
    private final String USERNAME = "username";

    @InjectMocks
    AppUserService appUserService;

    @Mock
    AppUserRepository appUserRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void usernameNotFoundException() {
        appUserService.loadUserByUsername(USERNAME);
    }

    @Test
    public void foundUserName() {
        when(appUserRepository.getAppUserByUsername(anyString())).thenReturn(new AppUser());
        assertThat(appUserService.loadUserByUsername(USERNAME)).isNotNull();
    }

}
