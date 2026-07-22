package com.refresh_token.auth.bootstrap;

import com.refresh_token.auth.entity.Permission;
import com.refresh_token.auth.entity.Role;
import com.refresh_token.auth.repository.PermissionRepository;
import com.refresh_token.auth.repository.RoleRepository;
import com.refresh_token.auth.repository.UserRepository;
import com.refresh_token.common.constants.PermissionNames;
import com.refresh_token.common.constants.RoleNames;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String @NonNull ... args) throws Exception {

        Permission profileRead = createPermissionIfNotExists(PermissionNames.PROFILE_READ);
        Permission createTask = createPermissionIfNotExists(PermissionNames.CREATE_TASK);
        Permission getAllTask = createPermissionIfNotExists(PermissionNames.GET_ALL_TASK);
        Permission getTaskByName = createPermissionIfNotExists(PermissionNames.GET_TASK_BY_NAME);

        Role userRole = createRoleIfNotExists(RoleNames.USER);

        assignPermission(userRole, profileRead);
        assignPermission(userRole, createTask);
        assignPermission(userRole, getAllTask);
        assignPermission(userRole, getTaskByName);
    }

    private Role createRoleIfNotExists(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() ->
                        roleRepository.save(
                                Role.builder()
                                        .name(roleName)
                                        .build()
                        )
                );
    }

    private Permission createPermissionIfNotExists(String permissionName) {
        return permissionRepository.findByName(permissionName)
                .orElseGet(() ->
                        permissionRepository.save(
                                Permission.builder()
                                        .name(permissionName)
                                        .build()
                        )
                );
    }

    private void assignPermission(Role role, Permission permission) {
        if (!role.getPermissions().contains(permission)) {
            role.getPermissions().add(permission);
            roleRepository.save(role);
        }
    }

//    private void createAdminIfNotExists(Role adminRole) {
//
//        if (userRepository.existsByUsername(AdminCredentials.Admin1.ADMIN_USERNAME)) {
//            return;
//        }
//
//        User admin = User.builder()
//                .firstName(AdminCredentials.Admin1.ADMIN_FIRST_NAME)
//                .lastName(AdminCredentials.Admin1.ADMIN_LAST_NAME)
//                .username(AdminCredentials.Admin1.ADMIN_USERNAME)
//                .email(AdminCredentials.Admin1.ADMIN_EMAIL)
//                .password(passwordEncoder.encode(AdminCredentials.Admin1.ADMIN_PASSWORD))
//                .roles(Set.of(adminRole))
//                .build();
//
//        userRepository.save(admin);
//    }
}

