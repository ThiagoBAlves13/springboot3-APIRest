package med.voll.api.paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosAtualizacaoMedico;

@Service
public class PacienteService {
	
	@Autowired
    PacienteRepository pacienteRepo;

	public Paciente salvar(DadosCadastroPaciente dados) {
		return pacienteRepo.save(new Paciente(dados));
	}

	public Page<DadosListagemPaciente> listar(Pageable paginacao) {
		return pacienteRepo.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
	}

	public Paciente atualizar(DadosAtualizacaoPaciente dados) {
		var paciente = pacienteRepo.findById(dados.id()).orElseThrow();
		paciente.atualizarInformacoes(dados);
		return paciente;
	}

	public void excluir(Long id) {
		var paciente = pacienteRepo.findById(id).orElseThrow();
		paciente.inativar();
	}

	public Paciente detalhar(Long id) {
		return pacienteRepo.findById(id).orElseThrow();
	}

}
