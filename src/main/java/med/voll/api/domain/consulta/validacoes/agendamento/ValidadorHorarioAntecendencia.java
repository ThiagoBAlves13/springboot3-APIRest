package med.voll.api.domain.consulta.validacoes.agendamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioAntecendencia implements ValidadorAgendamentoDeConsulta{
	
	public void validar(DadosAgendamentoConsulta dados) {
		var dataConsulta = dados.data();
		var agora = LocalDateTime.now();
		var diferencaMinuto = Duration.between(agora, dataConsulta).toMinutes();
		
		if(diferencaMinuto < 30)
			throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
	}

}
