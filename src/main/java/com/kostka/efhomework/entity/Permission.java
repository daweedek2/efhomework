package com.kostka.efhomework.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permission")
public class Permission implements Serializable {
    @Id
    private String name;

    @ManyToMany
    private Set<Permission> requiredPermissions = new HashSet<>();

    public Permission() {
        // empty constructor
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Permission> getRequiredPermissions() {
        return requiredPermissions;
    }

    public void setRequiredPermissions(final Set<Permission> requiredPermissions) {
        this.requiredPermissions = requiredPermissions;
    }

    @Override
    public String toString() {
        return "Permission{"
                + "name='" + name + '\''
                + ", requiredPermissions=" + requiredPermissions
                + '}';
    }
}
