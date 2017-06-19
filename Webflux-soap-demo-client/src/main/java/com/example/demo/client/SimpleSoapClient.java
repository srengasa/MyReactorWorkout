package com.example.demo.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.sudharsan.simpleservice.getdepartment.EmpDepartmentRequestType;
import org.sudharsan.simpleservice.getdepartment.EmpDepartmentResponseType;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import reactor.core.publisher.Mono;

public class SimpleSoapClient {

	public static void main(String[] args) {
		System.out.println("Start");
		//EmpDepartmentRequestType e = getRequest("400","sample", "FPT");
		GetCountryRequest req = getSpringRequest("Spain");
		
		Mono<GetCountryResponse> mono = WebClient.create().post()
				.uri("http://localhost:8080/ws/countries.wsdl")
		//.accept(MediaType.APPLICATION_XML)
		.body(BodyInserters.fromObject(req))
		.exchange()
		.flatMap(response -> response.bodyToMono(GetCountryResponse.class));
		
		System.out.println("********** " + mono.block());
		System.out.println("End");
	}

	private static GetCountryRequest getSpringRequest(String countryName) {
		GetCountryRequest req = new GetCountryRequest();
		req.setName(countryName);
		return req;
	}

	private static EmpDepartmentRequestType getRequest(String empId, String empName, String proj) {
		EmpDepartmentRequestType e = new EmpDepartmentRequestType();
		e.setEmpId(empId);
		e.setEmpName(empName);
		e.setEmpProj(proj);
		return e;
	}

}
