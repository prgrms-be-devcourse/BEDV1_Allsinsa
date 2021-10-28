package com.progm.allsinsa.product;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class productController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody ProductRequestDto requestDto){
        long savedId = productService.save(requestDto);
        return ResponseEntity.ok(savedId);
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
    public ResponseEntity<Page<ProductDto>> getAllByCategory(@RequestParam("category") String category, Pageable pageable) {
        Page<ProductDto> findProductByCategory = productService.findAllByCategory(category, pageable);
        return ResponseEntity.ok(findProductByCategory);
    }


}
