package com.progm.allsinsa.product.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progm.allsinsa.product.dto.ProductDto;
import com.progm.allsinsa.product.dto.ProductRequestDto;
import com.progm.allsinsa.product.service.ProductService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class productController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody ProductRequestDto requestDto) {
        long savedId = productService.save(requestDto);
        return ResponseEntity.created(URI.create("/api/v1/products/" + savedId))
                .body(savedId);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAll(Pageable pageable) {
        Page<ProductDto> productDtos = productService.findAll(pageable);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable Long id) throws NotFoundException {
        ProductDto productDto = productService.findOneById(id);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/category")
    public ResponseEntity<Page<ProductDto>> getAllByCategory(@RequestParam("category") String category,
            Pageable pageable) {
        Page<ProductDto> findProductByCategory = productService.findAllByCategory(category, pageable);
        return ResponseEntity.ok(findProductByCategory);
    }

}
