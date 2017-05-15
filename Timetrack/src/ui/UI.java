
package ui;

import java.util.ArrayList;

import app.Employee;
import app.Project;
import app.Task;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Semestrální práce ALP2
 * Timetracking
 * @author Vojtěch Musil
 * @version build 1
 */
public class UI {
    
    private static ArrayList<Employee> employees = null;
    private static ArrayList<Project> projects = null;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws ParseException {
        int end = 1;
        int state = 0;
        int subState = 0;
        
        employees = new ArrayList();
        projects = new ArrayList();
        
        while(end != 0) {
            switch(state) {
                // employee state
                case 3: 
                    switch(subState) {
                        // show employee info
                        case 2: 
                            showEmployeeInfo();
                            subState = 0;
                            break;
                        
                        // add employee
                        case 1: 
                            addEmployee();
                            subState = 0;
                            break;
                        
                        // project menu    
                        default: 
                            employeesMenu();
                            subState = readChoice();
                            if(subState == 0) { state = subState; }
                    }
                    break;
                    
                // task state
                case 2:
                    switch(subState) {
                        // add task solver
                        case 4:
                            addTaskSolver();
                            subState = 0;
                            break;
                        
                        // complete task
                        case 3: 
                            completeTask();
                            subState = 0;
                            break;
                            
                        // add time
                        case 2: 
                            addTimeToTask();
                            subState = 0;
                            break;
                        
                        // add project
                        case 1: 
                            taskCreation();
                            subState = 0;
                            break;
                        
                        // project menu    
                        default: 
                            tasksMenu();
                            subState = readChoice();
                            if(subState == 0) { state = subState; }
                    }
                    break;
                    
                // project state
                case 1: 
                    switch(subState) {
                        // add project supervisor
                        case 3: 
                            addProjectSupervisor();
                            subState = 0;
                            break;
                            
                        // show project tasks
                        case 2: 
                            showProjectTasks();
                            subState = 0;
                            break;
                        
                        // add project
                        case 1: 
                            projectCreation();
                            subState = 0;
                            break;
                        
                        // project menu    
                        default: 
                            projectsMenu();
                            subState = readChoice();
                            if(subState == 0) { state = subState; }
                    }
                    break;
                
                // start state
                default: 
                    mainMenu();
                    state = readChoice();
                    if(state == 0) { end = state; }
            }
        }
    }
    
    // main menus
    
    public static int readChoice() {
        String tryChoice;
        int choice;
        
        while(true) {
            System.out.print("Volba: ");
            
            try {
                tryChoice = sc.nextLine();
                choice = Integer.parseInt(tryChoice);
                System.out.println();
                return choice;
            } 
            catch (NumberFormatException ne) {
                System.out.println("Spatny vstup, opakujte...");
            }
        }
    }
    
    public static void mainMenu() {
        System.out.println("1 - Projekty");
        System.out.println("2 - Ukoly");
        System.out.println("3 - Zamestnanci");
        System.out.println("0 - Konec");
        System.out.println();
    }
    
    public static void projectsMenu() {
        System.out.println("1 - Pridat projekt");
        System.out.println("2 - Zobrazit ukoly projektu");
        System.out.println("3 - Pridat spravce k projektu");
        System.out.println("0 - Zpet");
        System.out.println();
    }
    
    public static void tasksMenu() {
        System.out.println("1 - Pridat ukol k projektu");
        System.out.println("2 - Zapsat cas k ukolu");
        System.out.println("3 - Splnit ukol");
        System.out.println("0 - Zpet");
        System.out.println();
    }
    
    public static void employeesMenu() {
        System.out.println("1 - Vytvorit zamestnance");
        System.out.println("2 - Informace o zamestnanci");
        System.out.println("0 - Zpet");
        System.out.println();
    }
    
    // employees gets & shows & works
    
    public static void showEmployees() {
        for(Employee employee : employees) {
            System.out.println(employee.toString());
        }
        System.out.println();
    }
    
