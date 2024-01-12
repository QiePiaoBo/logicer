package com.dylan.licence.model.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    private Date createTime;

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    private Date updateTime;

    public boolean isValid(){
        return Objects.nonNull(getId())
                && Objects.nonNull(getTitle())
                && Objects.nonNull(getAuthor())
                && Objects.nonNull(getPrice())
                ;
    }

}
