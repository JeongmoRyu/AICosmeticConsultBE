package ai.maum.mcl.skins.api.manager.service;

import ai.maum.mcl.skins.api.manager.mapper.ManagerMapper;
import ai.maum.mcl.skins.api.manager.model.Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerLoginService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final ManagerMapper managerMapper;

    @Override
    public UserDetails loadUserByUsername(String managerId) throws UsernameNotFoundException {
        Manager manager = managerMapper.getManagerByManagerId(managerId);
        return manager;
//        return new User(String.valueOf(manager.getId()), manager.getSkinsPwd(), new ArrayList<>());
    }
}
