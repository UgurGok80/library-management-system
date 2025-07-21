package com.library.management_system.service;

import com.library.management_system.model.Book;
import com.library.management_system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    // Tüm kitapları getir
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    // ID ile kitap getir
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    
    // Yeni kitap kaydet
    public Book saveBook(Book book) {
        // Basit validasyon
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap başlığı boş olamaz");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Yazar adı boş olamaz");
        }
        return bookRepository.save(book);
    }
    
    // Kitap güncelle
    public Book updateBook(Long id, Book bookDetails) {
        Optional<Book> existingBook = bookRepository.findById(id);
        
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setPublisher(bookDetails.getPublisher());
            book.setPageCount(bookDetails.getPageCount());
            book.setPublishDate(bookDetails.getPublishDate());
            
            return bookRepository.save(book);
        } else {
            throw new RuntimeException("Kitap bulunamadı: " + id);
        }
    }
    
    // Kitap sil
    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Silinecek kitap bulunamadı: " + id);
        }
    }
    
    // Başlığa göre kitap ara
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    // Yazara göre kitap ara
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
    
    // Başlık veya yazara göre kitap ara
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleOrAuthorContaining(keyword);
    }
    
    // Toplam kitap sayısı
    public long getTotalBookCount() {
        return bookRepository.count();
    }
}