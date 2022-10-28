package perobobbot.domain.jpa.entity;

import fpc.tools.persistence.SimplePersistentObjectWithIdentityId;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity extends SimplePersistentObjectWithIdentityId {
}
