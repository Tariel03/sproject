package com.example.sproject.Repositories;


import com.example.sproject.Models.Doll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DollRepository extends JpaRepository<Doll,Long> {

}
