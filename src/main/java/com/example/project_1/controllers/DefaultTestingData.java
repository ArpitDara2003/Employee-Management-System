package com.example.project_1.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.project_1.dataAccessLayer.UpdateCalendarEvent;
import com.example.project_1.dataAccessLayer.UpdateCompany;
import com.example.project_1.dataAccessLayer.UpdateEmployee;
import com.example.project_1.dataAccessLayer.UpdateLeave;
import com.example.project_1.dataAccessLayer.UpdateTask;
import com.example.project_1.dataModels.CalendarEvent;
import com.example.project_1.dataModels.Company;
import com.example.project_1.dataModels.Employee;
import com.example.project_1.dataModels.Leave;
import com.example.project_1.dataModels.LeaveBalance;
import com.example.project_1.dataModels.NewsItems;
import com.example.project_1.dataModels.PerformanceMetrics;
import com.example.project_1.dataModels.Task;

public class DefaultTestingData {

    public static void initialize() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Save mock company
            Company mockCompany = getMockCompany();
            UpdateCompany.saveOrUpdateCompany(mockCompany, session);

            // Save mock employees
            List<Employee> mockEmployees = getMockEmployees();
            for (Employee employee : mockEmployees) {
                UpdateEmployee.saveEmployeeToDB(employee, session);
            }

            // Save mock news
            List<NewsItems> mockNews = getMockNews();
            for (NewsItems news : mockNews) {
                session.beginTransaction();
                session.persist(news);
                session.getTransaction().commit();
            }

            // Save mock calendar events
            UpdateCalendarEvent updateCalendarEvent = new UpdateCalendarEvent();
            List<CalendarEvent> mockEvents = getDummyCalendarEvents();
            session.beginTransaction();
            for (CalendarEvent event : mockEvents) {
                updateCalendarEvent.updateCalendarEvent(session, event);
            }
            session.getTransaction().commit();

            // Save mock tasks
            List<Task> mockTasks = getMockTasks();
            session.beginTransaction();
            for (Task task : mockTasks) {
                UpdateTask.saveTaskToDB(task, session);
            }

            // Save mock leave balances
            List<LeaveBalance> mockLeaveBalances = getMockLeaveBalances();
            session.beginTransaction();
            for (LeaveBalance leaveBalance : mockLeaveBalances) {
                session.persist(leaveBalance);
            }
            session.getTransaction().commit();

