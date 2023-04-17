package com.stp.springsecurityjwtdbcontainer.BussinesLayer;


import com.stp.springsecurityjwtdbcontainer.DAO.Enduser;
import com.stp.springsecurityjwtdbcontainer.Exeptions.UserDoesntExistException;
import com.stp.springsecurityjwtdbcontainer.Repositories.EnduserRepository;
import com.stp.springsecurityjwtdbcontainer.SecurityLayer.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class EnduserServiceImpl implements EnduserService {

    private final EnduserRepository enduserRepository;

    private final ApplicationConfig applicationConfig;

    @Override
    public Enduser update(String username, Map<String, Object> updates) throws UserDoesntExistException {
        return enduserRepository.findByUsername (username).map (enduser -> {
            updates.forEach ((key, value) -> {
                switch (key) {
                    case "username":
                        enduser.setUsername ((String) value);
                        break;
                    case "email":
                        enduser.setEmail ((String) value);
                        break;
                    case "image":
                        enduser.setImage (((String) value).getBytes ());
                        break;
                    case "password":
                        String encodedPassword = applicationConfig.passwordEncoder().encode((String) value);
                        enduser.setPassword(encodedPassword);
                        break;
                }
            });
            return enduserRepository.save (enduser);
        }).orElseThrow (() -> new UserDoesntExistException ("User doesn't exist"));
    }

}