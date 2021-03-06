package com.developersboard.backend.service.user;

import com.developersboard.backend.persistent.domain.user.Role;
import com.developersboard.backend.persistent.repository.RoleRepository;
import com.developersboard.backend.service.user.impl.RoleServiceImpl;
import com.developersboard.enums.RoleType;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class RoleServiceTest {

  @InjectMocks private transient RoleServiceImpl roleService;

  @Mock private transient RoleRepository roleEntityRepository;

  private transient Role roleEntity;

  @BeforeEach
  void setUp() {
    roleEntity = new Role(RoleType.ROLE_USER);
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void saveRole() {
    Mockito.when(roleEntityRepository.save(roleEntity)).thenReturn(roleEntity);

    Role storedRoleDetails = roleService.saveRole(this.roleEntity);
    Assertions.assertNotNull(storedRoleDetails);
  }

  @Test
  void getRoleById() {
    Mockito.when(roleEntityRepository.findById(roleEntity.getId()))
        .thenReturn(Optional.of(roleEntity));

    Role storedRoleDetails = roleService.getRoleById(roleEntity.getId());
    Assertions.assertEquals(roleEntity, storedRoleDetails);
  }

  @Test
  void getRoleByName() {
    Mockito.when(roleEntityRepository.findByName(roleEntity.getName())).thenReturn(roleEntity);

    Role storedRoleDetails = roleService.getRoleByName(roleEntity.getName());
    Assertions.assertEquals(roleEntity, storedRoleDetails);
  }
}
