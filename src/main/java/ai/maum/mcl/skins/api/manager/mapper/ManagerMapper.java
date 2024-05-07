package ai.maum.mcl.skins.api.manager.mapper;

import ai.maum.mcl.skins.api.apiuser.model.ApiUser;
import ai.maum.mcl.skins.api.manager.model.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ManagerMapper {
    public Manager getManagerByManagerId(String managerId);
    public Manager getManagerById(String id);
    public void insertManager(Manager manager);
}