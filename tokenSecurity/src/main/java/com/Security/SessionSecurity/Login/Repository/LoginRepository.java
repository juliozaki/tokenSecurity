package com.Security.SessionSecurity.Login.Repository;

import com.Security.SessionSecurity.Login.Model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {
    Login findByEmail(String email);
}