    public static void showEmployeesProjectsById(int id) {
        System.out.println(getEmployeeById(id).getName() + " - Projekty:");
        
        if (!projects.isEmpty()) {
            for(Project project : projects) {
                for (Employee supervisor : project.getSupervisors()) {
                    if(supervisor.getId() == id) {
                        System.out.println(project.toString());
                    }
                }
            }
        } else {
            System.out.println("Zadne vytvorene projekty");
        }
        
        System.out.println();
    }
    
    public static void showEmployeesTasksById(int id) {
        System.out.println(getEmployeeById(id).getName() + " - Ukoly:");
        
        if(!projects.isEmpty()) {
            for(Project project : projects) {
                if(project.getTasks() != null) {
                    for (Task task : project.getTasks()) {
                        if(task.getSolvers() != null) {
                            for (Employee solver : task.getSolvers()) {
                                if(solver.getId() == id) {
                                    System.out.println(task.toString());
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Zadne vytvorene ukoly");
                }
            }
        } else {
            System.out.println("Zadne vytvorene projekty a ukoly");
        }
        
        System.out.println();
    }
    
    public static Employee getEmployeeById(int id) {
        if(!employees.isEmpty()) {
            for(Employee employee : employees) {
                if( employee.getId() == id ) {
                    return employee;
                }
            }
            
            System.out.println("Zamestnanec nenalezen");
            return null;
        } else {
            System.out.println("Zadni zamestnanci neexistuji");
            return null;
        }
    }
    
    public static void showEmployeeInfo() {
        int employeeId;
        
        if(employees.isEmpty()) {
            System.out.println("Zadni existujici zamestnanci");
            System.out.println();
            return;
        }
        
        showEmployees();
        
        System.out.println("ID zamestnance: ");
        employeeId = readChoice();
        
        showEmployeesProjectsById(employeeId);
        
        showEmployeesTasksById(employeeId);
    }
    
    public static void addEmployee() {
        String name;
        
        System.out.print("Jmeno zamestnance: ");
        name = sc.nextLine();
        System.out.println();
        
        employees.add(new Employee(name));
    }
    
    // projects gets & shows & works
    
    public static void showAllProjects() {
        if(!projects.isEmpty()) {
            for(Project project : projects) {
                System.out.println(project.toString());
            }
        } else {
            System.out.println("Zadne vytvorene projekty");
        }
        
        System.out.println();
    }
    
    public static Project getProjectById(int id) {
        if(!projects.isEmpty()) {
            for(Project project : projects) {
                if( project.getId() == id ) {
                    return project;
                }
            }
            
            System.out.println("Projekt nenalezen");
            return null;
        } else {
            System.out.println("Zadne vytvorene projekty");
            return null;
        }
    }
    
    public static void projectCreation() {
        String name;
        int creator;
        String timeBudget;
        
        System.out.print("Nazev projektu: ");
        name = sc.nextLine();
        System.out.println();
        
        System.out.print("Casovy budget (HH:mm:ss): ");
        timeBudget = sc.nextLine();
        System.out.println();
        
        showEmployees();
        System.out.print("ID spravce projektu: ");
        creator = readChoice();
        
        projects.add(new Project(name, timeBudget, employees, creator));
    }
    
    public static void addProjectSupervisor() {
        int employeeId;
        int projectId;
        
        if(projects.isEmpty()) { 
            System.out.println("Zadne vytvorene projekty");
            System.out.println();
            return; 
        }
        
        showEmployees();
        
        System.out.println("ID zamestnance: ");
        employeeId = readChoice();
        
        showAllProjects();
        
        System.out.println("ID projektu: ");
        projectId = readChoice();
        
        getProjectById(projectId).addSupervisor(getEmployeeById(employeeId));
    }
    
    public static void showProjectTasks() {
        if(projects.isEmpty()) {
            System.out.println("Zadne vytvorene projekty");
            System.out.println();
            return;
        }
        
        showAllProjects();
        showProjectTasksById(readChoice());
    }
    
    // tasks gets & shows & works
    
    public static void showProjectTasksById(int id) {
        if(!projects.isEmpty()) {
            for(Project project : projects) {
                if( project.getId() == id ) {
                    if(project.getTasks() != null) {
                        for (Task task : project.getTasks()) {
                            System.out.println(task.toString());
                        }
                    } else {
                        System.out.println("V projektu nejsou zadne ukoly");
                    }
                }
            }
        } else {
            System.out.println("Zadne vytvorene projekty");
        }
        
        
        System.out.println();
    }
    
    public static Task getProjectTaskById(Project project, int id) {
        if(project.getTasks() != null) {
            for(Task task : project.getTasks()) {
                if( task.getId() == id ) {
                    return task;
                }
            }
            
            System.out.println("Tento ukol neexistuje");
            return null;
        } else {
            System.out.println("V projektu nejsou zadne ukoly");
            return null;
        }
    }
    
    public static void taskCreation() throws ParseException {
        int projectId;
        String name;
        String limit; 
        String timeBudget;
        
        if(projects.isEmpty()) { 
            System.out.println("Zadne vytvorene projekty");
            System.out.println();
            return; 
        }
        
        showAllProjects();
        
        System.out.println("ID projektu: ");
        projectId = readChoice();
        
        System.out.print("Nazev ukolu: ");
        name = sc.nextLine();
        System.out.println();
        
        System.out.print("Termin splneni (dd/MM/YYYY): ");
        limit = sc.nextLine();
        System.out.println();
        
        System.out.print("Casovy budget (HH:mm:ss): ");
        timeBudget = sc.nextLine();
        System.out.println();
        
        getProjectById(projectId).createTask(name, limit, timeBudget);
    }
    
    public static void addTimeToTask() {
        int projectId;
        int taskId;
        String addedTime;
        
        if(projects.isEmpty()) { 
            System.out.println("Zadne vytvorene projekty");
            System.out.println();
            return; 
        }
        
        System.out.println("Vyberte projekt: ");
        showAllProjects();
        projectId = readChoice();
        
        if(getProjectById(projectId).getTasks().isEmpty()) { 
            System.out.println("Zadne vytvorene ukoly");
            System.out.println();
            return; 
        }
        
        System.out.println("Vyberte ukol: ");
        showProjectTasksById(projectId);
        taskId = readChoice();
        
        System.out.print("Zapsany cas(HH:mm:ss): ");
        addedTime = sc.nextLine();
        
        getProjectTaskById(getProjectById(projectId), taskId).addSpentTime(addedTime);
    }
    
    public static void completeTask() {
        int projectId;
        int taskId;
        
        if(projects.isEmpty()) { 
            System.out.println("Zadne vytvorene projekty");
            System.out.println();
            return; 
        }
        
        System.out.println("Vyberte projekt: ");
        showAllProjects();
        projectId = readChoice();
        
        if(getProjectById(projectId).getTasks().isEmpty()) { 
            System.out.println("Zadne vytvorene ukoly");
            System.out.println();
            return; 
        }
        
        System.out.println("Vyberte ukol: ");
        showProjectTasksById(projectId);
        taskId = readChoice();
        
        getProjectTaskById(getProjectById(projectId), taskId).completeTask();
    }
    
    public static void addTaskSolver() {
        int employeeId;
        int projectId;
        int taskId;
        
        if(employees.isEmpty()) { 
            System.out.println("Zadni existujici zamestnanci");
            System.out.println();
            return; 
        }
        
        showEmployees();
        
        System.out.println("ID zamestnance: ");
        employeeId = readChoice();
        
        showAllProjects();
        
        System.out.println("ID projektu: ");
        projectId = readChoice();
        
        if(getProjectById(projectId).getTasks().isEmpty()) { 
            System.out.println("Zadne vytvorene ukoly");
            System.out.println();
            return; 
        }
        
        showProjectTasksById(projectId);
        
        System.out.println("ID ukolu: ");
        taskId = readChoice();
        
        getProjectTaskById(getProjectById(projectId), taskId).addSolver(getEmployeeById(employeeId));
    }
    
}
