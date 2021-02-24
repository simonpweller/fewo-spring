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
const arrivalInThePastError = document.querySelector('#arrival-in-the-past-error');
const dateOrderError = document.querySelector('#date-order-error');
const numberOfNightsError = document.querySelector('#number-of-nights-error');
const adultRequiredError = document.querySelector('#adult-required-error');
const numberOfPeopleErrorApartment = document.querySelector('#number-of-people-error-apartment');
const numberOfPeopleErrorBungalow = document.querySelector('#number-of-people-error-bungalow');
const calculatedPrice = document.getElementById('calculated-price');
const calculatedPricePerNight = document.getElementById('calculated-price-per-night');
const submitButton = document.getElementById('submit-button');
const form = document.querySelector('form');

fetchBookings();
updatePrice();
validate();

propertySelect.addEventListener('change', () => {
	fetchBookings();
});

calendarContainer.addEventListener('click', (e) => {
	if (!e.target.classList.contains('calendar-control')) return;
	const property = propertySelect.value;
	const month = e.target.getAttribute('data-month');
	const year = e.target.getAttribute('data-year');
	fetchBookings(`/buchungen?property=${property}&year=${year}&month=${month}`);
});

form.addEventListener('change', () => {
	updatePrice();
	updateSecondBedroom();
	validate();
})

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

function getValidationErrors() {
	return {...validateNumberOfPeople(), ...validateDates()};
}

function validate() {
	const errors = getValidationErrors();

	adultRequiredError.classList.toggle('form-field-error__hidden', !errors.adultRequiredError);
	numberOfPeopleErrorApartment.classList.toggle('form-field-error__hidden', !errors.numberOfPeopleErrorApartment);
	numberOfPeopleErrorBungalow.classList.toggle('form-field-error__hidden', !errors.numberOfPeopleErrorBungalow);
	arrivalInThePastError.classList.toggle('form-field-error__hidden', !errors.arrivalInThePastError);
	dateOrderError.classList.toggle('form-field-error__hidden', !errors.dateOrderError);
	numberOfNightsError.classList.toggle('form-field-error__hidden', !errors.numberOfNightsError);

	submitButton.disabled = Object.values(errors).filter(Boolean).length > 0;
}

function validateNumberOfPeople() {
	const adults = Number(numberOfAdults.value);
	const children = Number(numberOfChildren.value);
	const totalPeople = adults + children;

	return {
		adultRequiredError: adults < 1,
		numberOfPeopleErrorApartment: isApartmentSelected() && (totalPeople < 1 || totalPeople > 3),
		numberOfPeopleErrorBungalow: !isApartmentSelected() && (totalPeople < 2 || totalPeople > 5),
	}
}

function isApartmentSelected() {
	return propertySelect.value === 'APARTMENT';
}

function validateDates() {
	const missingDate = !arrivalDate.value || !departureDate.value;
	const from = new Date(arrivalDate.value);
	const to = new Date(departureDate.value);
	const numberOfNights = differenceInCalendarDays(to, from);
	return {
		arrivalInThePastError: !missingDate && arrivalDate.value < today(),
		dateOrderError: !missingDate && arrivalDate.value > departureDate.value,
		numberOfNightsError: !missingDate && numberOfNights < 1,
		missingDateError: missingDate,
	}
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

	if (Object.values(getValidationErrors()).filter(Boolean).length > 0) {
		calculatedPrice.innerText = '-';
		calculatedPricePerNight.innerText = '-';
	} else {
		const price = calculatePrice(numberOfNights, adults, children, extraBedroom, isApartment);
		calculatedPrice.innerText = price + '€';
		calculatedPricePerNight.innerText = (price / numberOfNights) + '€';
	}
}
