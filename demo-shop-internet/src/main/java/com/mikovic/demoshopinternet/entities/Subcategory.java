package com.mikovic.demoshopinternet.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "subcategories")
@Data
@NoArgsConstructor
//@ToString
public class Subcategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    @Size(min = 5, max = 250, message = "требуется минимум 5 символов")
    private String title;
    @Column(name = "description")
    @Size(min = 5, max = 250, message = "требуется минимум 5 символов")
    private String description;
    //mappedBy на другой стороне будет subcategory
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subcategory")
    @ToString.Exclude
    private List<Product> products;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinTable(
            name = "categories_subcategories",
            joinColumns = @JoinColumn(name = "subcategory_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
     private Category category;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinTable(
            name = "subcategories_images",
            joinColumns = @JoinColumn(name = "subcategory_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )

    private Image image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
