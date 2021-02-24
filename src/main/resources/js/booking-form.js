import {calculatePrice} from './calculatePrice'
import differenceInCalendarDays from 'date-fns/differenceInCalendarDays';

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
const calculatedPrice = document.getElementById('calculated-price');
const calculatedPricePerNight = document.getElementById('calculated-price-per-night');
const submitButton = document.getElementById('submit-button');
const form = document.querySelector('form');

fetchBookings();
updatePrice();

propertySelect.addEventListener('change', () => {
	updateSecondBedroom();
	fetchBookings();
});

numberOfAdults.addEventListener('change', updateSecondBedroom);
numberOfChildren.addEventListener('change', updateSecondBedroom);

calendarContainer.addEventListener('click', (e) => {
	if (!e.target.classList.contains('calendar-control')) return;
	const property = propertySelect.value;
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

form.addEventListener('change', updatePrice)

function fetchBookings(url = `/buchungen?property=${propertySelect.value}`) {
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

function updatePrice() {
	const from = new Date(arrivalDate.value);
	const to = new Date(departureDate.value);
	const adults = Number(numberOfAdults.value);
	const children = Number(numberOfChildren.value);
	const extraBedroom = secondBedroom.checked;
	const isApartment = isApartmentSelected();

	const numberOfNights = differenceInCalendarDays(to, from);
	const people = adults + children;

	const isInvalid = isNaN(numberOfNights) || numberOfNights < 1 || adults < 1 || (isApartment && (people < 1 || people > 3)) || (!isApartment && (people < 2 || people > 5));
	if (isInvalid) {
		calculatedPrice.innerText = '-';
		calculatedPricePerNight.innerText = '-';
		submitButton.disabled = true;
	} else {
		const price = calculatePrice(numberOfNights, adults, children, extraBedroom, isApartment);
		calculatedPrice.innerText = price + '€';
		calculatedPricePerNight.innerText = (price / numberOfNights) + '€';
		submitButton.disabled = false;
	}
}
