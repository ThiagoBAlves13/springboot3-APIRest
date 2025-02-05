package med.voll.api.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
	
	@Autowired
    PacienteRepository pacienteRepo;

	public Paciente salvar(DadosCadastroPaciente dados) {
		return pacienteRepo.save(new Paciente(dados));
	}

	public Page<DadosListagemPaciente> listar(Pageable paginacao) {
		return pacienteRepo.findAll(paginacao).map(DadosListagemPaciente::new);
	}

}
