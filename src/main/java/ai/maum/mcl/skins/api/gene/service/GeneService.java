package ai.maum.mcl.skins.api.gene.service;

import ai.maum.mcl.skins.api.gene.mapper.GeneMapper;
import ai.maum.mcl.skins.api.gene.model.GeneInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneService {
    private final GeneMapper geneMapper;
    public List<GeneInfo> getGeneInfoByUserKey(Long userKey) {
        return geneMapper.getGeneInfoByUserKey(userKey);
    }
}
