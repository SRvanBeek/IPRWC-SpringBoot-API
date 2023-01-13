package hsleiden.iprwc.entities;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name="images_extra")
@SQLDelete(sql = "UPDATE images_extra SET enabled = false WHERE product=?")
@Where(clause = "enabled=true")
public class ExtraImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    @ManyToOne
    @JoinColumn(name="product")
    Product product;
    @Column(name = "image_url")
    String image_url;

    @Column(name = "enabled")
    boolean enabled = Boolean.TRUE;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "ExtraImage{" +
                "id=" + id +
                ", product=" + product +
                ", image_url='" + image_url + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
