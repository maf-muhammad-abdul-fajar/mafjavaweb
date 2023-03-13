package com.juaracoding.mafjavaweb.repo;

import com.juaracoding.mafjavaweb.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long>{

}
