package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.consulta.ConsultaService;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

	@Autowired
	JacksonTester<DadosDetalhamentoConsulta> dadosDetallhamentoConsultaJson;
	
	@MockitoBean
	ConsultaService consultaService;

	@Test
	@WithMockUser
	@DisplayName("Deveria devolver código http 400 quando as informações estão inválidas")
	void agendar_cenario1() throws Exception {
		var response = mvc.perform(post("/consultas")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	@WithMockUser
	@DisplayName("Deveria devolver código http 200 quando as informações estão válidas")
	void agendar_cenario2() throws Exception {

		var data = LocalDateTime.now().plusHours(1);
		var especialidade = Especialidade.CARDIOLOGIA;
		var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);
		
		when(consultaService.agendar(any())).thenReturn(dadosDetalhamento);
		
		var response = mvc
				.perform(post("/consultas").contentType(MediaType.APPLICATION_JSON)
						.content(dadosAgendamentoConsultaJson
								.write(new DadosAgendamentoConsulta(2l, 5l, data, especialidade)).getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		var jsonEsperado = dadosDetallhamentoConsultaJson.write(dadosDetalhamento).getJson();
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}

}
