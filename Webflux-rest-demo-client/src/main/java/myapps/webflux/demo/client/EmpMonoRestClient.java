package myapps.webflux.demo.client;

import org.springframework.web.client.RestTemplate;

import myapps.webflux.demo.model.CombineResults;
import myapps.webflux.demo.model.Department;
import myapps.webflux.demo.model.Employee;
import reactor.core.publisher.Mono;

public class EmpMonoRestClient {

	public static void main(String[] args) {
		new EmpMonoRestClient().zipMonoCombinedResults();
	}

	private void zipMonoCombinedResults() {
		Mono<Integer> intMono = Mono.just(0);
		System.out.println("Zip Mono Combined Results");
		Mono<Department> deptMono = intMono.flatMap(value -> getDepartment(new Employee("Name", "1")));
		Mono<Employee> empMono = intMono.flatMap(value -> getEmployee(new Department("Coding", "2")));
		Mono<CombineResults> combinedResults = Mono.zip(this::combineResults, deptMono, empMono);
		CombineResults combinedResult = combinedResults.block();
		System.out.println(combinedResult);
	}
	
	private CombineResults combineResults(Object[] monos) {
		System.out.println("Inside Combined Results");
		CombineResults combineResults = new CombineResults();
		for (Object obj : monos) {
			System.out.println(obj);
			if (obj instanceof Department) {
				combineResults.setDepartment((Department)obj);
			} else if (obj instanceof Employee) {
				combineResults.setEmployee((Employee) obj);
			}
		}
		return combineResults;
	}
	private Mono<Employee> getEmployee(Department department) {
		Employee emp = new RestTemplate().postForEntity("http://localhost:9001/Department/getEmployee", department, Employee.class).getBody();
		System.out.println("Response from Employee service" + emp);
		return Mono.just(emp);
	}

	private Mono<Department> getDepartment(Employee e) {
		System.out.println("Department Service call*******");
		Department dept = new RestTemplate().postForEntity("http://localhost:9001/Employee/getDepartment", e, Department.class).getBody();
		System.out.println("Response from Department service" + dept);
		return Mono.just(dept);
	}

}
