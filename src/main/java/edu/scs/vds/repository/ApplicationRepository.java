package edu.scs.vds.repository;

import edu.scs.vds.model.Application;
import edu.scs.vds.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    Application findByUser(User user);

}
