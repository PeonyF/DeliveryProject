package com.programmers.controller;

import com.programmers.domain.Food;
import com.programmers.dto.FoodRequestDto;
import com.programmers.dto.FoodResponseDto;
import com.programmers.service.FoodServiceImpl;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/foods")
public class FoodController {
    private final FoodServiceImpl foodServiceImpl;

      //음식 등록
    @PostMapping("/save")
    public void saveFood(@RequestBody Food food) {
        foodServiceImpl.save(food);
    }

    //음식 조회(foodId)
    @GetMapping("/searchById/{id}")
    public FoodResponseDto searchFoodById(@PathVariable Long id) {
        return foodServiceImpl.findById(id);
    }

    //음식 조회(포함 단어)
    @GetMapping("/search/{name}")
    public List<Food> searchFoodContainName(@PathVariable String name) {
        return foodServiceImpl.findByNameContaining(name);
    }

    //음식 수정
    @PutMapping("/update/{id}")
    public void updateFood(@PathVariable Long id, @RequestBody FoodRequestDto foodRequestDto) {
        foodServiceImpl.update(id, foodRequestDto);
    }

    //음식 삭제
    @DeleteMapping("/delete")
    public void deleteFoodId(@RequestBody Food food) {
        foodServiceImpl.delete(food);
    }
}
