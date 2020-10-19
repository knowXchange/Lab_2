package co.edu.unal.software_engineering.labs.service;

import co.edu.unal.software_engineering.labs.model.Association;
import co.edu.unal.software_engineering.labs.model.Course;
import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;
import co.edu.unal.software_engineering.labs.repository.AssociationRepository;
import co.edu.unal.software_engineering.labs.repository.UserRepository;

import java.util.ArrayList;

import org.springframework.stereotype.Service;


@Service
public class UserService{

    private final UserRepository userRepository;
    
    private final AssociationRepository associationRepository;

    public UserService( UserRepository userRepository , AssociationRepository associationRepository){
        this.userRepository = userRepository;
        this.associationRepository = associationRepository;
    }


    public User findByUsername( String username ){
        return userRepository.findByUsername( username );
    }

    public void save( User user ){
        userRepository.save( user );
    }

    public boolean isRightUser( RegisterUserPOJO user ){
        boolean correctness = user.getNames( ) != null && user.getPassword( ) != null && user.getUsername( ) != null &&
                user.getSurnames( ) != null;
        if( correctness ){
            correctness = !user.getNames( ).trim( ).isEmpty( )
                    && !user.getPassword( ).trim( ).isEmpty( )
                    && !user.getUsername( ).trim( ).isEmpty( )
                    && !user.getSurnames( ).trim( ).isEmpty( );
        }
        return correctness;
    }

    public ArrayList<Course> getCoursesByRole(Integer roleId, User user){
    	ArrayList<Course> coursesObtained = new ArrayList<Course>();
        int userId = user.getId();
        ArrayList<Association> associations = (ArrayList<Association>)this.associationRepository.findAll();
    	for(int i = 0; i<associations.size();i++) {
    		Association associationObtained = associations.get(i);
    		if(associationObtained.getRole().getId().equals(roleId) && associationObtained.getUser().getId().equals(userId)) {
    			coursesObtained.add(associationObtained.getCourse());
    		}
    	}
    	return coursesObtained;
    }
}