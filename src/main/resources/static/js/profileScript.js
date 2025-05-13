document.addEventListener('DOMContentLoaded', function () {
    // Form for updating status
    const statusForm = document.querySelector('form[id="status-form"]');
    if (statusForm) {
        statusForm.addEventListener('submit', function (e) {
            console.log('Status form submitted'); // Debug log
            const status = document.getElementById('status');
            if (!status || !status.value) {
                alert('Please select a valid status.');
                e.preventDefault();
            }else {
                console.log('Form validation not failed.');
            }
        });
    }

    // Form for updating profile details
    const profileForm = document.querySelector('form[id="profile-form"]');
    if (profileForm) {
        profileForm.addEventListener('submit', function (e) {
            console.log('Profile form submitted'); // Debug log

            const name = document.getElementById('name');
            const position = document.getElementById('position');
            const email = document.getElementById('email');
            const phone = document.getElementById('phoneNumber');
            const phoneRegex = /^[0-9]{10}$/;

            let hasError = false;

            if (!name || name.value.trim() === '') {
                alert('Name cannot be empty.');
                hasError = true;
            }

            if (!position || position.value.trim() === '') {
                alert('Position cannot be empty.');
                hasError = true;
            }

            if (!email || !email.value.includes('@')) {
                alert('Please enter a valid email address.');
                hasError = true;
            }

            if (!phone || !phoneRegex.test(phone.value)) {
                alert('Phone number must be 10 digits.');
                hasError = true;
            }

            if (hasError) {
                console.log('Form validation failed.');
                e.preventDefault();
            }else {
                console.log('Form validation not failed.');
            }
        });
    }

    // Profile image preview
    const profileImageInput = document.getElementById('profileImage');
    if (profileImageInput) {
        profileImageInput.addEventListener('change', function (event) {
            console.log('Profile image input changed');
            const file = event.target.files[0];
            if (file && file.type.startsWith('image/')) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    let preview = document.getElementById('imagePreview');
                    if (!preview) {
                        preview = document.createElement('img');
                        preview.id = 'imagePreview';
                        preview.style.maxWidth = '100px';
                        preview.style.marginTop = '10px';
                        profileImageInput.parentElement.appendChild(preview);
                    }
                    preview.src = e.target.result;
                };
                reader.readAsDataURL(file);
            } else {
                alert('Please upload a valid image file.');
            }
        });
    }
});
