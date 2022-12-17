package org.psu.lab5.repository;

import java.sql.Blob;

import org.psu.lab5.model.BinFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinFileRepository extends JpaRepository<BinFile, Long> {

}
