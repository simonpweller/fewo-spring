export function calculatePrice(numberOfNights, adults, children, extraBedroom, isApartment) {
	const people = adults + children;
	const effectiveNights = getEffectiveNights(numberOfNights, people);
	const pricePerNight = getPricePerNight(numberOfNights, adults, children);
	return effectiveNights * pricePerNight;
}

function getPricePerNight(numberOfNights, adults, children) {
	const people = adults + children;
	const baseChargePerNight = getBaseChargePerNight(numberOfNights, people);
	const additionalAdults = Math.max(adults - 2, 0);
	const additionalChildren = adults === 1 ? Math.max(children - 1, 0) : children;
	return baseChargePerNight + additionalAdults * 10 + (additionalChildren * 5);
}

function getEffectiveNights(numberOfNights, people) {
	if (people === 1) return Math.max(numberOfNights, 3);
	return Math.max(numberOfNights, 2);
}

function getBaseChargePerNight(numberOfNights, people) {
	if (people === 1) return 25;
	return numberOfNights > 2 ? 40 : 50;
}
