package org.psu.lab5.repository;

import org.psu.lab5.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

}
