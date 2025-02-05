package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosAtualizacaoMedico;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoService;

@RestController
@RequestMapping("medicos")
public class MedicoController {

	@Autowired
	MedicoService medicoService;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {

		Medico medico = medicoService.salvar(dados);

		return ResponseEntity.created(null).body(medico);
	}

	@GetMapping
	public ResponseEntity<?> listar(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {

		Page<DadosListagemMedico> medicos = medicoService.listar(paginacao);

		return ResponseEntity.ok(medicos);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<?> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		
		Medico medico = medicoService.atualizar(dados);

		return ResponseEntity.created(null).body(medico);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluir(@PathVariable Long id){
		
		medicoService.excluir(id);
		
		return ResponseEntity.noContent().build();
	}

}
