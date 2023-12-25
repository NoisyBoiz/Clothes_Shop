package com.tuananh.AdminPage.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Table(name = "tbl_color", schema = "dbo", catalog = "admin")
public class TblColorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "label", nullable = true, length = 50)
    private String label;
    @Basic
    @Column(name = "image", nullable = true, length = 2147483647)
    private String image;

}
