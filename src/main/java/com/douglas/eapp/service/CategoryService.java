package com.douglas.eapp.service;

import com.douglas.eapp.domain.Category;
import com.douglas.eapp.dto.CategoryDTO;
import com.douglas.eapp.exception.ObjectNotFoundException;
import com.douglas.eapp.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public Category findCategory(Integer id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Category with id: " + id + " not found."));
  }

  public Category insert(Category category) {
    return categoryRepository.save(category);
  }

  public Category update(Category updateCategory) {
    Category category = mergeCategory(findCategory((updateCategory.getId())), updateCategory);
    return categoryRepository.save(category);
  }

  public void delete(Integer id) {
    findCategory(id);
    try {
      categoryRepository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationException("Cannot delete");
    }
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    return categoryRepository.findAll(pageRequest);
  }

  public Category fromDTO(CategoryDTO categoryDTO) {
    return Category.builder()
        .id(categoryDTO.getId())
        .name(categoryDTO.getName())
        .build();
  }

  private Category mergeCategory(Category category, Category updateCategory) {
    category.setName(updateCategory.getName());
    return category;
  }
}
