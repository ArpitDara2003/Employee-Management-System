// Modal-related logic consolidated from various scripts

// Ensure modal is hidden on page load
document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('myModal');
    if (modal) {
        modal.style.display = 'none';
    }

    // Open the modal when the button is clicked
    const btn = document.querySelectorAll('openModalBtn');
    if (btn) {
        btn.onclick = function() {
            modal.style.display = 'flex'; // Use "flex" to maintain centering as per CSS
        };
    }

    // Close the modal when the user clicks on <span> (x)
    const span = document.getElementsByClassName('close')[0];
    if (span) {
        span.onclick = function() {
            modal.style.display = 'none';
        };
    }

    // Close the modal when the user clicks anywhere outside of the modal
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    };
});

function openModal(button) {
    const modal = document.getElementById('myModal');
    if (modal) {
        modal.style.display = 'flex';
    }
}
