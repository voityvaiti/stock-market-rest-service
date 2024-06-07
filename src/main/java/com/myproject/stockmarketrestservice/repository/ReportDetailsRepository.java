package com.myproject.stockmarketrestservice.repository;

import com.myproject.stockmarketrestservice.model.document.ReportDetails;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportDetailsRepository extends MongoRepository<ReportDetails, ObjectId> {

    Optional<ReportDetails> findByReportId(String reportId);

}