            // Save mock leaves
            List<Leave> mockLeaves = getMockLeavesForEmployees();
            session.beginTransaction();
            for (Leave leave : mockLeaves) {
                UpdateLeave.saveLeave(session, leave);
            }
            session.getTransaction().commit();
        } catch (HibernateException hEx) {
            Logger logger = LoggerFactory.getLogger(DefaultTestingData.class);
            logger.error("Hibernate exception occurred while initializing default testing data", hEx);
        } catch (RuntimeException rEx) {
            Logger logger = LoggerFactory.getLogger(DefaultTestingData.class);
            logger.error("Runtime exception occurred while initializing default testing data", rEx);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(DefaultTestingData.class);
            logger.error("Unexpected error occurred while initializing default testing data", e);
        }
    }

    public static Company getMockCompany() {
        Company company = new Company();
        company.setCompanyId(1);
        company.setCompanyName("S.H.I.E.L.D.");
        company.setCompanyLogo("https://example.com/shield_logo.png");
        company.setCreatedAt(LocalDateTime.now().minusYears(5));
        company.setUpdatedAt(LocalDateTime.now());

        return company;
    }

    public static List<Employee> getMockEmployees() {
        List<Employee> employees = new ArrayList<>();

        // Team Leads
        employees.add(new Employee(1, "Nick Fury", "fury@shield.com", "Director", "Night Agents", "2010-01-01", "https://randomuser.me/api/portraits/men/1.jpg", "fury@123", "1234567890", "Active"));
        employees.add(new Employee(2, "Tony Stark", "tony@avengers.com", "Team Lead", "Avengers", "2012-05-01", "https://randomuser.me/api/portraits/men/2.jpg", "ironman@123", "1234567891", "Active"));
        employees.add(new Employee(3, "Peter Parker", "peter@shield.com", "Team Lead", "Spider Team", "2016-09-01", "https://randomuser.me/api/portraits/men/3.jpg", "spidey@123", "1234567892", "Active"));

        // Night Agents
        employees.add(new Employee(4, "Phil Coulson", "phil@shield.com", "Agent", "Night Agents", "2010-06-01", "https://randomuser.me/api/portraits/men/4.jpg", "phil@123", "1234567893", "Active"));
        employees.add(new Employee(5, "Maria Hill", "maria@shield.com", "Agent", "Night Agents", "2011-08-15", "https://randomuser.me/api/portraits/women/5.jpg", "maria@123", "1234567894", "Active"));
        employees.add(new Employee(6, "Daisy Johnson", "quake@shield.com", "Specialist", "Night Agents", "2013-05-12", "https://randomuser.me/api/portraits/women/6.jpg", "quake@123", "1234567895", "Active"));
        employees.add(new Employee(7, "Melinda May", "may@shield.com", "Pilot", "Night Agents", "2010-03-11", "https://randomuser.me/api/portraits/women/7.jpg", "may@123", "1234567896", "Active"));

        // Avengers
        employees.add(new Employee(8, "Steve Rogers", "steve@avengers.com", "Captain", "Avengers", "2011-07-04", "https://randomuser.me/api/portraits/men/8.jpg", "cap@123", "1234567897", "Active"));
        employees.add(new Employee(9, "Bruce Banner", "bruce@avengers.com", "Scientist", "Avengers", "2011-07-04", "https://randomuser.me/api/portraits/men/9.jpg", "hulk@123", "1234567898", "Active"));
        employees.add(new Employee(10, "Natasha Romanoff", "natasha@avengers.com", "Spy", "Avengers", "2012-03-01", "https://randomuser.me/api/portraits/women/10.jpg", "widow@123", "1234567899", "Active"));
        employees.add(new Employee(11, "Clint Barton", "clint@avengers.com", "Archer", "Avengers", "2011-05-06", "https://randomuser.me/api/portraits/men/11.jpg", "hawk@123", "1234567800", "Active"));
        employees.add(new Employee(12, "Thor Odinson", "thor@avengers.com", "God of Thunder", "Avengers", "2011-05-01", "https://randomuser.me/api/portraits/men/12.jpg", "mjolnir@123", "1234567801", "Active"));
        employees.add(new Employee(13, "Wanda Maximoff", "wanda@avengers.com", "Scarlet Witch", "Avengers", "2015-06-29", "https://randomuser.me/api/portraits/women/13.jpg", "chaos@123", "1234567802", "Active"));
        employees.add(new Employee(14, "Vision", "vision@avengers.com", "Synthezoid", "Avengers", "2015-07-01", "https://randomuser.me/api/portraits/men/14.jpg", "jarvis@123", "1234567803", "Active"));

        // Spider Team
        employees.add(new Employee(15, "Miles Morales", "miles@shield.com", "Web-Slinger", "Spider Team", "2018-01-15", "https://randomuser.me/api/portraits/men/15.jpg", "miles@123", "1234567804", "Active"));
        employees.add(new Employee(16, "Gwen Stacy", "gwen@shield.com", "Spider-Woman", "Spider Team", "2019-02-20", "https://randomuser.me/api/portraits/women/16.jpg", "gwen@123", "1234567805", "Active"));
        employees.add(new Employee(17, "Jessica Drew", "jessica@shield.com", "Spider-Woman", "Spider Team", "2020-03-10", "https://randomuser.me/api/portraits/women/17.jpg", "jessica@123", "1234567806", "Active"));
        employees.add(new Employee(18, "Cindy Moon", "cindy@shield.com", "Silk", "Spider Team", "2021-04-12", "https://randomuser.me/api/portraits/women/18.jpg", "silk@123", "1234567807", "Active"));
        employees.add(new Employee(19, "Ben Reilly", "ben@shield.com", "Scarlet Spider", "Spider Team", "2022-06-18", "https://randomuser.me/api/portraits/men/19.jpg", "scarlet@123", "1234567808", "Active"));

        // Guardians of the Galaxy
        employees.add(new Employee(20, "Peter Quill", "quill@guardians.com", "Star-Lord", "Guardians of the Galaxy", "2014-08-01", "https://randomuser.me/api/portraits/men/20.jpg", "star-lord@123", "1234567809", "Active"));
        employees.add(new Employee(21, "Gamora", "gamora@guardians.com", "Assassin", "Guardians of the Galaxy", "2014-08-01", "https://randomuser.me/api/portraits/women/21.jpg", "gamora@123", "1234567810", "Active"));
        employees.add(new Employee(22, "Drax", "drax@guardians.com", "Destroyer", "Guardians of the Galaxy", "2014-08-01", "https://randomuser.me/api/portraits/men/22.jpg", "destroyer@123", "1234567811", "Active"));
        employees.add(new Employee(23, "Rocket Raccoon", "rocket@guardians.com", "Weapons Expert", "Guardians of the Galaxy", "2014-08-01", "https://randomuser.me/api/portraits/men/23.jpg", "rocket@123", "1234567812", "Active"));
        employees.add(new Employee(24, "Groot", "groot@guardians.com", "Flora Colossus", "Guardians of the Galaxy", "2014-08-01", "https://randomuser.me/api/portraits/men/24.jpg", "groot@123", "1234567813", "Active"));

        return employees;
    }


    public static List<Task> getMockTasks() {
        List<Employee> employees = getMockEmployees();
        Company shieldCompany = getMockCompany();
        List<Task> tasks = new ArrayList<>();

        // Main Task: Save the USA (Continued)
        Task saveUSA = new Task("Save the USA", 
                                "A critical task to save the USA from impending doom.", 
                                10, 0, 100, "In Progress", LocalDate.now().plusDays(10), 
                                employees.get(0), shieldCompany); // Assigned to Nick Fury

        // Subtask: Save Los Angeles
        Task saveLA = new Task("Save Los Angeles", 
                            "A major task to save Los Angeles from the villainous forces.", 
                            8, 0, 50, "In Progress", LocalDate.now().plusDays(8), 
                            employees.get(4), shieldCompany); // Assigned to Phil Coulson
        
        // Subtask: Save New York City
        Task saveNYC = new Task("Save New York City", 
                                "A critical mission to protect NYC from the villain invasion.", 
                                9, 0, 60, "In Progress", LocalDate.now().plusDays(7), 
                                employees.get(8), shieldCompany); // Assigned to Steve Rogers
        
        // Subtask: Save California
        Task saveCalifornia = new Task("Save California", 
                                    "A huge operation to defend California from chaos and destruction.", 
                                    7, 0, 55, "In Progress", LocalDate.now().plusDays(9), 
                                    employees.get(5), shieldCompany); // Assigned to Maria Hill

        // Subtask: Save Texas
        Task saveTexas = new Task("Save Texas", 
                                "A state under attack from a powerful villain force.", 
                                8, 0, 60, "In Progress", LocalDate.now().plusDays(6), 
                                employees.get(6), shieldCompany); // Assigned to Black Widow
        
        // Subtask: Save Washington DC
        Task saveDC = new Task("Save Washington DC", 
                            "A mission to protect the nation's capital from an imminent attack.", 
                            9, 0, 70, "In Progress", LocalDate.now().plusDays(5), 
                            employees.get(7), shieldCompany); // Assigned to Captain Marvel
        
        // Subtasks of Save LA: Defeating Villains
        Task defeatVillainLA = new Task("Defeat Villain in LA", 
                                        "Stop the villain attacking Los Angeles.", 
                                        10, 0, 30, "In Progress", LocalDate.now().plusDays(5), 
                                        employees.get(6), shieldCompany); // Assigned to Melinda May
        saveLA.setSubTasks(Arrays.asList(defeatVillainLA));

        // Subtasks of Save NYC: Defeating Villains
        Task defeatVillainNYC = new Task("Defeat Villain in NYC", 
                                        "Stop the villain attacking New York City.", 
                                        9, 0, 30, "In Progress", LocalDate.now().plusDays(4), 
                                        employees.get(9), shieldCompany); // Assigned to Natasha Romanoff
        saveNYC.setSubTasks(Arrays.asList(defeatVillainNYC));

        // Subtasks of Save California: Defeating Villains
        Task defeatVillainCalifornia = new Task("Defeat Villain in California", 
                                                "Defeat the villain threatening California.", 
                                                8, 0, 40, "In Progress", LocalDate.now().plusDays(6), 
                                                employees.get(7), shieldCompany); // Assigned to Melinda May
        saveCalifornia.setSubTasks(Arrays.asList(defeatVillainCalifornia));

        // Subtasks of Save Texas: Defeating Villains
        Task defeatVillainTexas = new Task("Defeat Villain in Texas", 
                                        "Stop the villain threatening the great state of Texas.", 
                                        8, 0, 40, "In Progress", LocalDate.now().plusDays(3), 
                                        employees.get(6), shieldCompany); // Assigned to Black Widow
        saveTexas.setSubTasks(Arrays.asList(defeatVillainTexas));

        // Subtasks of Save Washington DC: Defeating Villains
        Task defeatVillainDC = new Task("Defeat Villain in Washington DC", 
                                        "Prevent the villain from attacking the nation's capital.", 
                                        10, 0, 50, "In Progress", LocalDate.now().plusDays(4), 
                                        employees.get(5), shieldCompany); // Assigned to Maria Hill
        saveDC.setSubTasks(Arrays.asList(defeatVillainDC));

        // Adding main tasks and subtasks to the tasks list
        tasks.add(saveUSA);
        tasks.add(saveLA);
        tasks.add(saveNYC);
        tasks.add(saveCalifornia);
        tasks.add(saveTexas);
        tasks.add(saveDC); 
        
        // Adding more villain-related tasks
        // Task: Stop Villain Invasion
        Task stopVillainInvasion = new Task("Stop Villain Invasion", 
                                            "A global threat with villains attacking cities worldwide.", 
                                            10, 0, 80, "In Progress", LocalDate.now().plusDays(12), 
                                            employees.get(1), shieldCompany); // Assigned to Thor
        
        Task defendCity = new Task("Defend City", 
                                "Defend a city from major villain attacks.", 
                                9, 0, 60, "In Progress", LocalDate.now().plusDays(11), 
                                employees.get(10), shieldCompany); // Assigned to Wanda Maximoff
        
        // Adding new tasks to the list
        tasks.add(stopVillainInvasion);
        tasks.add(defendCity);

        return tasks;
    }

    public static List<NewsItems> getMockNews() {
        List<NewsItems> newsList = new ArrayList<>();
        Company shield = getMockCompany();

        newsList.add(new NewsItems("New Initiative", "S.H.I.E.L.D. launches a new initiative.", "https://example.com/news1.jpg", LocalDateTime.now().minusDays(10).toString(), shield));
        newsList.add(new NewsItems("Avengers Assemble", "The Avengers are back in action!", "https://example.com/news2.jpg", LocalDateTime.now().minusDays(5).toString(), shield));
        newsList.add(new NewsItems("Spider Team Update", "Spider Team welcomes new members.", "https://example.com/news3.jpg", LocalDateTime.now().minusDays(2).toString(), shield));

        return newsList;
    }

    public static List<CalendarEvent> getDummyCalendarEvents() {
        List<Employee> employees = getMockEmployees();
        Company company = getMockCompany();
        List<CalendarEvent> events = new ArrayList<>();

        // 🎂 Birthdays for Employees
        for (Employee e : employees) {
            events.add(new CalendarEvent(
                e.getFirstName() + "'s Birthday",
                "SHIELD HQ - Celebration Room",
                LocalDate.of(2025, (int)(Math.random() * 12 + 1), (int)(Math.random() * 28 + 1)),
                "10:00",
                "12:00",
                "Birthday",
                e,
                company
            ));
        }

        // 🎉 Festivals
        events.add(new CalendarEvent(
            "Avengers Day",
            "Avengers Tower, NYC",
            LocalDate.of(2025, 4, 28),
            "09:00",
            "18:00",
            "Festival",
            null,
            company
        ));

        events.add(new CalendarEvent(
            "Shield Unity Fest",
            "SHIELD HQ Central Garden",
            LocalDate.of(2025, 6, 15),
            "15:00",
            "21:00",
            "Festival",
            null,
            company
        ));

        // 🥳 Parties
        events.add(new CalendarEvent(
            "Tony's Afterparty",
            "Stark Mansion",
            LocalDate.of(2025, 8, 18),
            "20:00",
            "02:00",
            "Party",
            null,
            company
        ));

        events.add(new CalendarEvent(
            "Black Widow's Farewell Bash",
            "SHIELD Rooftop",
            LocalDate.of(2025, 7, 10),
            "18:00",
            "23:00",
            "Party",
            null,
            company
        ));

        return events;
    }


    public static List<PerformanceMetrics> getMockPerformanceMetricsData() {
                List<Employee> employees = getMockEmployees();
        Company company = getMockCompany();
        List<PerformanceMetrics> allMetrics = new ArrayList<>();
        Random random = new Random();
        LocalDate lastMonth = LocalDate.now().withDayOfMonth(1).minusMonths(1); // Last full month

        for (Employee employee : employees) {
            int numberOfMonths = 5 + random.nextInt(16); // Random between 5 and 20

            for (int i = 0; i < numberOfMonths; i++) {
                LocalDate date = lastMonth.minusMonths(numberOfMonths - 1 - i);
                String formattedMonth = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                        + "'" + String.valueOf(date.getYear()).substring(2);

                int value = 70 + random.nextInt(30); // Random between 70 and 99
                int max = 100;

                PerformanceMetrics metric = new PerformanceMetrics("Efficiency", value, max, employee, company);
                metric.setTitle(formattedMonth); // Set month as title for time series
                allMetrics.add(metric);
            }
        }

        return allMetrics;
    }


    public static List<LeaveBalance> getMockLeaveBalances() {
        List<Employee> employees = getMockEmployees();
        Company company = getMockCompany();
        List<LeaveBalance> leaveBalances = new ArrayList<>();
        Random random = new Random();

        for (Employee employee : employees) {
            int annualTotal = 10 + random.nextInt(11); // 10–20
            int annualUsed = random.nextInt(annualTotal + 1);

            int sickTotal = 10 + random.nextInt(11);
            int sickUsed = random.nextInt(sickTotal + 1);

            int casualTotal = 10 + random.nextInt(11);
            int casualUsed = random.nextInt(casualTotal + 1);

            int approved = random.nextInt(6); // 0–5
            int applied = approved + random.nextInt(4); // approved ≤ applied

            LeaveBalance balance = new LeaveBalance(
                annualTotal,
                annualUsed,
                sickTotal,
                sickUsed,
                casualTotal,
                casualUsed,
                applied,
                approved,
                employee
            );
            balance.setCompany(company);
            leaveBalances.add(balance);
        }

        return leaveBalances;
    }

    private static final String[] LEAVE_TYPES = { "Annual", "Sick", "Casual" };
    private static final String[] STATUSES = { "Pending", "Approved", "Rejected" };
    private static final Random RANDOM = new Random();

    public static List<Leave> getMockLeavesForEmployees() {
        List<Employee> employees = getMockEmployees();
        Company company = getMockCompany();
        List<Leave> leaves = new ArrayList<>();

        for (Employee employee : employees) {
            int numberOfLeaves = RANDOM.nextInt(5) + 1; // 1 to 5 leave records per employee

            for (int i = 0; i < numberOfLeaves; i++) {
                String type = LEAVE_TYPES[RANDOM.nextInt(LEAVE_TYPES.length)];
                String status = STATUSES[RANDOM.nextInt(STATUSES.length)];

                // Random start date in past 6 months
                LocalDate startDate = LocalDate.now().minusDays(RANDOM.nextInt(180));
                LocalDate endDate = startDate.plusDays(RANDOM.nextInt(3) + 1); // 1 to 3 day leave

                Leave leave = new Leave(
                    type,
                    startDate,
                    endDate,
                    status,
                    "Auto-generated leave",
                    employee,
                    company
                );

                leaves.add(leave);
            }
        }

        return leaves;
    }

}

class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
