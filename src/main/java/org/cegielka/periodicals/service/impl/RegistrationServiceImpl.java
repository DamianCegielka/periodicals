package org.cegielka.periodicals.service.impl;

import lombok.extern.log4j.Log4j2;
import org.cegielka.periodicals.dto.UserRegistrationRequest;
import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.UserRepository;
import org.cegielka.periodicals.service.RegistrationService;
import org.cegielka.periodicals.service.exception.UserNotFoundByIdException;
import org.cegielka.periodicals.service.mapper.UserRegistrationRequestMapper;
import org.cegielka.periodicals.service.validator.PasswordEncoder;
import org.cegielka.periodicals.service.validator.UserRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private UserRegistrationRequestMapper userRegistrationRequestMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRegistrationValidator userRegistrationValidator;


    public RegistrationServiceImpl(UserRepository userRepository,
                                   UserRegistrationValidator userRegistrationValidator,
                                   PasswordEncoder passwordEncoder,
                                   UserRegistrationRequestMapper userRegistrationRequestMapper){
        this.userRepository = userRepository;
        this.userRegistrationValidator=userRegistrationValidator;
        this.passwordEncoder=passwordEncoder;
        this.userRegistrationRequestMapper=userRegistrationRequestMapper;}


    @Override
    @Transactional
    public void register(UserRegistrationRequest request) {
        userRegistrationValidator.validate(request);
        String password= request.getPassword();
        String encodePassword=passwordEncoder.encode(password);
        User user = userRegistrationRequestMapper.map(request,encodePassword);
        userRepository.save(user);

    }

    @Override
    public User login(String email, String password) {
        User user=userRepository.findUserByEmail(email)
                .orElseThrow(()->new RuntimeException());
        boolean matched= passwordEncoder.isMatched(password,user.getPassword());

        if(!matched){
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> listAll(){
        return (List<User>) userRepository.findAll();
    }

    public User get(Long id) throws UserNotFoundByIdException {
        Optional<User> resultById=userRepository.findById(id);
        if(resultById.isPresent()){
            return resultById.get();
        }
        else {
            throw new UserNotFoundByIdException();
        }
    }

}





