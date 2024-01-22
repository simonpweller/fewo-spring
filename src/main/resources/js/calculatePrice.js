export function calculatePrice(numberOfNights, adults, children, extraBedroom, isApartment) {
	const effectiveNights = Math.max(numberOfNights, 2);
	const pricePerNight = getPricePerNight(numberOfNights, adults, children, isApartment, extraBedroom);
	return effectiveNights * pricePerNight;
}

function getPricePerNight(numberOfNights, adults, children, isApartment, extraBedroom) {
	const people = adults + children;
	const baseChargePerNight = getBaseChargePerNight(numberOfNights, people, isApartment);
	const additionalAdults = Math.max(adults - 2, 0);
	const additionalChildren = adults === 1 ? Math.max(children - 1, 0) : children;
	const chargePerAdditionalAdult = 15;
	const extraBedroomCharge = extraBedroom && people <= 3 ? 15 : 0;
	return baseChargePerNight + (additionalAdults * chargePerAdditionalAdult) + (additionalChildren * 8) + extraBedroomCharge;
}

function getBaseChargePerNight(numberOfNights, people, isApartment) {
	if (people === 1) return getSinglePersonChargePerNight(numberOfNights);
	if (isApartment) {
		return numberOfNights > 2 ? 55 : 65;
	} else {
		return numberOfNights > 2 ? 65 : 75;
	}
}

function getSinglePersonChargePerNight(numberOfNights) {
	switch (numberOfNights) {
		case 1: return 55;
		case 2: return 55;
		case 3: return 45;
		case 4: return 45;
		default: return 40;
	}
}
