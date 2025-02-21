package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;

@Service
public class ConsultaService {

	@Autowired
	ConsultaRepository consultaRepository;

	@Autowired
	MedicoRepository medicoRepository;

	@Autowired
	PacienteRepository pacienteRepository;
	
	@Autowired
	List<ValidadorAgendamentoDeConsulta> validadores;

	public void agendar(DadosAgendamentoConsulta dados) {

		if (pacienteRepository.existsById(dados.idPaciente()))
			throw new ValidacaoException("Id do paciente informado não existe!");

		if (dados.idMedico() != null && medicoRepository.existsById(dados.idMedico()))
			throw new ValidacaoException("Id do médico informado não existe!");
		
		validadores.forEach(v -> v.validar(dados));

		Medico medico = escolherMedico(dados);
		Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());

		Consulta consulta = new Consulta(null, medico, paciente, dados.data(), null);

		consultaRepository.save(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {

		if (dados.idMedico() != null)
			return medicoRepository.getReferenceById(dados.idMedico());

		if (dados.especialidade() == null)
			throw new ValidacaoException("Especialidade obrigatório quando médico não for escolhido!");

		return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
	}

	public void cancelar(@Valid DadosCancelamentoConsulta dados) {

		if (!consultaRepository.existsById(dados.idConsulta())) {
			throw new ValidacaoException("Id da consulta informado não existe!");
		}

		var consulta = consultaRepository.getReferenceById(dados.idConsulta());
		consulta.cancelar(dados.motivo());

	}

}
