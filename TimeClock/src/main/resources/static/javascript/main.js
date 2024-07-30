var userId = 0;
var nameOfUser;
var tempMessageId;

var mainContent = {

    init: function() {
        this.showLoginForm();
        this.addListener();
        this.setUserName();
        
        $("#welcomeMessage").text("Welcome " + nameOfUser);
    },

    addListener: function() {
        $("#registerSubmit").on("click", function(event) {
            $('.errors').text('');
            event.preventDefault();
            this.registerForm();
        }.bind(this));

        $("#backToLogin").on("click", function(event) {
            $('.errors').text('');
            event.preventDefault();
            this.showLoginForm();
        }.bind(this));
        $("#backToRegister").on("click", function(event) {
            $('.errors').text('');
            event.preventDefault();
            this.showRegisterForm();
        }.bind(this));
        $("#loginSubmit").on("click", function(event) {
            $('.errors').text('');
            event.preventDefault();
            this.loginForm();
        }.bind(this));

        $("#clockInBtn").on("click", function(event) {
            event.preventDefault();
            this.clockIn();
        }.bind(this));

		$("#clockOutBtn").on("click", function(event) {
			event.preventDefault();
			this.clockOut();
		}.bind(this));

		$("#logout").on("click", function() {
			$('.errors').text('');
			this.userLogout();
		}.bind(this));
	},

	showLoginForm: function() {
		$('.errors').text('');
		$("#registerForm").hide();
		$("#loginForm").show();
		$("#messageDisplay").hide();
	},

	showRegisterForm: function() {
		$('.errors').text('');
		$("#loginForm").hide();
		$("#registerForm").show();
		$("#messageDisplay").hide();
	},

	clear: function() {
		$('.errors').text('');
		$("#registerForm").hide();
		$("#loginForm").hide();
		$("#messageDisplay").hide();
	},

	loginForm: function() {
		let formData = {
			"email": $('#loginEmail').val(),
			"password": $('#loginPassword').val()
		}
		$.ajax({
			url: '/api/Login',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(formData),
			dataType: 'json',
			success: function(data) {
				$('.errors').text('');
				userId = data;
				mainContent.clear();
				mainContent.showDisplayMessage();
				mainContent.displayTimeClockEntries();
			}.bind(this),
			error: function(xhr, status, error) {
				var errors = xhr.responseJSON.message;
				console.error('Error logging in:', error);
				$('.errors').text(errors);
			}
		});
	},

	registerForm: function() {
		let formData = {
			"email": $('#registerEmail').val(),
			"userName": $('#registerName').val(),
			"password": $('#registerPassword').val()
		}
		$.ajax({
			url: '/api/register',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(formData),
			dataType: 'json',
			success: function() {
				mainContent.showLoginForm();
				$('.errors').text('');
			}.bind(this),
			error: function(xhr, error, status) {
				var errors = xhr.responseJSON.message;
				console.error('Error registering:', error);
				$('.errors').text(errors);
			}
		});
	},

	clockIn: function() {
		let formData = {
			"userId": userId
		}
		$.ajax({
			url: '/api/clockin',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(formData),
			success: function() {
				console.log('Clocked in successfully');
				mainContent.displayTimeClockEntries();
			},
			error: function(xhr, status, error) {
				var errors = xhr.responseJSON.message;
				console.error('Error clocking in:', error);
				$('.errors').text(errors);
			}
		});
	},

	clockOut: function() {
		let formData = {
			"userId": userId
		}
		$.ajax({
			url: '/api/clockout',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(formData),
			success: function() {
				console.log('Clocked out successfully');
				mainContent.displayTimeClockEntries();
			},
			error: function(xhr, status, error) {
				var errors = xhr.responseJSON.message;
				console.error('Error clocking out:', error);
				$('.errors').text(errors);
			}
		});
	},


	userLogout: function() {
		userId = 0;
		nameOfUser = "";
		$("#timeClockEntries").html("");
		this.showLoginForm();
	},

	showDisplayMessage: function() {
		$('#messageDisplay').show();
	},

	displayTimeClockEntries: function() {
    $.ajax({
        url: '/api/timeclock/' + userId, // Assuming userId is defined somewhere
        type: 'GET',
        contentType: 'application/json',
        success: function(data) {
            $('#timeClockEntries').empty(); // Clear previous entries
            let table = $("<table class='table'></table>");
            let tableHeader = $("<thead><tr><th>User Name</th><th>Date</th><th>Time</th><th>Action</th></tr></thead>");
            let tableBody = $("<tbody></tbody>");

            data.forEach(function(entry) {
                let name = entry.userName;
                let date, time;
                if (entry.clockInTime) {
                    date = formatDate(entry.clockInTime);
                    time = new Date(entry.clockInTime).toLocaleTimeString();
                } else {
                    date = formatDate(entry.clockOutTime);
                    time = new Date(entry.clockOutTime).toLocaleTimeString();
                }
                let action = entry.action === "Clocked In" ? "clocked in" : "clocked out";

                let row = $("<tr></tr>");
                let nameCell = $("<td></td>").text(name);
                let dateCell = $("<td></td>").text(date);
                let timeCell = $("<td></td>").text(time);
                let actionCell = $("<td></td>").text(action);

                row.append(nameCell, dateCell, timeCell, actionCell);
                tableBody.prepend(row); // Add the row at the top
            });

            table.append(tableHeader, tableBody);
            $('#timeClockEntries').append(table);
        },
        error: function(xhr, status, error) {
            var errors = xhr.responseJSON.message;
            console.error('Error fetching time clock entries:', error);
            $('.errors').text(errors);
        }
    });
}







};

function getMonth(month) {
	const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
	return months[parseInt(month, 10) - 1];
}

function formatDate(messageDate) {
    messageDate = new Date(messageDate); // Convert to Date object
    const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    const month = months[messageDate.getMonth()];
    const day = messageDate.getDate();
    const year = messageDate.getFullYear();
    return `${month} ${day}, ${year}`;
}


$(document).ready(function() {
    mainContent.init();
});
