function showProfile(button) {
    // Prevent redirection if the clicked element is the 'Alot Work' button
    if (button.id === 'openModalBtn') {
        return;
    }

    const employeeId = button.getAttribute('data-employee-id');
    console.log('Employee ID:', employeeId);
    // Redirect to the employee's profile page
    window.location.href = `/employees/${employeeId}`;
}

function updateProgress(metricName, change) {
    fetch(`/updateMetric`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ metricName, change }),
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('Failed to update metric.');
        }
    })
    .catch(error => console.error('Error updating metric:', error));
}


function handleAlotWorkClick(event, button) {
    event.stopPropagation();
    const employeeId = button.getAttribute('data-employee-id');
    console.log('Alot Work button clicked for Employee ID:', employeeId);
    const input = document.getElementById('employeeIdInput');
    if (input) {
        input.value = employeeId;
    }
}


document.getElementById('popupForm').addEventListener('submit', function (event) {
    const employeeId = document.getElementById('employeeIdInput').value;
    const selectedProject = document.getElementById('projectDropdown').value;
    const title = document.getElementById('title').value.trim();
    const workDescription = document.getElementById('workDescription').value.trim();
    const dueDate = document.getElementById('dueDateInput').value;
    const estimatedTimeRequired = document.getElementById('estimatedTimeRequired').value;
    const complexity = document.getElementById('comlexity').value;

    let errorMessage = '';

    if (!employeeId) {
        errorMessage += 'Employee ID is missing.\n';
    }
    if (!selectedProject) {
        errorMessage += 'Please select a project.\n';
    }
    if (!title) {
        errorMessage += 'Title is required.\n';
    }
    if (!workDescription) {
        errorMessage += 'Work description is required.\n';
    }
    if (!dueDate) {
        errorMessage += 'Due date is required.\n';
    }
    if (!estimatedTimeRequired || estimatedTimeRequired <= 0) {
        errorMessage += 'Estimated time required must be a positive number.\n';
    }
    if (!complexity) {
        errorMessage += 'Please select a complexity level.\n';
    }

    if (errorMessage) {
        alert(errorMessage);
        event.preventDefault(); // Prevent form submission
    }
});