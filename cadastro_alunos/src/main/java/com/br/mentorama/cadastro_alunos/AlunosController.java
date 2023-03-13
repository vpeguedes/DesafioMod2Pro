package com.br.mentorama.cadastro_alunos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cadastroalunos")
public class AlunosController {

    private final AlunosService alunosService;

    public AlunosController(AlunosService alunosService) {
        this.alunosService = alunosService;
    }

    @GetMapping
    public List<Aluno> findAll(@RequestParam(required = false) String nome, Integer idade) throws AlunoNotFoundException {
        try {
            return alunosService.findAll(nome, idade);
        } catch (AlunoNotFoundException notFoundException) {
            throw new AlunoNotFoundException("Aluno não encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody final Aluno aluno) {
        alunosService.addAluno(aluno);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Aluno findById(@PathVariable("id") Integer id) throws AlunoNotFoundException {
        try {
            return alunosService.findById(id);
        } catch (AlunoNotFoundException notFoundException) {
            throw new AlunoNotFoundException("Aluno não encontrado");
        }
    }

    @PutMapping
    public ResponseEntity updateAluno(@RequestBody final Aluno aluno) {
        alunosService.updateAluno(aluno);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeAluno(@PathVariable("id") Integer id) {
        alunosService.deleteAluno(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
