package med.voll.api.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class MedicoService {
	
	@Autowired
	MedicoRepository medicoRepository;

	public Medico salvar(DadosCadastroMedico dados) {
		return medicoRepository.save(new Medico(dados));
	}

	public Page<DadosListagemMedico> listar(Pageable paginacao) {
		return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
	}

	public Medico atualizar(DadosAtualizacaoMedico dados) {
		
		var medico = medicoRepository.findById(dados.id()).orElseThrow();
		medico.atualizarInformacoes(dados);
		return medico;
	}

	public void excluir(Long id) {	
		var medico = medicoRepository.findById(id).orElseThrow();
		medico.inativar();
	}

	public Medico detalhar(Long id) {
		return medicoRepository.findById(id).orElseThrow();
	}

}
