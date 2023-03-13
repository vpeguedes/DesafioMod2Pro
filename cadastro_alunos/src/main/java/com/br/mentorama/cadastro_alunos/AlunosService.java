package com.br.mentorama.cadastro_alunos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunosService {

    private final List<Aluno> alunos;

    public AlunosService() {
        this.alunos = new ArrayList<>();
    }

    public List<Aluno> findAll(String nome, Integer idade) throws AlunoNotFoundException {
        List<Aluno> filtroAlunos = alunos;
        if(nome != null) {
            filtroAlunos = alunos.stream()
                    .filter(aln -> aln.getNome().contains(nome))
                    .collect(Collectors.toList());
            if(filtroAlunos.isEmpty()) {
                throw new AlunoNotFoundException("Aluno não encontrado");
            }
        }
        if(idade != null) {
            filtroAlunos = alunos.stream()
                    .filter(aln -> aln.getIdade().equals(idade))
                    .collect(Collectors.toList());
        }
        if(filtroAlunos.isEmpty()) {
            throw new AlunoNotFoundException("Aluno não encontrado");
        }
        return filtroAlunos;
    }

    public Aluno findById(Integer id) throws AlunoNotFoundException {
        return this.alunos.stream().filter(aln -> aln.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AlunoNotFoundException("Aluno não encontrado"));
    }

    public Aluno findByNome(String nome) {
        return this.alunos.stream().filter(aln -> aln.getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }

    public AlunosService addAluno(final Aluno aluno) {
        if(aluno.getId() == null) {
            aluno.setId(alunos.size() + 1);
        }
        alunos.add(aluno);
        return null;
    }

    public ResponseEntity updateAluno(final Aluno aluno) {
        alunos.stream()
                .filter(aln -> aln.getId().equals(aluno.getId()))
                .forEach(aln -> aln.setNome(aluno.getNome()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity deleteAluno(Integer id) {
        alunos.removeIf(aln -> aln.getId().equals(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
