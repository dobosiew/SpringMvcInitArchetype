package ${package}.domain.appdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AppDataRepository extends JpaRepository<AppData, Long>, AppDataInterface {

    public AppData getAppDataById(Long appDataId);
}
