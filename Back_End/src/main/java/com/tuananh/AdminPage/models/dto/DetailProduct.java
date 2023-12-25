package com.tuananh.AdminPage.models.dto;

import com.tuananh.AdminPage.entities.TblColorEntity;
import com.tuananh.AdminPage.entities.TblQuantityEntity;
import com.tuananh.AdminPage.entities.TblSizeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class DetailProduct {
    private int id;
    private int categoryId;
    List<TblSizeEntity> sizes;
    List<TblColorEntity> colors;
    List<TblQuantityEntity> quantities;
    private String sku;
    private String name;
    private String description;
    private String regularPrice;
    private String finalPrice;
    private String image;
    private String materials;
    private String instruction;
}