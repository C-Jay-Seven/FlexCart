package com.potragis.FlexCart.config;

import com.potragis.FlexCart.model.entity.Client;
import com.potragis.FlexCart.model.entity.Role;
import com.potragis.FlexCart.model.enums.RoleName;
import com.potragis.FlexCart.repository.IClientRepository;
import com.potragis.FlexCart.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final IClientRepository clientRepository;
    private final IRoleRepository roleRepository;

    @Override
    public void run(String... args) {

        boolean adminExists = roleRepository.existsByRoleName(RoleName.ADMIN);
        if (adminExists) {
            return;
        }

        Client admin = new Client();
        admin.setUsername("admin");
        admin.setEmail("admin@flexcart.sys");
        admin.setPhoneNumber("00000000000");
        admin.setFirstName("System");
        admin.setLastName("Admin");
        admin.setPassword("password");
        clientRepository.save(admin);

        Role role = new Role();
        role.setRoleName(RoleName.ADMIN);
        role.setDescription("System administrator");
        role.setActive(true);
        role.setClient(admin);

        roleRepository.save(role);
    }

}
