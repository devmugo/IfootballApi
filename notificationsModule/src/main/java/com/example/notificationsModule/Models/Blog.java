package com.example.notificationsModule.Models;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "football_blogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String author;

    private LocalDateTime publishedDate;


    @Lob  // Marks this field as a Large Object (useful for large Base64 images)
    @Column(columnDefinition = "LONGTEXT") // Store Base64 as a large text
    private String blogImage;

    @ElementCollection
    private List<String> tags;
}
