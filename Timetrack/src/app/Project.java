
package app;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Projekt
 * @author Musil
 */
public class Project implements Comparable<Project>  {
    
    private int id;
    private String name;
    private Employee creator;
    private LocalTime timeBudget;
    private ArrayList<Task> tasks;
    private ArrayList<Employee> supervisors;
    
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static int projectCount = 0;
    
    public Project(String name, String timeBudget, ArrayList<Employee> employees, int creatorId) {
        id = ++projectCount;
        this.name = name;
        this.timeBudget = LocalTime.parse(timeBudget, timeFormat);
        
        for (Employee checkedEmployee : employees) {
            if( checkedEmployee.getId() == creatorId ) {
                creator = checkedEmployee;
            }
        }
        
        tasks = new ArrayList();
        supervisors = new ArrayList();
        supervisors.add(creator);
    }
    
    // logics
    
    /**
    * Compares timebudget of project with already depleted time on project's tasks
    * @return int; 0 if same, >1 if budget is bigger, <1 if spent time is bigger
    */
    public int compareTimes() {
        return timeBudget.compareTo(LocalTime.parse(getSpentBudget(), timeFormat));
    }
    
    /**
    * Creates a new task
    * @param String name - name of project
    * @param String limit - date to which the task should be done
    * @param String timeBudget - time budget for project
    */
    public void createTask(String name, String limit, String timeBudget) throws ParseException {
        tasks.add(new Task(name, limit, timeBudget));
    }
    
    /**
    * Adds a supervisor for project
    * @param Employee addedEmployee - employee added to list
    */
    public void addSupervisor(Employee addedEmployee) {
        for (Employee supervisor : supervisors) {
            if(supervisor.getId() == addedEmployee.getId()) {
                return;
            }
        }
        
        supervisors.add(addedEmployee);
    }
    
    /**
    * Removes a supervisor from project
    * @param Employee removedEmployee - employee removed from list
    */
    public void removeSupervisor(Employee removedEmployee) {
        for (Employee supervisor : supervisors) {
            if(supervisor.getId() == removedEmployee.getId()) {
                supervisors.remove(supervisor);
                return;
            }
        }
    }
    
    // getters
    
    /**
    * Removes a supervisor from project
    * @return int; id of project
    */
    public int getId() { return id; }

    /**
    * Returns name of project
    * @return String; name of project
    */
    public String getName() { return name; }
    
    /**
    * Returns time budget of project
    * @return String; time budget
    */
    public String getTimeBudget() { return timeBudget.format(timeFormat); }
    
    /**
    * Returns supervisors of project
    * @return ArrayList; list of employees
    */
    public ArrayList<Employee> getSupervisors() { return supervisors; }
    
    /**
    * Returns spent time on project. Goes through projects task.
    * @return String; spent time
    */
    public String getSpentBudget() {
        LocalTime spentTime = LocalTime.parse("00:00:00", timeFormat);
        
        for (Task task : tasks) {
            spentTime = spentTime.plusSeconds(LocalTime.parse(task.getSpentBudget(), timeFormat).toSecondOfDay());
        }
        
        return spentTime.format(timeFormat);
    }
    
    /**
    * Returns creator of project
    * @return Employee; creator
    */
    public Employee getCreator() { return creator; }
    
    /**
    * Returns tasks of project
    * @return ArrayList; list of project's tasks
    */
    public ArrayList<Task> getTasks() { 
        if(tasks.isEmpty()) {
            return null;
        } else {
            return tasks;
        } 
    }
    
    @Override
    public String toString() {
        return id + " " + name + "; Casovy budget: " + timeFormat.format(timeBudget) + "; Spotrebovany cas: " + this.getSpentBudget() + "; Hl. spravce: " + creator.getName(); 
    }

    @Override
    public int compareTo(Project o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
