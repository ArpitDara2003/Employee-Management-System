document.addEventListener('DOMContentLoaded', () => {
  populateLeaveDropdown();
  showAvailableLeaves();
  renderLeaveSummary(leavesApproved, 'leavesApprovedContainer', true);
  renderLeaveSummary(leavesPending, 'leavesPendingContainer', true);
});

function populateLeaveDropdown() {
  const select = document.getElementById('leaveTypeSelect');
  for (const type in leaveType) {
      const option = document.createElement('option');
      option.value = type;
      option.textContent = type;
      select.appendChild(option);
  }
}

function showAvailableLeaves() {
  const type = document.getElementById('leaveTypeSelect').value;
  const count = leaveType[type];
  document.getElementById('availableLeaves').textContent = `${count} leaves available`;
}

function renderLeaveSummary(dataMap, containerId, revocable) {
  const container = document.getElementById(containerId);
  container.innerHTML = '';

  for (const type in dataMap) {
      dataMap[type].forEach(dateStr => {
          const row = document.createElement('div');
          row.classList.add('leave-entry');

          const dateFormatted = new Date(dateStr).toDateString();
          row.innerHTML = `<strong>${type}</strong> - ${dateFormatted}`;

          if (revocable) {
              const btn = document.createElement('button');
              btn.textContent = 'Revoke';
              btn.className = 'revoke-btn';
              btn.onclick = () => revokeLeave(type, dateStr);
              row.appendChild(btn);
          }

          container.appendChild(row);
      });
  }
}
document.querySelectorAll('.revoke-btn').forEach(button => {
  button.addEventListener('click', function (e) {
      if (!confirm("Are you sure you want to revoke this leave?")) {
          e.preventDefault();
      }
  });
});

function revokeLeave(type, date) {
  document.getElementById('revokeType').value = type;
  document.getElementById('revokeDate').value = date;
  document.getElementById('revokeForm').submit();
}
document.getElementById('leaveForm').addEventListener('submit', function (e) {
    const leaveType = document.getElementById('leaveTypeSelect').value;
    const fromDate = document.getElementById('fromLeaveDate').value;
    const tillDate = document.getElementById('tillLeaveDate').value;

    if (!leaveType || !fromDate || !tillDate) {
        e.preventDefault();
        alert("Please fill out all fields before submitting the form.");
        return;
    }

    if (new Date(fromDate) > new Date(tillDate)) {
        e.preventDefault();
        alert("From Date cannot be after Till Date.");
        return;
    }

    alert(
        `Form will be submitted with:\n` +
        `Leave Type: ${leaveType}\n` +
        `From Date: ${fromDate}\n` +
        `Till Date: ${tillDate}`
    );
});
