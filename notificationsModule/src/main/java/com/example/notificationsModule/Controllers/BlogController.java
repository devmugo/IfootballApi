package com.example.notificationsModule.Controllers;

import com.example.notificationsModule.DTO.blogDTO;
import com.example.notificationsModule.Models.Blog;
import com.example.notificationsModule.Services.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // ✅ Get all blogs
    @GetMapping
    public Page<Blog> getAllBlogs(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return blogService.searchBlogs(keyword, pageable);
    }

    // ✅ Get a single blog by ID
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addBlog(@RequestBody blogDTO blog) {
        blogService.addBlog(blog);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Blog saved successfully");
        return ResponseEntity.ok(response);
    }

    // ✅ Update an existing blog
    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog updatedBlog) {
        try {
            Blog blog = blogService.updateBlog(id, updatedBlog);
            return ResponseEntity.ok(blog);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Delete a blog by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
}

