import {calculatePrice} from './calculatePrice'

describe(`calculatePrice`, () => {
	describe(`apartment`, () => {
		it(`should use a base price of 25€ per night for 1 person`, () => {
			expect(calculatePrice(3, 1, 0, false, true)).toBe(75);
			expect(calculatePrice(4, 1, 0, false, true)).toBe(100);
		});

		it(`should use a base price of 50€ per night for 2 people`, () => {
			expect(calculatePrice(2, 2, 0, false, true)).toBe(100);
		});

		it(`should use a base price of 40€ per night for 2 people staying 3 nights or more`, () => {
			expect(calculatePrice(3, 2, 0, false, true)).toBe(120);
			expect(calculatePrice(4, 2, 0, false, true)).toBe(160);
		});

		it(`should add 10€ per night for an additional person`, () => {
			expect(calculatePrice(3, 3, 0, false, true)).toBe(150);
			expect(calculatePrice(4, 3, 0, false, true)).toBe(200);
		});

		it(`should add 5€ per night for a child`, () => {
			expect(calculatePrice(3, 2, 1, false, true)).toBe(135);
			expect(calculatePrice(4, 2, 1, false, true)).toBe(180);
		});

		it(`should treat 1 adult and 1 child like 2 adults`, () => {
			expect(calculatePrice(2, 1, 1, false, true)).toBe(100);
			expect(calculatePrice(3, 1, 1, false, true)).toBe(120);
		});

		it(`should correctly enforce the minimum number of nights`, () => {
			expect(calculatePrice(1, 1, 0, false, true)).toBe(75);
			expect(calculatePrice(2, 1, 0, false, true)).toBe(75);

			expect(calculatePrice(1, 2, 0, false, true)).toBe(100);
			expect(calculatePrice(1, 1, 1, false, true)).toBe(100);
			expect(calculatePrice(1, 1, 2, false, true)).toBe(110);
			expect(calculatePrice(1, 3, 0, false, true)).toBe(120);
		});
	});
});
