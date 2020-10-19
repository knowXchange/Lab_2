package co.edu.unal.software_engineering.labs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unal.software_engineering.labs.model.Course;
import co.edu.unal.software_engineering.labs.model.Period;
import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.service.AssociationService;
import co.edu.unal.software_engineering.labs.service.CourseService;
import co.edu.unal.software_engineering.labs.service.PeriodService;
import co.edu.unal.software_engineering.labs.service.UserService;

@RestController
public class TeacherController{

    private AssociationService associationService;

    private UserService userService;

    private CourseService courseService;

    private PeriodService periodService;

    public TeacherController( UserService userService, CourseService courseService, PeriodService periodService,
                              AssociationService associationService ){
        this.userService = userService;
        this.courseService = courseService;
        this.periodService = periodService;
        this.associationService = associationService;
    }

    @PostMapping( value = { "/profesor/crear-curso/nombre/{courseName}/duracion/{time}/periodo/{periodId}" } )
    public ResponseEntity<Void> associateStudent( @PathVariable Integer periodId,@PathVariable String courseName,@PathVariable Integer time){
        String username = SecurityContextHolder.getContext( ).getAuthentication( ).getName( );
        User teacher = userService.findByUsername( username );
        Course course = new Course();
        course.setCourseName(courseName);
        course.setDurationHours(time);
        courseService.save(course);
        Period period = periodService.findById( periodId );
        Role role = new Role( );

        if( course == null || period == null ){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }

        role.setId( Role.ROLE_TEACHER );
        associationService.associate( teacher, role, course, period );

        return new ResponseEntity<>( HttpStatus.CREATED );
    }

}
