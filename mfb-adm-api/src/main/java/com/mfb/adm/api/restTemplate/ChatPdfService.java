package com.mfb.adm.api.restTemplate;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mfb.adm.comm.constants.ConstApp;
import com.mfb.adm.comm.constants.MsgApp;
import com.mfb.adm.comm.dtos.MessageChatPdfDto;
import com.mfb.adm.comm.dtos.RespuestaRestChatPdf;
import com.mfb.adm.comm.dtos.RespuestaRestTemplate;
import com.mfb.adm.comm.requests.SolicitudPreguntaChatPdf;

@Service
public class ChatPdfService implements IChatPdfService {

	@Value("${chatpdf.url}")
	private String URL;
	@Value("${chatpdf.token}")
	private String API_KEY;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

	@Override
	public RespuestaRestTemplate preguntar(String pregunta, String idLibro) {
		try {
			MessageChatPdfDto mensaje = MessageChatPdfDto.builder().role("user").content(pregunta).build();
			SolicitudPreguntaChatPdf solicitud = SolicitudPreguntaChatPdf.builder().sourceId(idLibro)
					.referenceSources(true).messages(Arrays.asList(mensaje)).build();
			String url = URL + "/chats/message";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("x-api-key", API_KEY);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			HttpEntity<String> requestBody = new HttpEntity<>(this.gson.toJson(solicitud), headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<RespuestaRestChatPdf> vResponse = restTemplate.postForEntity(builder.toUriString(),
					requestBody, RespuestaRestChatPdf.class);
			RespuestaRestTemplate respuesta = new RespuestaRestTemplate();
			if (vResponse.getStatusCodeValue() == ConstApp.HTTP_200_OK) {
				respuesta.setSuccess(true);
				respuesta.setMessage(MsgApp.RESPONSE_QUESTION_SUCCESS);
				respuesta.setContent(vResponse.getBody());
			} else {
				respuesta.setMessage(vResponse.getBody().getError());
			}

			return respuesta;
		} catch (Exception e) {
			RespuestaRestTemplate respuesta = new RespuestaRestTemplate();
			respuesta.setMessage(MsgApp.RESPONSE_CALL_SERVICE_ERROR + URL + " : " + e.getMessage());
			return respuesta;
		}
	}

}
