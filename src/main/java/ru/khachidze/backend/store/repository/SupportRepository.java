package ru.khachidze.backend.store.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.khachidze.backend.store.entity.SupportEntity;

@Repository
public interface SupportRepository extends CrudRepository<SupportEntity, Long> {
}
