package com.stp.springsecurityjwtdbcontainer.DTO;


import com.stp.springsecurityjwtdbcontainer.DAO.Enduser;
import com.stp.springsecurityjwtdbcontainer.DAO.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnduserDTO {
    private String username;
    private String image;
    private Role role;
    public EnduserDTO(Enduser enduser) {
        this.username = enduser.getUsername ();
        this.image = enduser.getImage ();
        this.role=enduser.getRole ();
    }
}
