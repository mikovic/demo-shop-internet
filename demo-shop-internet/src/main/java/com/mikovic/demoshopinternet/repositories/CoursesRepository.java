package com.mikovic.demoshopinternet.repositories;



import com.mikovic.demoshopinternet.entities.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends CrudRepository<Course, Long> {
}
