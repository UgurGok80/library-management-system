package com.library.management_system.repository;

import com.library.management_system.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Başlığa göre kitap arama (içerir)
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Yazara göre kitap arama (içerir)
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    // Yayınevine göre kitap arama
    List<Book> findByPublisher(String publisher);
    
    // Sayfa sayısına göre aralık arama
    List<Book> findByPageCountBetween(Integer minPages, Integer maxPages);
    
    // Custom query - Başlık veya yazar ile arama
    @Query("SELECT b FROM Book b WHERE b.title ILIKE %:keyword% OR b.author ILIKE %:keyword%")
    List<Book> findByTitleOrAuthorContaining(@Param("keyword") String keyword);
}