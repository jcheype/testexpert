/*
 * This file is part of TestExpert.
 *
 * Copyright (C) 2012
 * "David Gageot" <david@gageot.net>,
 *
 * TestExpert is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TestExpert is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TestExpert. If not, see <http://www.gnu.org/licenses/>.
 */
package net.gageot.test.rules;

import org.junit.*;

public class ExpectedExceptionTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void canExpectExceptionByType() {
		exception.expect(RuntimeException.class);

		throw new RuntimeException();
	}

	@Test
	public void canExpectWithMessage() {
		exception.expectMessage("BUG");

		throw new RuntimeException("BUG");
	}
}
