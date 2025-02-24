package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidadorMedicoComOutraConsulta implements ValidadorAgendamentoDeConsulta {

	@Autowired
	ConsultaRepository repository;

	public void validar(DadosAgendamentoConsulta dados) {

		var medicoConsultaNoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(),
				dados.data());

		if (medicoConsultaNoHorario)
			throw new ValidacaoException("Médico já possui outra consulta agendada no mesmo horário!");

	}
}
