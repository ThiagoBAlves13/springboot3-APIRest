package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.ConsultaService;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;

@RestController
@RequestMapping("consultas")
public class ConsultaController {
	
	@Autowired
	ConsultaService consultaService;

	@PostMapping
	@Transactional
	public ResponseEntity<?> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
		
		consultaService.agendar(dados);
		
		return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
	}
	
	@Transactional
	@DeleteMapping
	public ResponseEntity<?> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados){
		
		consultaService.cancelar(dados);
		
		return ResponseEntity.noContent().build();
	}
}
