package com.kostka.efhomework.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/** .
 * think about the abstract or super entity with the similar methods (User, Group, Permission)
 */

@Entity
@Table(name = "groups")
public class Group extends AbstractEntity {

    @Override
    public String toString() {
        return "Group{"
                + "name='" + super.getName() + '\''
                + ", parentGroup=" + super.getParentGroups()
                + ", grantedPermissions=" + super.getGrantedPermissions()
                + ", revokedPermissions=" + super.getRevokedPermissions()
                + '}';
    }
}

