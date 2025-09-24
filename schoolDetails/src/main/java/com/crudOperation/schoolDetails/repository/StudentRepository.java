package com.crudOperation.schoolDetails.repository;

import com.crudOperation.schoolDetails.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//ctrl+alt+o unused import remover
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
