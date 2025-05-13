window.onload = function() {
    function navigateToMonth(offset) {
        const currentMonthYearEl = document.getElementById('currentMonthYear');
        if (!currentMonthYearEl) {
            console.error("Element with ID 'currentMonthYear' not found.");
            return;
        }

        const currentMonthYear = currentMonthYearEl.innerText.trim();
        const parts = currentMonthYear.split(" ");
        const monthName = parts[0];
        let year = parseInt(parts[1]);

        const monthMap = {
            "January": 1, "February": 2, "March": 3, "April": 4,
            "May": 5, "June": 6, "July": 7, "August": 8,
            "September": 9, "October": 10, "November": 11, "December": 12
        };

        const monthShortMap = {
            1: "jan", 2: "feb", 3: "mar", 4: "apr",
            5: "may", 6: "jun", 7: "jul", 8: "aug",
            9: "sep", 10: "oct", 11: "nov", 12: "dec"
        };

        let month = monthMap[monthName];

        month += offset;
        if (month === 0) {
            month = 12;
            year -= 1;
        } else if (month === 13) {
            month = 1;
            year += 1;
        }

        const targetUrl = '/calendar/' + monthShortMap[month] + year;
        window.location.href = targetUrl;
    }

    document.getElementById('prevMonthBtn').addEventListener('click', function() {
        navigateToMonth(-1);
    });

    document.getElementById('nextMonthBtn').addEventListener('click', function() {
        navigateToMonth(1);
    });
};
