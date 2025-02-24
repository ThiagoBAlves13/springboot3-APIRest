package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consultas")
public class Consulta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medico_id")
	private Medico medico;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_id")
	private Paciente paciente;

	private LocalDateTime data;
	
	@Column(name = "motivo_cancelamento")
	@Enumerated(EnumType.STRING)
	private MotivoCancelamento motivoCancelamento;

	public void cancelar(@NotNull MotivoCancelamento motivo) {
		this.motivoCancelamento = motivo;
	}
}
