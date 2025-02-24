package med.voll.api.domain.consulta.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioAntecedenciaCancelamento implements ValidadorCancelamentoDeConsulta {

	@Autowired
    private ConsultaRepository repository;
	
	@Override
	public void validar(DadosCancelamentoConsulta dados) {

		var consulta = repository.getReferenceById(dados.idConsulta());
		var diferencaHorario = Duration.between(LocalDateTime.now(), consulta.getData()).toHours();
		
		if(diferencaHorario < 24)
			throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
		
	}

}
