package br.com.msacademico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "disciplinas")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String nome;

    @Column(nullable = false)
    private Integer cargaHoraria;

    @Column(length = 50)
    private String sigla;

    @Column(length = 500)
    private String descricao;

    @Column(name = "escola_id")
    private Long escolaId;

    @Column(name = "data_cadastro")
    private String dataCadastro;

    @Column(length = 20)
    private String status;

}
