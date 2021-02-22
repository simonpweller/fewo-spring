import { calculatePrice } from './calculatePrice'

describe(`calculatePrice`, () => {
	it(`should return 75€ for 1 person staying in the apartment for 3 nights`, () => {
		const numberOfNights = 3;
		const adults = 1;
		const children = 0;
		const extraBedroom = false;
		const isApartment = true;

		expect(calculatePrice(numberOfNights, adults, children, extraBedroom, isApartment)).toBe(75);
	});
});
