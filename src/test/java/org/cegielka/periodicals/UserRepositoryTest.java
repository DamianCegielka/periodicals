package org.cegielka.periodicals;


import org.cegielka.periodicals.entity.User;
import org.cegielka.periodicals.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Collection;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class UserRepositoryTest {
    /*

    @Autowired
    private UserRepository userRepository;

    @Test
    public void AddNewUserAndUpdateInDatabase(){
        User savedUser=addUserToDatabase(userRepository,1);
        Assertions.assertNotNull(savedUser);
        Assertions.assertNotEquals(0,savedUser.getId());


    }

    @Test
    public void listAllUsersInDatabase(){
        addUserToDatabase(userRepository,2);
        Assertions.assertEquals(2,userRepository.count());

    }
    @Test
    public void updateUserInDatabase() {
        User addedUser=addUserToDatabase(userRepository,1);
        Long idToUpdate=addedUser.getId();
        Optional<User> optionalUser = userRepository.findById(idToUpdate);
        User userUpdate=null;
        if(optionalUser.isPresent()) {
            userUpdate = optionalUser.get();
            userUpdate.setActive(false);
        }
        Assertions.assertEquals(false, userUpdate.getActive());

    }

    @Test
    public void testGetUser(){
        addUserToDatabase(userRepository,1);
        Iterable<User> users=userRepository.findAll();
        Assertions.assertEquals(1,size(users));
    }

    @Test
    public void testDeleteUserFromDatabase(){
        addUserToDatabase(userRepository,1);
        userRepository.deleteAll();
        Assertions.assertEquals(0,userRepository.count());
    }

    public static int size(Iterable data) {

        if (data instanceof Collection) {
            return ((Collection<?>) data).size();
        }
        int counter = 0;
        for (Object i : data) {
            counter++;
        }
        return counter;
    }

    public static long idLastUserFromDatabase(Iterable data) {
        long id=0;

        if (data instanceof Collection) {
            return ((Collection<?>) data).size();
        }
        int counter = 0;
        for (Object i : data) {
            User result=(User)i;
            id=result.getId();
            return id;
        }
        return 0l;
    }

    public static User addUserToDatabase(UserRepository userRepository,Integer howManyUser){
        User saveUser=new User();
        for(int i=1;i<=howManyUser;i++) {
            User user = new User();
            user.setEmail("test" + i + ".test1@test1.com");
            user.setPassword("testNr1");
            user.setActive(true);
           saveUser=userRepository.save(user);
        }
        return saveUser;
    }

     */

}
