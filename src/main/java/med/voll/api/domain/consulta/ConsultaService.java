package med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void agendar(DadosAgendamentoConsulta dados) {
		
		if(pacienteRepository.existsById(dados.idPaciente()))
			throw new ValidacaoException("Id do paciente informado não existe!");
		
		if(dados.idMedico() != null && medicoRepository.existsById(dados.idMedico()))
			throw new ValidacaoException("Id do médico informado não existe!");
		
		Medico medico = escolherMedico(dados);		
		Paciente paciente = pacienteRepository.findById(dados.idPaciente()).get();
		
		Consulta consulta = new Consulta(null, medico, paciente, dados.data());
		
		consultaRepository.save(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {

		if(dados.idMedico() != null)
			return medicoRepository.findById(dados.idMedico()).get();
		
		
		return null;
	}
	
}
