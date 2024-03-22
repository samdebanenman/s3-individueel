package com.tennissupplies.tennissuppliesbackend.repository;
import com.tennissupplies.tennissuppliesbackend.models.StringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StringRepository extends JpaRepository<StringEntity, Long> {
}
