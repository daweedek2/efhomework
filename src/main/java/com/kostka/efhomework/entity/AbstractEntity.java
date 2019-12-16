package com.kostka.efhomework.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    private String name;

    @Column
    @ManyToMany
    private Set<Group> parentGroups = new HashSet<>();

    @Column
    @ManyToMany
    private Set<Permission> grantedPermissions = new HashSet<>();

    @Column
    @ManyToMany
    private Set<Permission> revokedPermissions = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Group> getParentGroups() {
        return parentGroups;
    }

    public void setParentGroups(final Set<Group> groups) {
        this.parentGroups = groups;
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
}
