package com.stp.springsecurityjwtdbcontainer.BussinesLayer;


import com.stp.springsecurityjwtdbcontainer.DAO.Enduser;
import com.stp.springsecurityjwtdbcontainer.Exeptions.UserDoesntExistException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface EnduserService {

    public Enduser update(String username, Map<String, Object> updates) throws UserDoesntExistException;

}
