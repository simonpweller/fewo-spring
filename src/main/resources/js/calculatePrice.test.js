import {calculatePrice} from './calculatePrice'

describe(`calculatePrice`, () => {
	describe(`apartment`, () => {
		it(`should use a base price of 45€ per night for 1 person`, () => {
			expect(calculatePrice(3, 1, 0, false, true)).toBe(135);
			expect(calculatePrice(4, 1, 0, false, true)).toBe(180);
		});

		it(`should use a base price of 40€ per night for 1 person staying 5 nights or more`, () => {
			expect(calculatePrice(5, 1, 0, false, true)).toBe(200);
			expect(calculatePrice(6, 1, 0, false, true)).toBe(240);
		});

		it(`should use a base price of 55€ per night for 1 person staying 2 nights only`, () => {
			expect(calculatePrice(2, 1, 0, false, true)).toBe(110);
		});

		it(`should use a base price of 65€ per night for 2 people`, () => {
			expect(calculatePrice(2, 2, 0, false, true)).toBe(130);
		});

		it(`should use a base price of 55€ per night for 2 people staying 3 nights or more`, () => {
			expect(calculatePrice(3, 2, 0, false, true)).toBe(165);
			expect(calculatePrice(4, 2, 0, false, true)).toBe(220);
		});

		it(`should add 15€ per night for an additional person`, () => {
			expect(calculatePrice(3, 3, 0, false, true)).toBe(210);
			expect(calculatePrice(4, 3, 0, false, true)).toBe(280);
		});

		it(`should add 8€ per night for a child`, () => {
			expect(calculatePrice(3, 2, 1, false, true)).toBe(189);
			expect(calculatePrice(4, 2, 1, false, true)).toBe(252);
		});

		it(`should treat 1 adult and 1 child like 2 adults`, () => {
			expect(calculatePrice(2, 1, 1, false, true)).toBe(130);
			expect(calculatePrice(3, 1, 1, false, true)).toBe(165);
		});

		it(`should correctly enforce the minimum number of nights`, () => {
			expect(calculatePrice(1, 1, 0, false, true)).toBe(110);

			expect(calculatePrice(1, 2, 0, false, true)).toBe(130);
			expect(calculatePrice(1, 1, 1, false, true)).toBe(130);
			expect(calculatePrice(1, 1, 2, false, true)).toBe(146);
			expect(calculatePrice(1, 3, 0, false, true)).toBe(160);
		});
	});

	describe(`bungalow`, () => {
		it(`should use a base price of 75€ per night for 2 people`, () => {
			expect(calculatePrice(2, 2, 0, false, false)).toBe(150);
		});

		it(`should use a base price of 65€ per night for 2 people staying 3 nights or more`, () => {
			expect(calculatePrice(3, 2, 0, false, false)).toBe(195);
			expect(calculatePrice(4, 2, 0, false, false)).toBe(260);
		});

		it(`should add 15€ per night for an additional person`, () => {
			expect(calculatePrice(3, 3, 0, false, false)).toBe(240);
			expect(calculatePrice(4, 3, 0, false, false)).toBe(320);

			expect(calculatePrice(3, 4, 0, false, false)).toBe(285);
			expect(calculatePrice(4, 4, 0, false, false)).toBe(380);

			expect(calculatePrice(3, 5, 0, false, false)).toBe(330);
			expect(calculatePrice(4, 5, 0, false, false)).toBe(440);
		});

		it(`should add 8€ per night for a child`, () => {
			expect(calculatePrice(3, 2, 1, false, false)).toBe(219);
			expect(calculatePrice(4, 2, 1, false, false)).toBe(292);
		});

		it(`should treat 1 adult and 1 child like 2 adults`, () => {
			expect(calculatePrice(2, 1, 1, false, false)).toBe(150);
			expect(calculatePrice(3, 1, 1, false, false)).toBe(195);
		});

		it(`should correctly enforce the minimum number of nights`, () => {
			expect(calculatePrice(1, 2, 0, false, false)).toBe(150);
			expect(calculatePrice(1, 1, 1, false, false)).toBe(150);
		});

		it(`should add 15€ per night for the extra bedroom for up to 3 people`, () => {
			expect(calculatePrice(2, 2, 0, true, false)).toBe(180);
			expect(calculatePrice(3, 3, 0, true, false)).toBe(285);
		});

		it(`should not add an extra charge for the extra bedroom for more than 3 people`, () => {
			expect(calculatePrice(2, 2, 2, true, false)).toBe(182);
			expect(calculatePrice(3, 4, 0, true, false)).toBe(285);
		});
	});
});
