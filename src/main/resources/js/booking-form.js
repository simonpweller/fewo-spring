const calendarContainer = document.getElementById('calendar-container');
const propertySelect = document.getElementById('property');
const secondBedroomFormField = document.getElementById('secondBedroomFormField');
const secondBedroom = document.getElementById('secondBedroom');
const arrivalDate = document.getElementById('arrivalDate');
const numberOfAdults = document.getElementById('numberOfAdults');
const numberOfChildren = document.getElementById('numberOfChildren');
const departureDate = document.getElementById('departureDate');
const arrivalDateError = document.querySelector('#arrivalDate + .form-field-error');
const departureDateError = document.querySelector('#departureDate + .form-field-error');
const form = document.querySelector('form');
let property = document.querySelector('main').getAttribute('data-property');

fetchBookings()

propertySelect.addEventListener('change', (e) => {
	property = e.target.value;
	updateSecondBedroom();
	fetchBookings();
});

numberOfAdults.addEventListener('change', updateSecondBedroom);
numberOfChildren.addEventListener('change', updateSecondBedroom);

calendarContainer.addEventListener('click', (e) => {
	if (!e.target.classList.contains('calendar-control')) return;
	const month = e.target.getAttribute('data-month');
	const year = e.target.getAttribute('data-year');
	fetchBookings(`/buchungen?property=${property}&year=${year}&month=${month}`);
});

departureDate.addEventListener('change', validateDates)
arrivalDate.addEventListener('change', validateDates)
form.addEventListener('submit', (e) => {
	if (!validateDates()) {
		e.preventDefault();
	}
});

function fetchBookings(url = `/buchungen?property=${property}`) {
	fetch(url)
		.then(res => res.text())
		.then(html => calendarContainer.innerHTML = html)
		.catch(e => console.error(e));
}

function updateSecondBedroom() {
	secondBedroomFormField.classList.toggle('hidden', isApartmentSelected());
	if (isApartmentSelected()) {
		secondBedroom.checked = false;
	} else {
		const totalPeople = Number(numberOfAdults.value) + Number(numberOfChildren.value);
		if (totalPeople > 3) {
			secondBedroom.checked = true;
			secondBedroom.disabled = true;
		} else {
			secondBedroom.disabled = false;
		}
	}
}

function isApartmentSelected() {
	return propertySelect.value === 'APARTMENT';
}

function validateDates() {
	if (!arrivalDate.value || !departureDate.value) return false;
	const validArrivalDate = arrivalDate.value >= today();
	const validDateOrder = arrivalDate.value <= departureDate.value;
	arrivalDateError.classList.toggle('form-field-error__hidden', validArrivalDate);
	departureDateError.classList.toggle('form-field-error__hidden', validDateOrder);
	return validArrivalDate && validDateOrder;
}

function today() {
	const date = new Date();
	return [
		date.getFullYear(),
		('0' + (date.getMonth() + 1)).slice(-2),
		('0' + date.getDate()).slice(-2),
	].join('-');
}
