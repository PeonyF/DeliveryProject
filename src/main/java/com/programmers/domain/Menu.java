package com.programmers.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne
    @JoinColumn(name = "storeId", foreignKey = @ForeignKey(name = "fk_menu_store"))
    private Store store;

    @ManyToOne
    @JoinColumn(name = "foodId", foreignKey = @ForeignKey(name = "fk_menu_food"))
    private Food food;


}
