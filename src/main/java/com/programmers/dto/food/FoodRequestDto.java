package com.programmers.dto.food;

import com.programmers.domain.food.Food;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FoodRequestDto {
    @NotNull
    private Long id;
    @NotBlank
    @Length(min = 1, max = 30)
    private String name;
    @NotNull
    private Integer price;
    private String description;
    private String image;

    @Builder
    public FoodRequestDto(Long id, String name, Integer price, String description, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public static FoodRequestDto of(Food food){
        return FoodRequestDto.builder()
                .id(food.getId())
                .name(food.getName())
                .price(food.getPrice())
                .description(food.getDescription())
                .image(food.getImage())
                .build();
    }

    public Food toEntity() {
        return Food.builder()
                .id(id)
                .name(name)
                .price(price)
                .description(description)
                .image(image)
                .build();
    }
}
