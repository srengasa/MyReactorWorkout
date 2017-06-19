package myapps.webflux.demo.model;

public class CombineResults {

	private Department department;
	private Employee employee;
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@Override
	public String toString() {
		return "CombineResults [department=" + department + ", employee=" + employee + "]";
	}
	
}
