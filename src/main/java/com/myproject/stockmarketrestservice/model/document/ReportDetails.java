package com.myproject.stockmarketrestservice.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.UUID;

@Document(collection = "report_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetails {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private UUID reportId;

    private Map<String, Object> financialData;

    private String comments;

}
