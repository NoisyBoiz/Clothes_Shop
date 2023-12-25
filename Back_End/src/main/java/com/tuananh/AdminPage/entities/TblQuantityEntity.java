package com.tuananh.AdminPage.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "tbl_quantity", schema = "dbo", catalog = "admin")
public class TblQuantityEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "sku", nullable = true, length = 100)
    private String sku;
    @Basic
    @Column(name = "product_id")
    private int productId;
    @Basic
    @Column(name = "size_id", nullable = true, length = 100)
    private String sizeId;
    @Basic
    @Column(name = "color_id", nullable = true, length = 100)
    private String colorId;
    @Basic
    @Column(name = "value", nullable = true)
    private Long value;
    @Basic
    @Column(name = "media_gallery", nullable = true, length = 2147483647)
    private String mediaGallery;
    @Basic
    @Column(name = "deleted", nullable = true)
    private Boolean deleted;

}
