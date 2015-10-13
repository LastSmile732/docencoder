package com.jrsys.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jrsys.model.Logs;

public interface LogRepository extends JpaRepository<Logs ,Integer>{

}
