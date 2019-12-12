package com.kostka.efhomework.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    private String name;

    @ManyToMany
    private Set<Group> groups = new HashSet<>();

    @ManyToMany
    private Set<Permission> grantedPermissions = new HashSet<>();

    @ManyToMany
    private Set<Permission> revokedPermissions = new HashSet<>();

    public User() {
        // empty constructor
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(final Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Permission> getGrantedPermissions() {
        return grantedPermissions;
    }

    public void setGrantedPermissions(final Set<Permission> grantedPermissions) {
        this.grantedPermissions = grantedPermissions;
    }

    public Set<Permission> getRevokedPermissions() {
        return revokedPermissions;
    }

    public void setRevokedPermissions(final Set<Permission> revokedPermissions) {
        this.revokedPermissions = revokedPermissions;
    }

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", groups=" + groups
                + ", grantedPermissions=" + grantedPermissions
                + ", revokedPermissions=" + revokedPermissions
                + '}';
    }
}