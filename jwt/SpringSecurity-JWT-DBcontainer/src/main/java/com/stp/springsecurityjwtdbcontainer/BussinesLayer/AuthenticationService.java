package com.stp.springsecurityjwtdbcontainer.BussinesLayer;



import com.stp.springsecurityjwtdbcontainer.DTO.AuthenticationRequest;
import com.stp.springsecurityjwtdbcontainer.DTO.AuthenticationResponse;
import com.stp.springsecurityjwtdbcontainer.DTO.RegisterRequest;
import com.stp.springsecurityjwtdbcontainer.Exeptions.UserAlreadyExistsException;
import com.stp.springsecurityjwtdbcontainer.Exeptions.UserDoesntExistException;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {


    AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistsException;

    AuthenticationResponse authenticate(AuthenticationRequest request) throws UserDoesntExistException;
}
