package jp.ac.keio.pro2finalproject.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;

@Data
@Entity
@Table(name = "furniture")
public class Furn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "amount")
    @NotNull
    private Integer amount;

    @Column(name = "leased_amount")
    @ColumnDefault("0")
    @NotNull
    private Integer leasedAmount = 0;

    @Column(name = "img_url")
    private String imgUrl;
}
