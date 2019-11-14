package com.mikovic.demoshopinternet.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="subcategory_id", referencedColumnName = "id" )
    @ToString.Exclude
    private Subcategory subcategory;

    @NotNull(message = "Title not null")
    @Size(min = 4, message = "Title legth min 4 sym")
    @Column(name = "title")
    private String title;
//сущность brand уже существует и ее не надо запоминать
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name="brand_id", referencedColumnName = "id")
    private Brand brand;

    @NotNull(message = "Description not null")
    @Size(min = 4, message = "Description legth min 4 sym")
    @Column(name = "description")
    private String description;



    @NotNull(message = "price not null")
    @Min(value = 1, message = "Min 1")
    @Column(name = "price")
    private double price;
    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinTable(
            name = "categories_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.DETACH})
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "products_images",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images;

    public void addImage(Image image){
        if(images ==null){
            this.images = new ArrayList<>();
        }
        images.add(image);
    }
}
