package com.douglas.eapp.dto;

import com.douglas.eapp.domain.Category;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

  private Integer id;
  @NotEmpty(message = "Cannot be null")
  @Length(min = 5, max = 80, message = "Size must be between 5 and 80 characters")
  private String name;

  public CategoryDTO(Category category) {
    id = category.getId();
    name = category.getName();
  }
}
