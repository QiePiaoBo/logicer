package com.dylan.licence.model.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Data
@Document(indexName = "book", createIndex = true)
public class Book {

    @Id
    @Field(type = FieldType.Text)
    private String id;

    @Field(analyzer = "ik_max_word")
    private String title;

    @Field(analyzer = "ik_max_word")
    private String author;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd HH:mm:ss.SSSX || yyyy-MM-dd'T'HH:mm:ss'+08:00' || strict_date_optional_time || epoch_millis")
    private Date createTime;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd HH:mm:ss.SSSX || yyyy-MM-dd'T'HH:mm:ss'+08:00' || strict_date_optional_time || epoch_millis")
    private Date updateTime;

    public boolean dataValid(){
        return Objects.nonNull(getId())
                && Objects.nonNull(getTitle())
                && Objects.nonNull(getAuthor())
                && Objects.nonNull(getPrice())
                ;
    }

}
