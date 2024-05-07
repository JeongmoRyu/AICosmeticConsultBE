package ai.maum.mcl.skins.api.measure.mapper;

import ai.maum.mcl.skins.api.measure.model.MeasureInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MeasureMapper {
    public List<MeasureInfo> getMeasureInfoByUserKey(Long userKey);
}
