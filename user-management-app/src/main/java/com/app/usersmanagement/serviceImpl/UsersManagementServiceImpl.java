package com.app.usersmanagement.serviceImpl;

import com.app.usersmanagement.entity.Users;
import com.app.usersmanagement.repository.PageableRepository;
import com.app.usersmanagement.service.UsersManagementService;
import com.app.usersmanagement.utils.UserUtils;
import com.app.usersmanagement.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
/*
 *author: Sima Jadhav
 */

@Service
public class UsersManagementServiceImpl implements UsersManagementService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PageableRepository pageableRepository;

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserUtils register(UserUtils registrationRequest){
        UserUtils respUser = new UserUtils();

        try {
            Users user = new Users();
            user.setEmail(registrationRequest.getEmail());
            user.setCity(registrationRequest.getCity());
            user.setRoles(registrationRequest.getRoles());
            user.setName(registrationRequest.getName());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            Users ourUsersResult = usersRepository.save(user);
            if (ourUsersResult.getId()>0) {
                respUser.setUsers((ourUsersResult));
                respUser.setMessage("User Saved Successfully");
                respUser.setStatusCode(200);
            }

        }catch (Exception e){
            respUser.setStatusCode(500);
            respUser.setError(e.getMessage());
        }
        return respUser;
    }


    public UserUtils login(UserUtils loginRequest){
        UserUtils response = new UserUtils();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            var user = usersRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRoles(user.getRoles());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }





    public UserUtils refreshToken(UserUtils refreshTokenReqiest){
        UserUtils response = new UserUtils();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            Users users = usersRepository.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    public UserUtils getAllUsers() {
        UserUtils userUtils = new UserUtils();

        try {
            List<Users> result = usersRepository.findAll();
            if (!result.isEmpty()) {
                userUtils.setUsersList(result);
                userUtils.setStatusCode(200);
                userUtils.setMessage("Successful");
            } else {
                userUtils.setStatusCode(404);
                userUtils.setMessage("No users found");
            }
            return userUtils;
        } catch (Exception e) {
            userUtils.setStatusCode(500);
            userUtils.setMessage("Error occurred: " + e.getMessage());
            return userUtils;
        }
    }

    /*public Page<Users> getAllUsers(Pageable pageable) {
        ReqRes reqRes = new ReqRes();

        try {
            Page<Users> result = pageableRepo.findAll(pageable);
            if (!result.isEmpty()) {
                reqRes.setUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }*/

    public Page<Users> getAllUsers(Pageable pageable) {
            return pageableRepository.findAll(pageable);
    }


    public UserUtils getUsersById(Integer id) {
        UserUtils userUtils = new UserUtils();
        try {
            Users usersById = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            userUtils.setUsers(usersById);
            userUtils.setStatusCode(200);
            userUtils.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            userUtils.setStatusCode(500);
            userUtils.setMessage("Error occurred: " + e.getMessage());
        }
        return userUtils;
    }


    public UserUtils deleteUser(Integer userId) {
        UserUtils userUtils = new UserUtils();
        try {
            Optional<Users> userOptional = usersRepository.findById(userId);
            if (userOptional.isPresent()) {
                usersRepository.deleteById(userId);
                userUtils.setStatusCode(200);
                userUtils.setMessage("User deleted successfully");
            } else {
                userUtils.setStatusCode(404);
                userUtils.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            userUtils.setStatusCode(500);
            userUtils.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return userUtils;
    }

    public UserUtils updateUser(Integer userId, Users updatedUser) {
        UserUtils userUtils = new UserUtils();
        try {
            Optional<Users> userOptional = usersRepository.findById(userId);
            if (userOptional.isPresent()) {
                Users existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRoles(updatedUser.getRoles());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encoded the password and updated
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                Users savedUser = usersRepository.save(existingUser);
                userUtils.setUsers(savedUser);
                userUtils.setStatusCode(200);
                userUtils.setMessage("User updated successfully");
            } else {
                userUtils.setStatusCode(404);
                userUtils.setMessage("User not found for update");
            }
        } catch (Exception e) {
            userUtils.setStatusCode(500);
            userUtils.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return userUtils;
    }


    public UserUtils getMyInfo(String email){
        UserUtils userUtils = new UserUtils();
        try {
            Optional<Users> userOptional = usersRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                userUtils.setUsers(userOptional.get());
                userUtils.setStatusCode(200);
                userUtils.setMessage("successful");
            } else {
                userUtils.setStatusCode(404);
                userUtils.setMessage("User not found for update");
            }

        }catch (Exception e){
            userUtils.setStatusCode(500);
            userUtils.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return userUtils;

    }

}
