package br.com.trabalho.romulo.api.trabalhoRomulo.controller;

import br.com.trabalho.romulo.api.trabalhoRomulo.model.EnderecoPessoas;
import br.com.trabalho.romulo.api.trabalhoRomulo.model.EnderecoRepository;
import br.com.trabalho.romulo.api.trabalhoRomulo.model.PessoaRepositery;
import br.com.trabalho.romulo.api.trabalhoRomulo.model.Pessoas;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PessoaController {
    @Autowired
    private PessoaRepositery pessoaRepositery;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping("/pessoas")
    public String mostrarForm(Model model) {
        Pessoas pessoa = new Pessoas();
        pessoa.setEndereco(new EnderecoPessoas());
        EnderecoPessoas endereco = new EnderecoPessoas();
        model.addAttribute(pessoa);
        model.addAttribute(endereco);
        return "formPessoa";
    }

    @PostMapping(value = "/pessoas", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String adicionarPessoa(Pessoas pessoa, RedirectAttributes redirectAttributes) {
        EnderecoPessoas enderecoSalvo = enderecoRepository.save(pessoa.getEndereco());
        pessoa.setEndereco(enderecoSalvo);
        pessoaRepositery.save(pessoa);

        redirectAttributes.addFlashAttribute("mensagem", "Pessoa e endereço adicionados com sucesso!");
        return "redirect:/pessoas/listarPessoas";
    }

    @GetMapping("pessoas/listarPessoas")
    public String listarPessoas(Model model) {
        List<Pessoas> pessoas = pessoaRepositery.findAll();
        // List<EnderecoPessoas> enderecos = enderecoRepository.findAll();
        model.addAttribute("pessoas", pessoas);
        return "pessoas/listarPessoas";
    }

    @PostMapping("/limparDados")
    public String limparDados(RedirectAttributes redirectAttributes) {
        pessoaRepositery.deleteAll();
        enderecoRepository.deleteAll();
        // Adiciona uma mensagem de confirmação para exibição após o redirecionamento
        redirectAttributes.addFlashAttribute("mensagem", "Todos os dados foram apagados com sucesso!");
        return "redirect:/pessoas";
    }

    @PostMapping("/pessoas/deletar/{id}")
    public String SelecionarDadosParaDeletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (pessoaRepositery.existsById(id)) {
            pessoaRepositery.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Pessoa deletada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagem", "Pessoa não encontrada!");
        }
        return "redirect:/pessoas/listarPessoas";
    }

    @PostMapping("/pessoas/editar/{id}")
    public String editarPessoa(@PathVariable Long id, Pessoas pessoaAtualizada, RedirectAttributes redirectAttributes) {
        Pessoas pessoaExistente = pessoaRepositery.findById(id).orElse(null);

        if (pessoaExistente == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Pessoa não encontrada para edição!");
            return "redirect:/pessoas/listarPessoas";
        }

        pessoaExistente.setNome(pessoaAtualizada.getNome());
        pessoaExistente.setIdade(pessoaAtualizada.getIdade());

        if (pessoaAtualizada.getEndereco() != null) {
            EnderecoPessoas endereco = pessoaExistente.getEndereco();
            if (endereco == null) {
                endereco = new EnderecoPessoas();
                pessoaExistente.setEndereco(endereco);
            }
            endereco.setRua(pessoaAtualizada.getEndereco().getRua());
            endereco.setCidade(pessoaAtualizada.getEndereco().getCidade());
            endereco.setEstado(pessoaAtualizada.getEndereco().getEstado());
            endereco.setCep(pessoaAtualizada.getEndereco().getCep());
        }

        pessoaRepositery.save(pessoaExistente);
        redirectAttributes.addFlashAttribute("mensagem", "Pessoa editada com sucesso!");
        return "redirect:/pessoas/listarPessoas";
    }

    @GetMapping("/pessoas/editar/form/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Pessoas pessoa = pessoaRepositery.findById(id).orElse(null);

        if (pessoa == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Pessoa não encontrada!");
            return "redirect:/pessoas/listarPessoas";
        }

        model.addAttribute("pessoa", pessoa);
        return "editarPessoa";
    }


    

}
