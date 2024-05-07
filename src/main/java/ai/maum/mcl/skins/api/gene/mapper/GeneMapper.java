package ai.maum.mcl.skins.api.gene.mapper;

import ai.maum.mcl.skins.api.gene.model.GeneInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GeneMapper {
    public List<GeneInfo> getGeneInfoByUserKey(Long userKey);
}
