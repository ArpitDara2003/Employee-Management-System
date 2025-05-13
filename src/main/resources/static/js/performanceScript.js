// Update the displayed percentage value when the range input changes
var rangeInput = document.querySelector('input[type="range"]');
if (rangeInput) {
    rangeInput.oninput = function() {
        var rangeValue = document.querySelector('.range-value');
        rangeValue.innerText = this.value + '%';
    };
}

document.addEventListener("DOMContentLoaded", function () {
    const submitBtn = document.getElementById("submitBtn");
    if (submitBtn) {
        submitBtn.addEventListener("click", function () {
            const form = document.getElementById("popupForm");
            const formData = new FormData(form);

            const params = new URLSearchParams();
            for (const pair of formData.entries()) {
                params.append(pair[0], pair[1]);
            }

            fetch("/worklogSubmit", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: params.toString()
            })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                } else {
                    console.log("Submitted successfully!");
                }
            })
            .catch(error => console.error('Error submitting the form:', error));
        });
    }
});

function updateDueDate(selectElement, tasks) {
    if (!tasks || !Array.isArray(tasks)) {
        console.error("Tasks is not an array or is undefined:", tasks);
        document.getElementById('dueDate').innerText = 'No due date available';
        return;
    }

    const selectedTaskTitle = selectElement.value;
    console.log("Selected task:", selectedTaskTitle);

    const task = tasks.find(t => t.title === selectedTaskTitle);
    updatePercentageCompleted(tasks)
    if (task) {        
        document.getElementById('dueDate').innerText = task.dueDate || 'No due date available';
        console.log("Due date updated to:", task.dueDate);
    } else {
        document.getElementById('dueDate').innerText = 'No due date available';
        console.log("No matching task found. Resetting due date.");
    }
}

function updatePercentageCompleted(tasks) {
    const selectedTaskTitle = document.getElementById("projectDropdown").value;
    console.log("Selected task:", selectedTaskTitle);

    const task = tasks.find(t => t.title === selectedTaskTitle);
    const currentValue = task.percentageCompleted || 0;
    const addedValue = parseInt(document.getElementById("percentageWorkDone").value || 0);
    let percentageCompleted = currentValue + addedValue;

    // Clamp the value to a maximum of 100
    if (percentageCompleted > 100) {
        percentageCompleted = 100;
    }

    document.getElementById('workCompletionPercentage').innerText = percentageCompleted + '%';
    document.getElementById('workCompletionMeter').value = percentageCompleted;
}
