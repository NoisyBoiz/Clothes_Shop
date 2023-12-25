package com.tuananh.AdminPage.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "tbl_product", schema = "dbo", catalog = "admin")
public class TblProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "category_id", nullable = false)
    private int categoryId;
    @Basic
    @Column(name = "size_id", nullable = false, length = 100)
    private String sizeId;
    @Basic
    @Column(name = "color_id", nullable = false, length = 100)
    private String colorId;
    @Basic
    @Column(name = "sku", nullable = false, length = 50)
    private String sku;
    @Basic
    @Column(name = "name", nullable = true)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = 2147483647)
    private String description;
    @Basic
    @Column(name = "regular_price", nullable = false, length = 50)
    private String regularPrice;
    @Basic
    @Column(name = "final_price", nullable = false, length = 50)
    private String finalPrice;
    @Basic
    @Column(name = "image", nullable = true, length = 2147483647)
    private String image;
    @Basic
    @Column(name = "materials", nullable = true, length = 2147483647)
    private String materials;
    @Basic
    @Column(name = "instruction", nullable = true, length = 2147483647)
    private String instruction;
    @Basic
    @Column(name = "deleted", nullable = true)
    private Boolean deleted;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Date createdAt;
    @Basic
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;
    @Basic
    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;

}
