package com.stp.springsecurityjwtdbcontainer.DAO;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Enduser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enduserId;

    @Column(unique = true)
    private String username;

    private String email;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;


    private String password;


    @Enumerated(
            EnumType.STRING
    )
    private Role role;

    @OneToMany(mappedBy = "enduser")
    private List<Token> tokens;

    public Enduser(String username){
        this.username = username;

    }


    public String getImage(){
        return image != null ? Base64.getEncoder().encodeToString(image) : "";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of (new SimpleGrantedAuthority (role.name ()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
