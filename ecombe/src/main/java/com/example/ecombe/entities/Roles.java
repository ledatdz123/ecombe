package com.example.ecombe.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Roles {
    @Id
    @Column(name = "Role_ID", nullable = false)
    private int roleId;
    @Column(name = "Role_Name", nullable = false)
    private String roleName;
    @ManyToMany(mappedBy = "role")
    Set<User> user=new HashSet<>();
}
