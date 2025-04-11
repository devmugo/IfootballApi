package com.example.notificationsModule.Services;

import com.example.notificationsModule.DTO.blogDTO;
import com.example.notificationsModule.Models.Blog;
import com.example.notificationsModule.Repos.BlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public Blog addBlog(blogDTO blogdto ) {
        Blog blog = new Blog();
        blog.setTitle(blogdto.getBlogTitle());
        blog.setContent(blogdto.getBlogContent());
        blog.setAuthor(
                blogdto.getAuthor().split("@",2)[0]
        );
        blog.setPublishedDate(blogdto.getCreatedAt());
        blog.setBlogImage(blogdto.getBlogImage());
        blog.setTags(blogdto.getTags());
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Long id, Blog updatedBlog) {
        return blogRepository.findById(id).map(blog -> {
            blog.setTitle(updatedBlog.getTitle());
            blog.setContent(updatedBlog.getContent());
            blog.setTags(updatedBlog.getTags());
            return blogRepository.save(blog);
        }).orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    public Page<Blog> searchBlogs(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return blogRepository.searchByTitleOrTags(keyword, pageable);
        }
        return blogRepository.findAll(pageable);
    }
}

