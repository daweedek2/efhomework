package com.kostka.efhomework.entity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class User extends SuperEntity {

    @Override
    public String toString() {
        return "User{"
                + "name='" + super.getName() + '\''
                + ", groups=" + super.getParentGroups()
                + ", grantedPermissions=" + super.getGrantedPermissions()
                + ", revokedPermissions=" + super.getRevokedPermissions()
                + '}';
    }
}