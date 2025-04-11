package com.example.notificationsModule.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class blogDTO {

    public String blogTitle;
    public   String blogContent;
    public List<String> tags ;
    public String author ;
    public  String  blogImage ;
    public LocalDateTime createdAt ;
}
