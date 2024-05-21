package ai.maum.mcl.skins.api.manager.service;

import ai.maum.mcl.skins.api.manager.mapper.ManagerMapper;
import ai.maum.mcl.skins.api.manager.model.Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {
    private final PasswordEncoder passwordEncoder;
    private final ManagerMapper managerMapper;
    public Manager registManager(Manager manager) {
        String pwd = passwordEncoder.encode(manager.getSkinsPwd());
        manager.setSkinsPwd(pwd);
        managerMapper.insertManager(manager);
        return manager;
    }
}
