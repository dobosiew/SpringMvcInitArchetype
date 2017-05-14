package ${package}.domain.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    public AppUser getAppUserById(Long userId);

    @Query("SELECT user FROM AppUser user WHERE user.username = ?1")
    public AppUser getAppUserByUsername(String username);
}
