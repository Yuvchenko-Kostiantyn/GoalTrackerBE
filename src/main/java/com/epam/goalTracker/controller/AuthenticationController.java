//package com.epam.goalTracker.controller;
//
//import com.epam.goalTracker.mapperStruct.UserMapper;
//import com.epam.goalTracker.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RestController;
//
//@CrossOrigin
//@RestController
//public class AuthenticationController {
//
//    private final AuthenticationManager authenticationManager;
//
//    //private final JwtTokenProvider jwtTokenProvider;
//
//    private final UserService userService;
//
//    private UserMapper userMapper;
//
//    @Autowired
//    public AuthenticationController(AuthenticationManager authenticationManager,  UserService userService, UserMapper userMapper) {
//        this.authenticationManager = authenticationManager;
//        //this.jwtTokenProvider = jwtTokenProvider;
//        this.userService = userService;
//        this.userMapper = userMapper;
//    }
//
//    @GetMapping(path = "/reset")
//    public ResponseEntity logOut(@RequestHeader (value = "Authorization") String token){
//        JwtTokenBlackListDto jwtTokenBlackListDto = userService.getToken(token);
//        if(jwtTokenBlackListDto != null){
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//        userService.saveTokenToBlackList(token);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @PostMapping("/registration")
//    public ResponseEntity register(@RequestBody RegistrationModel registrationModel) {
//        UserDto userDto = userMapper.map(registrationModel, UserDto.class);
//        userService.createUser(userDto);
//        return new ResponseEntity(HttpStatus.CREATED);
//    }
//
//    @PostMapping("/admin/batch/registration")
//    public ResponseEntity registerBatch(@RequestBody RegistrationModel[] registrationModels) {
//        for (RegistrationModel registrationModel : registrationModels) {
//            UserDto userDto = userMapper.map(registrationModel, UserDto.class);
//            userService.createUser(userDto);
//        }
//        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
//        return new ResponseEntity(HttpStatus.CREATED);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody AuthRequestModel requestDto) {
//        try {
//            String email = requestDto.getEmail();
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
//            UserDto user = userService.getUser(email);
//
//            UserEntity foundUser = userMapper.map(user, UserEntity.class);
//            AuthResponseModel responseModel = new AuthResponseModel();
//            responseModel.setEmail(foundUser.getEmail());
//            responseModel.setToken(jwtTokenProvider.createToken(email, (List<RoleEntity>) foundUser.getRoles()));
//            responseModel.setId(foundUser.getId());
//
//            for(RoleEntity roleEntity : foundUser.getRoles()){
//                if(roleEntity.getName().equals("ROLE_ADMIN")) {
//                    responseModel.setAdmin(true);
//                    break;
//                }
//            }
//            return ResponseEntity.ok(responseModel);
//        } catch (AuthenticationException e) {
//            throw new BadCredentialsException("Invalid username or password");
//        }
//    }
//}