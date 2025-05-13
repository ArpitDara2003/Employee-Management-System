package com.example.project_1.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.project_1.dataModels.CalendarEvent;
import com.example.project_1.dataModels.Company;
import com.example.project_1.dataModels.Employee;
import com.example.project_1.dataModels.LeaveBalance;
import com.example.project_1.dataModels.NewsItems;
import com.example.project_1.dataModels.PerformanceMetrics;
import com.example.project_1.dataModels.Task;
import com.example.project_1.dataModels.UI.Achievement;
import com.example.project_1.dataModels.UI.DateCube;
import com.example.project_1.dataModels.UI.EmployeeView;
import com.example.project_1.dataModels.UI.NavItem;
import com.example.project_1.dataModels.UI.StatCard;
import com.example.project_1.dataModels.UI.TeamMember;
import com.example.project_1.businessLogicLayer.CompanyLogic;
import com.example.project_1.businessLogicLayer.EmployeeLogic;
import com.example.project_1.businessLogicLayer.LeaveBalanceLogic;
import com.example.project_1.businessLogicLayer.NewsLogic;  
import com.example.project_1.businessLogicLayer.PerformanceLogic;
import com.example.project_1.businessLogicLayer.StatCardsLogic;
import com.example.project_1.businessLogicLayer.TasksLogic;
@Controller
public class HomeController {
    private boolean isLoggedIn = false;
    private boolean isAdmin = false;
    private Company company = null;
    private TeamMember user = null;
    private Long companyId = null;
    private Long employeeId = null;
    
    public HomeController() {
        isLoggedIn = true;
        isAdmin = true;
        company = CompanyLogic.getCompanyById(1);
        user =  EmployeeLogic.getMangerFromDB(1L, 4L);
        companyId = 1L;
        employeeId = 4L;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login"; 
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @GetMapping("/")
    public String redirectToHome() {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        return "redirect:/home"; // Redirect to avoid circular view path
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("company",company);
        model.addAttribute("navItems", getNavigationItems());
        model.addAttribute("news", NewsLogic.getNews(company.getCompanyId().longValue()));
        model.addAttribute("performanceMetrics", PerformanceLogic.getEfficiencyMetric(companyId, employeeId));
        model.addAttribute("monthlyPerformance", PerformanceLogic.getMonthlyMetric(companyId, employeeId));
        model.addAttribute("teamPerformance",  PerformanceLogic.getTeamMetric(companyId, employeeId));
        model.addAttribute("statCards", StatCardsLogic.getDashboardStats(companyId, employeeId));
        model.addAttribute("calendarEvents", getMockCalendarEvents());
        model.addAttribute("achievements",  getMockedAchievements());
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);

        System.out.println("Returning dashboard.html");
        return "dashboard";
    }

    
    @GetMapping("/settings")
    public String settings(Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("company",company);
        model.addAttribute("navItems", getNavigationItems());
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        return "settings"; 
    }


    @GetMapping("/employees")
    public String employees(Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("company", company);
        model.addAttribute("juniors", EmployeeLogic.getUserJuniorMates(companyId, employeeId));
        model.addAttribute("tasks", TasksLogic.getTasks(companyId, employeeId));
        model.addAttribute("manager", EmployeeLogic.getMangerFromDB(companyId, employeeId));
        model.addAttribute("teamMembers", EmployeeLogic.getUserTeamMates(companyId, employeeId));
        model.addAttribute("navItems", getNavigationItems());
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        return "employees"; 
    }

    @GetMapping("/employees/{employeeId}")
    public String employeeProfile(@PathVariable("employeeId") String employeeId2, Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("navItems", getNavigationItems());
        model.addAttribute("user", user);
        model.addAttribute("company",company);
        model.addAttribute("teamMember", EmployeeLogic.getTeamMemberFromDB(companyId, Long.parseLong(employeeId2)));
        model.addAttribute("juniors", EmployeeLogic.getUserJuniorMates(companyId, Long.parseLong(employeeId2)));
        model.addAttribute("manager", EmployeeLogic.getMangerFromDB(companyId, Long.parseLong(employeeId2)));   
        model.addAttribute("teamMembers", EmployeeLogic.getUserTeamMates(companyId, Long.parseLong(employeeId2)));
        model.addAttribute("achievements",  getMockedAchievements());
        model.addAttribute("performanceMetrics", PerformanceLogic.getEfficiencyMetric(companyId, Long.parseLong(employeeId2)));
        model.addAttribute("monthlyPerformance", PerformanceLogic.getMonthlyMetric(companyId, Long.parseLong(employeeId2)));    
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        return "profile";
    }
        
