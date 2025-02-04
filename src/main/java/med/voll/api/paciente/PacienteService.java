package med.voll.api.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
	
	@Autowired
    PacienteRepository pacienteRepo;

	public Paciente salvar(DadosCadastroPaciente dados) {
		return pacienteRepo.save(new Paciente(dados));
	}

}
