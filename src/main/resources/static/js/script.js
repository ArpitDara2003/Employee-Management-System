
function navigateToPage(element) {
    var page = element.getAttribute('data-page');
    console.log('Navigating to:', page); // This is where you handle navigation
    window.location.href = page; // Example: navigate to the page
}