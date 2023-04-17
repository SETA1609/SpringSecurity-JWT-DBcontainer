package com.stp.springsecurityjwtdbcontainer.BussinesLayer;



import com.stp.springsecurityjwtdbcontainer.DAO.Enduser;
import com.stp.springsecurityjwtdbcontainer.DAO.Role;
import com.stp.springsecurityjwtdbcontainer.DAO.Token;
import com.stp.springsecurityjwtdbcontainer.DAO.TokenType;
import com.stp.springsecurityjwtdbcontainer.DTO.AuthenticationRequest;
import com.stp.springsecurityjwtdbcontainer.DTO.AuthenticationResponse;
import com.stp.springsecurityjwtdbcontainer.DTO.RegisterRequest;
import com.stp.springsecurityjwtdbcontainer.Exeptions.UserAlreadyExistsException;
import com.stp.springsecurityjwtdbcontainer.Exeptions.UserDoesntExistException;
import com.stp.springsecurityjwtdbcontainer.Repositories.EnduserRepository;
import com.stp.springsecurityjwtdbcontainer.Repositories.TokenRepository;
import com.stp.springsecurityjwtdbcontainer.SecurityLayer.JwtService;
import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final EnduserRepository enduserRepository;

    private  final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;

    private  final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistsException {
        if (enduserRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException ("Username already exists");
        }
        if (enduserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        var user = Enduser.builder()
                .username (request.getUsername ())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        var savedUser = enduserRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken (savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .username (savedUser.getUsername ())
                .token(jwtToken)
                .build();

    }

    private void saveUserToken(Enduser enduser, String jwtToken) {
        var token = Token.builder ()
                .enduser (enduser)
                .token (jwtToken)
                .tokenType (TokenType.BEARER)
                .revoked (false)
                .expired (false)
                .build ();

        tokenRepository.save (token);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserDoesntExistException {
        var user = enduserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserDoesntExistException("User with the given username doesn't exist"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken (
                        request.getUsername (),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .token(jwtToken)
                .role(user.getRole().toString())
                .build();
    }


    private void revokeAllUserTokens(Enduser enduser) {

        var validUserTokens = tokenRepository.findAllValidTokenByUser (enduser.getEnduserId ());
        if (validUserTokens.isEmpty ()){
               return;
        }
        validUserTokens.forEach (t -> {
            t.setRevoked (true);
            t.setExpired (true);
        });
        tokenRepository.saveAll (validUserTokens);



    }
}
