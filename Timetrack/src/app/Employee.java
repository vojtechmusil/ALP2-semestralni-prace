
package app;

/**
 * Pracovník
 * @author Vojtěch Musil
 */
public class Employee implements Comparable<Employee> {
    
    private int id;
    private String name;
    
    private static int employeeCount = 0;
    
    public Employee(String name) {
        id = ++employeeCount;
        this.name = name;
    }
    
    // getters
    
    /**
    * Returns id of employee
    * @return int; id
    */
    public int getId() { return id; }
    
    /**
    * Returns name of employee
    * @return String; name
    */
    public String getName() { return name; }
    
    @Override
    public String toString() {
        return id + " " + name;
    }

    @Override
    public int compareTo(Employee o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
