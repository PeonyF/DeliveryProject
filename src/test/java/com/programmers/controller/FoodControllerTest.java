package com.programmers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.domain.Food;
import com.programmers.dto.food.FoodResponseDto;
import com.programmers.repository.food.FoodRepository;
import com.programmers.service.food.FoodService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class FoodControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FoodService foodService;

    @BeforeEach
    void clean() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FoodController(foodService)).build();
        foodRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 db에 저장이 된다.")
    void saveFood() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        Food food = basicFoodData();
        Food newFood = foodRepository.save(food);

        String json = objectMapper.writeValueAsString(newFood);

        //when
        mockMvc.perform(post("/foods/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1, foodRepository.count());
        Food getFood = foodRepository.findAll().get(0);
        assertEquals("맛있는라면", getFood.getDescription());
        assertEquals(1000, getFood.getPrice());
    }


    @Test
    @DisplayName("글 1개 조회")
    void searchFoodById() throws Exception {
        //given
        Food food = basicFoodData();
        Food newFood = foodService.save(food);

        //when,then

        mockMvc.perform(get("/foods/{id}", newFood.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(String.valueOf(newFood.getId())))
                .andExpect(jsonPath("$.name").value("라면"))
                .andExpect(jsonPath("$.price").value(1000));
    }


    @Test
    @DisplayName("음식 이름 포함 테스트")
    public void searchFoodContainNameTest() throws Exception {
        //given
        String name = "라면";
        List<FoodResponseDto> expectedFoods = new ArrayList<>();
        expectedFoods.add(FoodResponseDto.of(basicFoodData()));

        foodService.save(basicFoodData());

        //when,then
        mockMvc.perform(get("/foods/search?name={name}", name))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value(expectedFoods.get(0).getName()))
                .andExpect(jsonPath("$[0].price").value(expectedFoods.get(0).getPrice()))
                .andExpect(jsonPath("$[0].description").value(expectedFoods.get(0).getDescription()));
    }

    @Test
    @DisplayName("음식 내용 수정 테스트")
    void updateFood() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        Food food = dummyFoodData();
        Food savedFood = foodService.save(food);

        Long updatedId = savedFood.getId();
        String expectedName = "냉면";
        int expectedPrice = 5000;
        String expectedDescription = "시원한 냉면";

        Food updatedFood = Food.builder()
                .name(expectedName)
                .price(expectedPrice)
                .description(expectedDescription)
                .build();

        //when,then
        mockMvc.perform(put("/foods/{id}",updatedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFood)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("음식 삭제 테스트")
    void deleteId() throws Exception {
        //given
        Food food = basicFoodData();
        Food savedFood = foodRepository.save(food);

        //expected
        mockMvc.perform(delete("/foods/{id}", savedFood.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(0L, foodRepository.count());
    }

    @Test
    @DisplayName("존재하지 않는 음식 조회")
    void getNotExistPost() throws Exception {
        mockMvc.perform(get("/foods/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    private Food basicFoodData() {
        return Food.builder()
                .id(1L)
                .price(1000)
                .description("맛있는라면")
                .name("라면")
                .build();
    }

    private Food dummyFoodData() {
        return Food.builder()
                .id(2L)
                .price(2000)
                .description("매콤한라면")
                .name("신라면")
                .build();
    }
}