    @GetMapping("/calendar")
    public String defaultCalendar(Model model) {
        String currentMonthYear = getCurrentMonthYear();
        return "redirect:/calendar/" + currentMonthYear;
    }

    @GetMapping("/calendar/{monthYear}")
    public String calendar(@PathVariable("monthYear") String monthYear, Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("user", getMockedUser());
        model.addAttribute("company",company);
        model.addAttribute("presentMonthYear",  getPresentMonthYear(monthYear));
        model.addAttribute("presentMonthDays", getMockPresentMonth(monthYear));
        model.addAttribute("navItems", getNavigationItems());
        model.addAttribute("statCards", getSampleStats());
        model.addAttribute("teamPerformance", getTeamPerformance());
        model.addAttribute("leaveBalance", getLeaveBalance());
        model.addAttribute("isAdmin", isAdmin);
        return "calendar";
    }

    @GetMapping("/performance")
    public String performance(Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("user", getMockedUser());
        model.addAttribute("company",company);
        model.addAttribute("tasks", getMockedtasks());
        model.addAttribute("achievements",  getMockedAchievements() );
        model.addAttribute("statCards", getSampleStats());
        model.addAttribute("navItems", getNavigationItems());
        model.addAttribute("performanceMetrics", getPerformanceMetrics());
        model.addAttribute("monthlyPerformance", mockedMonthlyData());
        model.addAttribute("teamPerformance", mockedTeamPerformance());
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("companyId", companyId);
        return "performance"; 
    }
    @GetMapping("/leave")
    public String leave(Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("company",company);
        model.addAttribute("leaveType", getLeaveType());
        model.addAttribute("leavesApproved", getLeavesApproved());
        model.addAttribute("leavesPending", getLeavesPending()); 
        
        model.addAttribute("statCards", getSampleStats());
        model.addAttribute("company",company);
        model.addAttribute("navItems", getNavigationItems());
        model.addAttribute("news", getMockNews());
        
        model.addAttribute("performanceMetrics", getPerformanceMetrics());
        model.addAttribute("monthlyPerformance", getMonthlyPerformance());
        model.addAttribute("teamPerformance", getTeamPerformance());
        model.addAttribute("leaveBalance", getLeaveBalance());
        model.addAttribute("calendarEvents", getMockCalendarEvents());
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("companyId", companyId);
        return "leave"; 
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }
        model.addAttribute("user", getMockedUser());
        model.addAttribute("company",company);
        model.addAttribute("navItems", getNavigationItems());
        model.addAttribute("news", getMockNews());
        
