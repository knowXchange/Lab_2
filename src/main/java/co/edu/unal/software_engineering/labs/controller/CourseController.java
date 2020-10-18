package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.Course;
import co.edu.unal.software_engineering.labs.pojo.CoursePOJO;
import co.edu.unal.software_engineering.labs.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CourseController{

    private final CourseService courseService;

    public CourseController( CourseService courseService ){
        this.courseService = courseService;
    }

    //-----------------------------------------------------------------------------------------
    //Original 
    
    @PostMapping( value = {"/profesor/cursos"} )
    public ResponseEntity<Void> createCourse( @RequestBody CoursePOJO coursePojo ){
        Course course = courseService.mapperCourseEntity( coursePojo );
        if( !courseService.isRightCourse( course ) ){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
        courseService.save( course );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }
    
    //----------------------------------------------------------------------------------
    
    
    //---------------------------------------------------------
    //Nuevo
    
    @PostMapping( value = {"/profesor/crear-curso"} )
    public ResponseEntity<Void> createCourseTeacher( @RequestBody CoursePOJO coursePojo ){
    	boolean creado = courseService.createCourseTeacherService( coursePojo );
    	
        if(creado){return new ResponseEntity<>( HttpStatus.CREATED );} 
        else { return new ResponseEntity<>( HttpStatus.BAD_REQUEST ); }
        
    }  
    //---------------------------------------------------------------------------
  

}