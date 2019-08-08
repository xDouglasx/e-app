package com.douglas.eapp.controller;

import com.douglas.eapp.domain.Category;
import com.douglas.eapp.dto.CategoryDTO;
import com.douglas.eapp.service.CategoryService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<Category> getCategory(@PathVariable Integer id) {
    return ResponseEntity.ok(categoryService.findCategory(id));
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Void> insert(@Valid @RequestBody CategoryDTO categoryDTO) {
    Category category = categoryService.insert(categoryService.fromDTO(categoryDTO));
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(category.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @PutMapping(value = "/{id}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Category> update(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable("id") String id) {
    categoryDTO.setId(Integer.parseInt(id));
    return ResponseEntity.ok(categoryService.update(categoryService.fromDTO(categoryDTO)));
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable("id") String id) {
    categoryService.delete(Integer.parseInt(id));
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<CategoryDTO>> findAll() {
    List<CategoryDTO> categoryList = categoryService.findAll()
        .stream()
        .map(CategoryDTO::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(categoryList);
  }

  @GetMapping(value = "/page")
  public ResponseEntity<Page<CategoryDTO>> findPage(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerpage,
      @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
      @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
    Page<CategoryDTO> categoryList = categoryService.findPage(page, linesPerpage, orderBy, direction)
        .map(CategoryDTO::new);
    return ResponseEntity.ok().body(categoryList);
  }

}
