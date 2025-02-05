package med.voll.api.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
	
	@Autowired
	MedicoRepository medicoRepository;

	public Medico salvar(DadosCadastroMedico dados) {
		return medicoRepository.save(new Medico(dados));
	}

	public Page<DadosListagemMedico> listar(Pageable paginacao) {
		return medicoRepository.findAll(paginacao).map(DadosListagemMedico::new);
	}

}
