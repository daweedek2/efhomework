package com.kostka.efhomework.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/** .
 * think about the abstract or super entity with the similar methods (User, Group, Permission)
 */

@Entity
@Table(name = "groups")
public class Group implements Serializable {

    @Id
    private String name;

    @ManyToMany
    private Set<Group> parentGroups = new HashSet<>();

    @ManyToMany
    private Set<Permission> grantedPermissions = new HashSet<>();

    @ManyToMany
    private Set<Permission> revokedPermissions = new HashSet<>();

    public Group() {
        //empty constructor
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Group> getParentGroups() {
        return parentGroups;
    }

    public void setParentGroups(final Set<Group> parentGroup) {
        this.parentGroups = parentGroup;
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
        return "Group{"
                + "name='" + name + '\''
                + ", parentGroup=" + parentGroups
                + ", grantedPermissions=" + grantedPermissions
                + ", revokedPermissions=" + revokedPermissions
                + '}';
    }
}

