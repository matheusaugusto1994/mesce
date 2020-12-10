package br.com.diocesesjc.mesce.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Paroquia {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String phone;
    private String address;

    @ManyToOne
    @JoinColumn(name = "regiao_id")
    private Regiao regiao;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;
}
