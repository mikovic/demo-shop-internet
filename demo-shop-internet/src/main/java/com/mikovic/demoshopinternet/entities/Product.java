package com.mikovic.demoshopinternet.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Title not null")
    @Size(min = 6, message = "Title legth min 5 sym")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Description not null")
    @Size(min = 6, message = "Description legth min 5 sym")
    @Column(name = "description")
    private String description;

    @NotNull(message = "price not null")
    @Min(value = 1, message = "Min 1")
    @Column(name = "price")
    private double price;




    @ManyToOne
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.DETACH})
    private Category category;

    @OneToMany
    @JoinTable(
            name = "products_images",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.DETACH})
    private List<Image> images;

    public void addImage(Image image){
        if(images ==null){
            this.images = new ArrayList<>();
        }
        images.add(image);
    }
}
