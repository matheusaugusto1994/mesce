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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String password;
    @ManyToOne @JoinColumn(name="pessoa_id")
    private Pessoa pessoa;
}
