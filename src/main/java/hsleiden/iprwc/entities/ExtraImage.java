package hsleiden.iprwc.entities;

import javax.persistence.*;

@Entity
@Table(name="images_extra")
public class ExtraImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    @Column(name = "product")
    long product;
    @Column(name = "image_url")
    String image_url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "ExtraImage{" +
                "id=" + id +
                ", product=" + product +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
