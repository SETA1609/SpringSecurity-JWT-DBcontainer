package com.stp.springsecurityjwtdbcontainer.API;


import com.stp.springsecurityjwtdbcontainer.BussinesLayer.AuthenticationService;
import com.stp.springsecurityjwtdbcontainer.DTO.AuthenticationRequest;
import com.stp.springsecurityjwtdbcontainer.DTO.RegisterRequest;
import com.stp.springsecurityjwtdbcontainer.Exeptions.UserAlreadyExistsException;
import com.stp.springsecurityjwtdbcontainer.Exeptions.UserDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthController {

    private final AuthenticationService authenticationService;

    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.register(request));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ){
    try{
        return ResponseEntity.ok (authenticationService.authenticate(request));
    }catch (UserDoesntExistException e){
        return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (e.getMessage ());
    }


    }

}
