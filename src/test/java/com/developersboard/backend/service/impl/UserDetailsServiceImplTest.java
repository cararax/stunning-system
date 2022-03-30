package com.developersboard.backend.service.impl;

import com.developersboard.backend.persistent.repository.UserRepository;
import com.developersboard.shared.util.StringUtils;
import com.developersboard.shared.util.UserUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserDetailsServiceImplTest {

  @Mock private transient UserRepository userRepository;

  @InjectMocks private transient UserDetailsServiceImpl userDetailsService;

  private transient String email;

  @BeforeEach
  void setUp(TestInfo testInfo) {
    MockitoAnnotations.openMocks(this);
    Assertions.assertNotNull(userRepository);
    Assertions.assertNotNull(userDetailsService);

    email = StringUtils.FAKER.internet().emailAddress();
    var user =
        UserUtils.createUser(
            testInfo.getDisplayName(), StringUtils.FAKER.internet().password(), email);

    Mockito.when(userRepository.findByUsername(testInfo.getDisplayName())).thenReturn(user);
    Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
  }

  @Test
  void testShouldReturnNullGivenNullInput() {
    Assertions.assertNull(userDetailsService.loadUserByUsername(null));
  }

  @Test
  void testShouldReturnUserGivenAnExistingUsername(TestInfo testInfo) {
    var userDetails = userDetailsService.loadUserByUsername(testInfo.getDisplayName());
    Assertions.assertNotNull(userDetails);
    Assertions.assertEquals(testInfo.getDisplayName(), userDetails.getUsername());
  }

  @Test
  void testShouldThrowExceptionForNonExistingUsername() {
    Assertions.assertThrows(
        UsernameNotFoundException.class,
        () -> userDetailsService.loadUserByUsername(StringUtils.FAKER.name().username()));
  }

  @Test
  void testShouldReturnUserGivenAnExistingEmail(TestInfo testInfo) {
    var userDetails = userDetailsService.loadUserByUsername(email);
    Assertions.assertNotNull(userDetails);
    Assertions.assertEquals(testInfo.getDisplayName(), userDetails.getUsername());
  }
}