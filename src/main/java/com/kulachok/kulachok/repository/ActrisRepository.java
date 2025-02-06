package com.kulachok.kulachok.repository;

import com.kulachok.kulachok.entity.Actris;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActrisRepository extends JpaRepository<Actris, Integer> {
}