        model.addAttribute("performanceMetrics", getPerformanceMetrics());
        model.addAttribute("monthlyPerformance", getMonthlyPerformance());
        model.addAttribute("teamPerformance", getTeamPerformance());
        model.addAttribute("leaveBalance", getLeaveBalance());
        model.addAttribute("calendarEvents", getMockCalendarEvents());
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("companyId", companyId);
        return "profile";
    }

    @GetMapping("/editprofile")
    public String editProfile(Model model) {
        if (!isLoggedIn) {
            return "redirect:/login";
        }        
        model.addAttribute("user", getMockedUser());
        model.addAttribute("company",company);
        model.addAttribute("navItems", getNavigationItems());
        return "editprofile";
    }

    @PostMapping("/submitWorkAloted")
    public String submitWorkAloted(
        @RequestParam String selectedProject,
        @RequestParam String title,
        @RequestParam String workDescription,
        @RequestParam String dueDate,
        @RequestParam int estimatedTimeRequired,
        @RequestParam String comlexity,
        @RequestParam String employeeId,
        Model model) {

        System.out.println("=== Work Assignment Details ===");
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Selected Project: " + selectedProject);
        System.out.println("Title: " + title);
        System.out.println("Work Description: " + workDescription);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Estimated Time Required (in days): " + estimatedTimeRequired);
        System.out.println("Complexity: " + comlexity);
        System.out.println("================================");

        // Redirect to dashboard after submission
        return "redirect:/employees";
    }

    @PostMapping("/addevent")
    public String handleEventForm(
            @RequestParam("selectedProject") String selectedProject,
            @RequestParam("workDate") String workDate,
            @RequestParam("eventTitle") String eventTitle,
            @RequestParam("eventDescription") String eventDescription
    ) {
        // Print the received form data
        System.out.println("Selected Event Type: " + selectedProject);
        System.out.println("Date: " + workDate);
        System.out.println("Title: " + eventTitle);
        System.out.println("Description: " + eventDescription);

        // You can redirect or return a view name here
        return "redirect:/calendar";
    }
    
    @PostMapping("/applyforleave")
    public String applyForLeave(
            @RequestParam("leaveType") String leaveType,
            @RequestParam("fromLeaveDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam("tillLeaveDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tillDate
    ) {
        System.out.println("Leave Type: " + leaveType);
        System.out.println("From Date: " + fromDate);
        System.out.println("Till Date: " + tillDate);

        return "redirect:/home"; 
    }



    @PostMapping("/leave/apply")
    public String applyLeave(@RequestParam String leaveType,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromLeaveDate,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tillLeaveDate,
                            RedirectAttributes redirectAttributes) {
        // Add the leave to DB or in-memory map (pending/approved)
        return "redirect:/leave";
    }

    @PostMapping("/leave/revoke")
    public String revokeLeave(@RequestParam String leaveType,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate leaveDate) {
        // Remove the leave from DB or in-memory map (from pending/approved)
        return "redirect:/leave";
    }


    @PostMapping("/submitWorkLog")
    public String submitWorkLog(
            @RequestParam("selectedProject") String selectedProject,
            @RequestParam("percentageWorkDone") int percentageWorkDone,
            @RequestParam("workDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate workDate
    ) {
        System.out.println("Selected Project: " + selectedProject);
        System.out.println("Percentage Work Done: " + percentageWorkDone);
        System.out.println("Work Date: " + workDate);

        return "redirect:/performance"; // Or return a confirmation view if needed
    }

    @PostMapping("/updatestatus")
    public String updateStatus(@RequestParam("status") String status, RedirectAttributes redirectAttributes) {
        System.out.println("Controller method invoked: updateStatus");
        System.out.println("Updated status to: " + status);
        redirectAttributes.addFlashAttribute("message", "Status updated successfully!");
        return "redirect:/editprofile";
    }

    @PostMapping("/updateprofile")
    public String updateProfile(@RequestParam Map<String, String> formData, @RequestParam(value = "profileImage", required = false) MultipartFile profileImage, RedirectAttributes redirectAttributes) {
        // Mock logic to update the profile details
        formData.forEach((key, value) -> System.out.println(key + ": " + value));

        if (profileImage != null && !profileImage.isEmpty()) {
            System.out.println("Uploaded profile image: " + profileImage.getOriginalFilename());
        }

        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        return "redirect:/editprofile";
    }
   

    public Map<String, Integer> mockedTeamPerformance() {
        Map<String, Integer> teamPerformance = new LinkedHashMap<>();
    
        teamPerformance.put("John Doe", 88);
        teamPerformance.put("Jane Smith", 92);
        teamPerformance.put("Bob Johnson", 79);
        teamPerformance.put("Alice Williams", 85);
        teamPerformance.put("Charlie Brown", 91);
    
        return teamPerformance;
    }

    public Map<String, Integer> mockedMonthlyData() {
        Map<String, Integer> monthlyData = new LinkedHashMap<>();
    
        monthlyData.put("Dec'23", 47);
        monthlyData.put("Jan'24", 78);
        monthlyData.put("Feb'24", 56);
        monthlyData.put("Mar'24", 64);
        monthlyData.put("Apr'24", 85);
        monthlyData.put("May'24", 91);
        monthlyData.put("Jun'24", 73);
        monthlyData.put("Jul'24", 88);
        monthlyData.put("Aug'24", 66);
        monthlyData.put("Sep'24", 59);
        monthlyData.put("Oct'24", 80);
        monthlyData.put("Nov'24", 71);
        monthlyData.put("Dec'24", 69);
        monthlyData.put("Jan'25", 92);
        monthlyData.put("Feb'25", 75);
        monthlyData.put("Mar'25", 83);
        monthlyData.put("Apr'25", 90);
        monthlyData.put("May'25", 77);
    
        return monthlyData;
    }
    

    private String getCurrentMonthYear() {
        LocalDate now = LocalDate.now();
        String month = now.getMonth().toString().substring(0, 3).toLowerCase();
        int year = now.getYear();
        return month + year;
    }
    
    private List<NavItem> getNavigationItems() {
        return Arrays.asList(
            new NavItem("Dashboard", "home", "fas fa-tachometer-alt"),
            new NavItem("Employees", "employees", "fas fa-users"),
            new NavItem("Calendar", "calendar", "fas fa-calendar-alt"),
            new NavItem("Leave", "leave", "fas fa-plane-departure"),
            new NavItem("Performance", "performance", "fas fa-chart-line"),
            new NavItem("Settings", "settings", "fas fa-cog")
        );
    }
    
    private EmployeeView getEmployeeViewModel() {
        Employee currentEmployee = getMockEmployee();
        return new EmployeeView(
            (int) currentEmployee.getId(),
            currentEmployee.getName(),
            currentEmployee.getEmail(),
            currentEmployee.getPosition(),
            currentEmployee.getDepartment(),
            currentEmployee.getJoinDate(),
            currentEmployee.getProfileImage()
        );
    }
    
    private List<PerformanceMetrics> getPerformanceMetrics() {
        return Arrays.asList(
            new PerformanceMetrics("Productivity", 85, 100),
            new PerformanceMetrics("Quality", 92, 100),
            new PerformanceMetrics("Teamwork", 88, 100),
            new PerformanceMetrics("Innovation", 78, 100)
        );
    }
    
    private Map<String, Integer> getMonthlyPerformance() {
        Map<String, Integer> data = new HashMap<>();
        data.put("Jan", 75);
        data.put("Feb", 80);
        data.put("Mar", 85);
        data.put("Apr", 82);
        data.put("May", 90);
        data.put("Jun", 88);
        return data;
    }

    private String getPresentMonthYear(String monthYear) {
        String monthAbbrev = monthYear.substring(0, 3).toLowerCase(); // e.g., "jan"
        String year = monthYear.substring(3); // e.g., "2025"
    
        String fullMonthName;
        switch (monthAbbrev) {
            case "jan": fullMonthName = "January"; break;
            case "feb": fullMonthName = "February"; break;
            case "mar": fullMonthName = "March"; break;
            case "apr": fullMonthName = "April"; break;
            case "may": fullMonthName = "May"; break;
            case "jun": fullMonthName = "June"; break;
            case "jul": fullMonthName = "July"; break;
            case "aug": fullMonthName = "August"; break;
            case "sep": fullMonthName = "September"; break;
            case "oct": fullMonthName = "October"; break;
            case "nov": fullMonthName = "November"; break;
            case "dec": fullMonthName = "December"; break;
            default: fullMonthName = "Unknown"; // fallback
        }
    
        return fullMonthName + " " + year;
    }
    
    private Map<String, Integer> getTeamPerformance() {
        Map<String, Integer> data = new HashMap<>();
        data.put("John Doe", 88);
        data.put("Jane Smith", 92);
        data.put("Bob Johnson", 79);
        data.put("Alice Williams", 85);
        data.put("Charlie Brown", 91);
        return data;
    }
    
    private LeaveBalance getLeaveBalance() {
        return new LeaveBalance(15, 8, 10, 3, 5, 2, 0, 0);
    }

    private Company getMockCompany(Integer companyId) {
        Company company = new Company();
        company.setCompanyId(companyId);
        company.setCompanyName("SiteMachine");
        company.setCompanyLogo("https://placehold.co/100x100?text=Logo");
        company.setNumberOfUsers(25);
        company.setCreatedAt(LocalDateTime.now().minusYears(2));
        company.setUpdatedAt(LocalDateTime.now());
        return company;
    }

    private Employee getMockEmployee() {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setEmail("john.doe@sitemachine.com");
        employee.setPosition("Senior Developer");
        employee.setDepartment("Engineering");
        employee.setJoinDate("2023-01-15");
        employee.setManagerEmployeeId(101L);
        employee.setProductivity(85);
        employee.setProfileImage("/img/profile.jpg");
        return employee;
    }

    private List<NewsItems> getMockNews() {
        List<NewsItems> newsList = new ArrayList<>();
        NewsItems news1 = new NewsItems();
        news1.setHeading("Company Picnic Next Month");
        news1.setDescription("Join us for our annual company picnic on May 15th at Central Park.");
        news1.setImageUrl("https://randomuser.me/api/portraits/women/0.jpg");
        news1.setPostedTime("2025-04-01 10:00:00");

        NewsItems news2 = new NewsItems();
        news2.setHeading("New Health Insurance Plan");
        news2.setDescription("We are pleased to announce our new comprehensive health insurance plan starting next quarter.");
        news2.setImageUrl("https://randomuser.me/api/portraits/women/0.jpg");
        news2.setPostedTime("2025-04-02 12:00:00");

        newsList.add(news1);
        newsList.add(news2);
        return newsList;
    }

    private List<CalendarEvent> getMockCalendarEvents() {
        List<CalendarEvent> events = new ArrayList<>();

        // Mock Employee and Company objects
        Employee mockEmployee = new Employee();
        mockEmployee.setName("John Doe");
        mockEmployee.setEmail("john.doe@example.com");

        Company mockCompany = new Company();
        mockCompany.setCompanyName("Example Corp");

        events.add(new CalendarEvent("Team Meeting", "Conference Room A", LocalDate.of(2025, 4, 15), "10:00 AM", "11:00 AM", "meeting", mockEmployee, mockCompany));
        events.add(new CalendarEvent("Project Deadline", "", LocalDate.of(2025, 4, 25), "2:00 PM", "3:00 PM", "deadline", mockEmployee, mockCompany));

        return events;
    }

    public static List<StatCard> getSampleStats() {
        List<StatCard> stats = new ArrayList<>();
        stats.add(new StatCard("fas fa-users", "blue", "Total Employees", "128"));
        stats.add(new StatCard("fas fa-tasks", "green", "Tasks Completed", "24/30"));
        stats.add(new StatCard("fas fa-calendar-alt", "orange", "Leave Balance", "7 days"));
        stats.add(new StatCard("fas fa-chart-line", "purple", "Performance", "88%"));
        return stats;
    }

    public static List<DateCube> getMockPresentMonth(String monthYear) {

        int year = Integer.parseInt(monthYear.substring(3)); 
        String monthString = monthYear.substring(0, 3).toLowerCase(); 
        int month = convertMonthStringToInt(monthString); 
        
        List<DateCube> existingDays = new ArrayList<>();
        existingDays.add(new DateCube(30, month-1, year, null, "Sunday"));
        existingDays.add(new DateCube(31, month-1, year, null, "Monday"));
        existingDays.add(new DateCube(1, month, year, List.of("Project Review","party hori", "shadi hori"), "Tuesday"));
        existingDays.add(new DateCube(2, month, year, List.of("Team Call"), "Wednesday"));
        existingDays.add(new DateCube(3, month, year, List.of(), "Thursday"));
        existingDays.add(new DateCube(4, month, year, List.of("HR Meeting"), "Friday"));
        existingDays.add(new DateCube(5, month, year, List.of("Bug Fixing"), "Saturday"));
        existingDays.add(new DateCube(6, month, year, List.of("Presentation"), "Sunday"));
        existingDays.add(new DateCube(7, month, year, List.of("Weekend"), "Monday"));

        return getAllDaysOfMonth(existingDays, year, month);
    }

    private Map<String, Integer> getLeaveType() {
        Map<String, Integer> leaveType = new LinkedHashMap<>();
        leaveType.put("Sick Leave", 5);
        leaveType.put("Casual Leave", 10);
        leaveType.put("Maternity Leave", 90);
        return leaveType;
    }

    private Map<String, List<Date>> getLeavesPending() {
        Map<String, List<Date>> leavesPending = new LinkedHashMap<>();
    
        leavesPending.put("Sick Leave", Arrays.asList(
                Date.valueOf(LocalDate.of(2025, 4, 15))
        ));
    
        leavesPending.put("Casual Leave", Arrays.asList(
                Date.valueOf(LocalDate.of(2025, 4, 20)),
                Date.valueOf(LocalDate.of(2025, 4, 25)),
                Date.valueOf(LocalDate.of(2025, 4, 26)),
                Date.valueOf(LocalDate.of(2025, 4, 27)),
                Date.valueOf(LocalDate.of(2025, 4, 28)),
                Date.valueOf(LocalDate.of(2025, 4, 29))
        ));
    
        return leavesPending;
    }
    
    private Map<String, List<Date>> getLeavesApproved() {
        Map<String, List<Date>> leavesApproved = new LinkedHashMap<>();
    
        leavesApproved.put("Sick Leave", Arrays.asList(
                Date.valueOf(LocalDate.of(2025, 3, 10)),
                Date.valueOf(LocalDate.of(2025, 3, 11))
        ));
    
        leavesApproved.put("Casual Leave", Arrays.asList(
                Date.valueOf(LocalDate.of(2025, 2, 15)),
                Date.valueOf(LocalDate.of(2025, 2, 20))
        ));
    
        return leavesApproved;
    }

    private static int convertMonthStringToInt(String monthString) {
        switch (monthString) {
            case "jan": return 1;
            case "feb": return 2;
            case "mar": return 3;
            case "apr": return 4;
            case "may": return 5;
            case "jun": return 6;
            case "jul": return 7;
            case "aug": return 8;
            case "sep": return 9;
            case "oct": return 10;
            case "nov": return 11;
            case "dec": return 12;
            default: return 1;
        }
    }

    public static List<DateCube> getAllDaysOfMonth(List<DateCube> existingDays, int year, int month) {
        Map<Integer, DateCube> existingDaysMap = new HashMap<>();
        for (DateCube dateCube : existingDays) {
            existingDaysMap.put(dateCube.getDay(), dateCube);
        }
        for (int day = 1; day <= 31; day++) {
            if (existingDaysMap.containsKey(day)) {
                continue;
            } else {
                String dayOfWeek = LocalDate.of(year, month, day).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                existingDays.add(new DateCube(day, month, year, null, dayOfWeek));
            }
        }
        return existingDays;
    }

    public List<Achievement> getMockedAchievements() {
        List<Achievement> achievements = new ArrayList<>();

        achievements.add(new Achievement("Completed 1 Year", "2025-04-15", "Office", "bronze"));
        achievements.add(new Achievement("Top Performer", "2025-04-10", "Remote", "silver"));
        achievements.add(new Achievement("Project Delivery", "2025-04-20", "Headquarters", "gold"));
        achievements.add(new Achievement("Employee of the Year", "2025-04-25", "Office", "diamond"));

        return achievements;
    }


    public List<Task> getMockedtasks() {
        List<Task> tasks = new ArrayList<>();
        Task temp = new Task("Task 1", "Description for task 1", 3, 5, "completed", LocalDate.of(2025, 4, 10));
        temp.setPercentageCompleted(35);
        tasks.add(temp);
        Task task2 = new Task("Task 2", "Description for task 2", 2, 4, "in-progress", LocalDate.of(2025, 4, 12));
        task2.setPercentageCompleted(23);
        tasks.add(task2);
        Task task3 = new Task("Task 3", "Description for task 3", 4, 6, "not-started", LocalDate.of(2025, 4, 15));
        task3.setPercentageCompleted(56);
        tasks.add(task3);
        Task task4 = new Task("Task 4", "Description for task 4", 1, 2, "completed", LocalDate.of(2025, 4, 18));
        task4.setPercentageCompleted(89); 
        tasks.add(task4);

        return tasks;
    }

    
    
    public static List<TeamMember> getMockedTeam() {
        List<TeamMember> team = new ArrayList<>();
    
        team.add(new TeamMember(
            "567",
            "John Doe",
            "Senior Developer",
            "john.doe@example.com",
            "+1234567890",
            "https://randomuser.me/api/portraits/men/32.jpg",
            "active"    
        ));
        team.add(new TeamMember(
            "2632",
            "Alice Johnson",
            "UI/UX Designer",
            "alice.johnson@example.com",
            "+1234509876",
            "https://randomuser.me/api/portraits/women/44.jpg",
            "on-leave"
        ));
        team.add(new TeamMember(
            "8976",
            "Robert Brown",
            "Backend Developer",
            "robert.brown@example.com",
            "+1234987650",
            "https://randomuser.me/api/portraits/men/65.jpg",
            "inactive"
        ));
        team.add(new TeamMember(
            "4567",
            "Emily Davis",
            "QA Engineer",
            "emily.davis@example.com",
            "+1298765432",
            "https://randomuser.me/api/portraits/women/56.jpg",
            "active"
        ));
    
        return team;
    }

    public TeamMember getMockedUser() {
        return new TeamMember(
            "007",
            "James Bond",
            "Secret Agent",
            "james.bond007@example.com",
            "9650257467",
            "https://randomuser.me/api/portraits/men/16.jpg",
            "active"
        );
    }
}