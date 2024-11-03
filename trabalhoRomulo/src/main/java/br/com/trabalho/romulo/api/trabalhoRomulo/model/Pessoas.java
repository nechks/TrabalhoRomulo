package br.com.trabalho.romulo.api.trabalhoRomulo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name ="Pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pessoas {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    EnderecoPessoas endereco = new EnderecoPessoas();
    //private EnderecoPessoas endereco;
    private String nome;
    private Integer idade;


}
