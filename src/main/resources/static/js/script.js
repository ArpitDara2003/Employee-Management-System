document.addEventListener('DOMContentLoaded', function() {
    // Add click event to profile-summary
    const profileSummary = document.querySelector('.profile-summary');
    if (profileSummary) {
        profileSummary.addEventListener('click', function() {
            window.location.href = '/editprofile';
        });
    }

    // Toggle Sidebar
    const leftToggleBtn = document.querySelector('.left-toggle');
    const rightToggleBtn = document.querySelector('.right-toggle');
    const mobileToggleBtn = document.querySelector('.mobile-toggle');
    const leftSidebar = document.querySelector('.left-sidebar');
    const rightSidebar = document.querySelector('.right-sidebar');
    const mainContainer = document.querySelector('.main-container');
    const sidebarOverlay = document.querySelector('.sidebar-overlay');

    // Left sidebar toggle (desktop)
    if (leftToggleBtn) {
        leftToggleBtn.addEventListener('click', function() {
            mainContainer.classList.toggle('sidebar-collapsed');
        });
    }

    // Mobile sidebar toggles
    if (mobileToggleBtn) {
        mobileToggleBtn.addEventListener('click', function() {
            leftSidebar.classList.add('open');
            sidebarOverlay.classList.add('show');
            document.body.style.overflow = 'hidden';
        });
    }

    if (rightToggleBtn) {
        rightToggleBtn.addEventListener('click', function() {
            rightSidebar.classList.remove('open');
            sidebarOverlay.classList.remove('show');
            document.body.style.overflow = '';
        });
    }

    // Right sidebar toggle buttons in cards
    const cardHeaderBtns = document.querySelectorAll('.card-header-btn');
    if (cardHeaderBtns) {
        cardHeaderBtns.forEach(btn => {
            btn.addEventListener('click', function() {
                rightSidebar.classList.add('open');
                sidebarOverlay.classList.add('show');
                document.body.style.overflow = 'hidden';
            });
        });
    }

    // Overlay click (close sidebars on mobile)
    if (sidebarOverlay) {
        sidebarOverlay.addEventListener('click', function() {
            leftSidebar.classList.remove('open');
            rightSidebar.classList.remove('open');
            sidebarOverlay.classList.remove('show');
            document.body.style.overflow = '';
        });
    }

    // Settings page tab switching
    const settingsLinks = document.querySelectorAll('.settings-menu li a');
    const settingsSections = document.querySelectorAll('.settings-section');

    if (settingsLinks && settingsSections) {
        settingsLinks.forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                
                // Remove active class from all links
                settingsLinks.forEach(l => {
                    l.parentElement.classList.remove('active');
                });
                
                // Add active class to current link
                this.parentElement.classList.add('active');
                
                // Get the target section
                const targetId = this.getAttribute('href');
                
                // Hide all sections
                settingsSections.forEach(section => {
                    section.classList.remove('active');
                });
                
                // Show the target section
                document.querySelector(targetId).classList.add('active');
            });
        });
    }

    // Toggle password visibility
    const togglePassword = document.querySelector('.toggle-password');
    if (togglePassword) {
        togglePassword.addEventListener('click', function() {
            const passwordInput = document.querySelector('#password');
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                this.innerHTML = '<i class="fas fa-eye-slash"></i>';
            } else {
                passwordInput.type = 'password';
                this.innerHTML = '<i class="fas fa-eye"></i>';
            }
        });
    }

    // Initialize charts if Chart.js is available
    if (typeof Chart !== 'undefined') {
        // Performance Chart
        const performanceChart = document.getElementById('performanceChart');
        if (performanceChart && window.monthlyPerformanceData) {
            new Chart(performanceChart, {
                type: 'line',
                data: {
                    labels: window.monthlyPerformanceData.labels, // Use global labels
                    datasets: [{
                        label: 'Performance',
                        data: window.monthlyPerformanceData.data, // Use global data
                        borderColor: '#4361ee',
                        backgroundColor: 'rgba(67, 97, 238, 0.1)',
                        tension: 0.3,
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: { display: false }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            max: 100,
                            ticks: {
                                callback: function(value) {
                                    return value + '%';
                                }
                            }
                        },
                        x: {
                            ticks: {
                                autoSkip: false, // Allow scrolling if labels are too many
                            }
                        }
                    }
                }    
            });                
        }

        // Team Performance Chart
        const teamPerformanceChart = document.getElementById('teamPerformanceChart');
        if (teamPerformanceChart && window.teamPerformanceData) {
            new Chart(teamPerformanceChart, {
                type: 'bar',
                data: {
                    labels: window.teamPerformanceData.labels,
                    datasets: [{
                        label: 'Team Performance',
                        data: window.teamPerformanceData.data,
                        backgroundColor: [
                            '#4361ee',
                            '#2ecc71',
                            '#f39c12',
                            '#9c27b0',
                            '#3498db'
                        ],
                        borderWidth: 0
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            display: false
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            max: 100,
                            ticks: {
                                callback: function(value) {
                                    return value + '%';
                                }
                            }
                        }
                    }
                }
            });
        }

        // Mini Performance Chart (for sidebar)
        const miniPerformanceChart = document.getElementById('miniPerformanceChart');
        if (miniPerformanceChart) {
            new Chart(miniPerformanceChart, {
                type: 'doughnut',
                data: {
                    labels: ['Completed', 'Remaining'],
                    datasets: [{
                        data: [88, 12],
                        backgroundColor: [
                            '#4361ee',
                            '#e2e8f0'
                        ],
                        borderWidth: 0
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    cutout: '70%',
                    plugins: {
                        legend: {
                            display: false
                        }
                    }
                }
            });
        }
    }

    // Utility function to show notifications
    function showNotification(message, type = 'info') {
        // Create notification element
        const notification = document.createElement('div');
        notification.className = `notification ${type}`;
        notification.innerHTML = `
            <div class="notification-content">
                <i class="fas ${type === 'success' ? 'fa-check-circle' : 'fa-info-circle'}"></i>
                <span>${message}</span>
            </div>
            <button class="notification-close">
                <i class="fas fa-times"></i>
            </button>
        `;
        
        // Add to DOM
        document.body.appendChild(notification);
        
        // Show with animation
        setTimeout(() => {
            notification.classList.add('show');
        }, 10);
        
        // Close button
        const closeBtn = notification.querySelector('.notification-close');
        if (closeBtn) {
            closeBtn.addEventListener('click', function() {
                notification.classList.remove('show');
                setTimeout(() => {
                    notification.remove();
                }, 300);
            });
        }
        
        // Auto remove after 5 seconds
        setTimeout(() => {
            notification.classList.remove('show');
            setTimeout(() => {
                notification.remove();
            }, 300);
        }, 5000);
    }

    // Theme switcher in settings
    const themeOptions = document.querySelectorAll('.theme-option');
    if (themeOptions) {
        themeOptions.forEach(option => {
            option.addEventListener('click', function() {
                // Remove active class from all options
                themeOptions.forEach(opt => {
                    opt.classList.remove('active');
                });
                
                // Add active class to clicked option
                this.classList.add('active');
            });
        });
    }

    // Responsive handling for table view on small screens
    function handleResponsiveTables() {
        const tables = document.querySelectorAll('table');
        if (tables && window.innerWidth < 768) {
            tables.forEach(table => {
                if (!table.classList.contains('responsive-handled')) {
                    const headerTexts = [];
                    const headerCells = table.querySelectorAll('thead th');
                    
                    // Get header text
                    headerCells.forEach(cell => {
                        headerTexts.push(cell.textContent);
                    });
                    
                    // Add data attributes to cells
                    const bodyCells = table.querySelectorAll('tbody td');
                    bodyCells.forEach((cell, index) => {
                        const headerIndex = index % headerTexts.length;
                        cell.setAttribute('data-label', headerTexts[headerIndex]);
                    });
                    
                    table.classList.add('responsive-handled');
                }
            });
        }
    }

    // Call once on load
    handleResponsiveTables();
    
    // Call on resize
    window.addEventListener('resize', handleResponsiveTables);

});