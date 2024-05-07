package ai.maum.mcl.skins.api.temptest.mapper;

import ai.maum.mcl.skins.api.temptest.model.TempTestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TempTestMapper {
    public List<TempTestInfo> getTempTestInfoByUser(Long userKey);
}
