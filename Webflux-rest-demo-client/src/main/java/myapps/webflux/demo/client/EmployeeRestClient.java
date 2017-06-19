package myapps.webflux.demo.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import myapps.webflux.demo.model.CombineResults;
import myapps.webflux.demo.model.Department;
import myapps.webflux.demo.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EmployeeRestClient {

	public static void main(String[] args) {
		//getDepartment(new Employee("emp","2"), 1);
		//callService();
		System.out.println("Start");
		zipService();
	}
	
	public static void callService() {
		Flux<Department> flux = Flux.range(0, 1).flatMap(value -> getDepartment(new Employee(value + "_name", "" + value), value))
				.doOnComplete(EmployeeRestClient::onComplete);
				
				Flux<Department> fluxs = Flux.just(new Department());
				fluxs = fluxs.flatMap(value->getDepartment(new Employee("_name", "asas"), 2));
				
				//Flux<List<Department>> fluxzip = flux.zip(flux, fluxs).map(pair -> collectDeptList(pair.getT1(), pair.getT2()));
				
				List<Department> depts = flux.collectList().block();
				System.out.println("Call 1********** Over");
				List<Department> dept1 = fluxs.collectList().block();
				System.out.println("Call 2********** Over" + dept1);
				System.out.println(depts);
	}
	
	public static void zipService() {
		System.out.println("inside Zip service");
		Flux<Department> departmentFlux = Flux.range(0, 1).flatMap(value -> getDepartment(new Employee(value + "_name", "" + value), value))
				.doOnComplete(EmployeeRestClient::onComplete);
		System.out.println("Department flux over");
		Flux<Employee> empFlux = Flux.range(0, 1).flatMap(value -> getEmployee(new Department(value + "_name", "" + value), value))
				.doOnComplete(EmployeeRestClient::onEmployeeComplete);
		System.out.println("Employee flux");
		Flux<CombineResults> combinedResults = Flux.zip(departmentFlux, empFlux).flatMap(pair -> combineResults(pair.getT1(), pair.getT2()));
		System.out.println("Combined results back*******");
		List<CombineResults> combinedResultsList = combinedResults.collectList().block();
		
		System.out.println(combinedResultsList);
	}
	
	private static Mono<CombineResults> combineResults(Department t1, Employee t2) {
		CombineResults combineResults = new CombineResults();
		combineResults.setDepartment(t1);
		combineResults.setEmployee(t2);
		return Mono.just(combineResults);
	}

	private static Mono<Employee> getEmployee(Department dept, int value) {
		Employee emp = new RestTemplate().postForEntity("http://localhost:9001/Department/getEmployee", dept, Employee.class).getBody();
		System.out.println("Response from Employee service" + emp);
		//System.out.println(mono.block());
		return Mono.just(emp);
	}

	private static List<Department> collectDeptList(Department t1, Department t2) {
		List<Department> list = new ArrayList<>();
		list.add(t1);
		list.add(t2);
		return list;
	}

	public static Mono<Department> getDepartment(Employee e, int value) {

		System.out.println("Value*******" + value);
		//Mono<Department> mono = new RestTemplate().postForObject("http://localhost:9001/Employee/getDepartment", new Employee("1","Sudharsan"), Mono.just(new Department()).getClass());
		Department dept = new RestTemplate().postForEntity("http://localhost:9001/Employee/getDepartment", e, Department.class).getBody();
		System.out.println("Response from Department service" + dept);
		//System.out.println(mono.block());
		return Mono.just(dept);
	}
	
	public static void onComplete() {
		System.out.println("Department Request Completed");
	}
	public static void onEmployeeComplete() {
		System.out.println("Employee Request Completed");
	}
}
