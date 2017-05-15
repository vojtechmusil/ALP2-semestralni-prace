
package app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Ukol
 * @author Musil
 */
public class Task  implements Comparable<Task>  {
    private int id;
    private String name;
    private Date dateLimit;
    private LocalTime timeBudget;
    private LocalTime spentTime;
    private ArrayList<Employee> solvers;
    private int state; // 0 - created and in progress; 1 - done; 2 - after limit
    
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
    private static int taskCount = 0;
    
    public Task(String name, String limit, String timeBudget) throws ParseException {
        id = ++taskCount;
        state = 0;
        this.name = name;
        
        dateLimit = dateFormat.parse(limit);
        
        solvers = new ArrayList();
                
        this.timeBudget = LocalTime.parse(timeBudget, timeFormat);
        spentTime = LocalTime.parse("00:00:00", timeFormat);
    }
    
    // logics
    
    /**
    * Adds spent time on task
    * @param String; time to be added
    */
    public void addSpentTime(String addedTime) {
        spentTime = spentTime.plusSeconds(LocalTime.parse(addedTime, timeFormat).toSecondOfDay());
    }
    
    /**
    * Remove spent time on task
    * @param String; time to be removed
    */
    public void removeSpentTime(String removedTime) {
        spentTime = spentTime.minusSeconds(LocalTime.parse(removedTime, timeFormat).toSecondOfDay());
    }
    
     /**
    * Compares timebudget of task with already depleted time on task
    * @return int; 0 if same, >1 if budget is bigger, <1 if spent time is bigger
    */
    public int compareTimes() {
        return timeBudget.compareTo(spentTime);
    }
    
     /**
    * Set the task to 'done' state
    */
    public void completeTask() {
        state = 1;
    }
    
    /**
    * Adds solver of task
    * @param Employee; solver to be added
    */
    public void addSolver(Employee solver) {
        solvers.add(solver);
    }
    
    /**
    * Removes solver of task
    * @param Employee; solver to be removed
    */
    public void removeSolver(Employee solver) {
        solvers.remove(solver);
    }
    
    // getters
    
    /**
    * Returns id of task
    * @return int; id
    */
    public int getId() { return id; }

    /**
    * Returns name of task
    * @return String; name
    */
    public String getName() { return name; }
    
    /**
    * Returns time budget of task
    * @return String; time budget
    */
    public String getTimeBudget() { return timeBudget.format(timeFormat); }
    
    /**
    * Returns spent time on task
    * @return String; spent time
    */
    public String getSpentBudget() { return spentTime.format(timeFormat); }
    
    /**
    * Returns solvers of task
    * @return ArrayList; list of solvers
    */
    public ArrayList<Employee> getSolvers() { 
        if(solvers.isEmpty()) {
            return null;
        } else {
            return solvers;
        } 
    }
    
    /**
    * Returns date limit of task
    * @return String; date limit
    */
    public String getDateLimit() { return dateLimit.toString(); }
    
    /**
    * Returns state of task. Compares task date limit to today's date
    * @return int; return 2 if limit<today, return 0 or 1 if limit>=today 
    */
    public int getState() {
        if( dateLimit.compareTo(new Date()) >= 0 || state == 1) {
            return state;
        } else {
            return 2; 
        }
    }
    
    /**
    * Returns text form of state of task
    * @return String; text form of state
    */
    public String getStateString() {
        switch (this.getState()) {
            case 0:
                return "Vytvoren a zadan";
            case 1:
                return "Hotov";
            case 2: 
                return "Zpozden";
            default:
                return "Stav neznamy";
        }
    }
    
    @Override
    public String toString() {
        return id + " " + name + "; Casovy budget: " + timeFormat.format(timeBudget) + "; Spotrebovany cas: " + timeFormat.format(spentTime) + "; Splnit do: " + dateFormat.format(dateLimit) + "; Stav: " + this.getStateString();
    }

    @Override
    public int compareTo(Task o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
