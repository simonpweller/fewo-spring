export function calculatePrice(numberOfNights, adults, children, extraBedroom, isApartment) {
	const people = adults + children;
	const effectiveNights = getEffectiveNights(numberOfNights, people);
	const pricePerNight = getPricePerNight(numberOfNights, adults, children, isApartment, extraBedroom);
	return effectiveNights * pricePerNight;
}

function getPricePerNight(numberOfNights, adults, children, isApartment, extraBedroom) {
	const people = adults + children;
	const baseChargePerNight = getBaseChargePerNight(numberOfNights, people, isApartment);
	const additionalAdults = Math.max(adults - 2, 0);
	const additionalChildren = adults === 1 ? Math.max(children - 1, 0) : children;
	const chargePerAdditionalAdult = isApartment ? 10 : 15;
	const extraBedroomCharge = extraBedroom && people <= 3 ? 10 : 0;
	return baseChargePerNight + (additionalAdults * chargePerAdditionalAdult) + (additionalChildren * 5) + extraBedroomCharge;
}

function getEffectiveNights(numberOfNights, people) {
	return people === 1 ? Math.max(numberOfNights, 3) : Math.max(numberOfNights, 2);
}

function getBaseChargePerNight(numberOfNights, people, isApartment) {
	if (people === 1) return 25;
	if (isApartment) {
		return numberOfNights > 2 ? 40 : 50;
	} else {
		return numberOfNights > 2 ? 50 : 60;
	}
}
