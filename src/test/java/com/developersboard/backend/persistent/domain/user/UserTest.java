package com.developersboard.backend.persistent.domain.user;

import com.developersboard.TestUtils;
import com.developersboard.shared.util.UserUtils;
import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void equalsContract() {
    User client = UserUtils.createUser();
    User admin = UserUtils.createUser();

    EqualsVerifier.forClass(User.class)
        .withRedefinedSuperclass()
        .withPrefabValues(User.class, client, admin)
        .withOnlyTheseFields(TestUtils.getUserEqualsFields().toArray(new String[0]))
        .verify();
  }

  @Test
  void testToString() {
    ToStringVerifier.forClass(User.class)
        .withClassName(NameStyle.SIMPLE_NAME)
        .withIgnoredFields("password", "userRoles", "userHistories")
        .verify();
  }
}
