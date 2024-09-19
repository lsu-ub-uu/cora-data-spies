/*
 * Copyright 2022 Olov McKie
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.data.spies;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class DataAttributeSpyTest {
	DataAttributeSpy dataAttribute;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		dataAttribute = new DataAttributeSpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() throws Exception {
		assertTrue(dataAttribute.MCR instanceof MethodCallRecorder);
		assertTrue(dataAttribute.MRV instanceof MethodReturnValues);
		assertSame(dataAttribute.MCR.onlyForTestGetMRV(), dataAttribute.MRV);
	}

	@Test
	public void testDefaultGetNameInData() throws Exception {
		assertTrue(dataAttribute.getNameInData() instanceof String);
	}

	@Test
	public void testGetNameInData() throws Exception {
		dataAttribute.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier("addCallAndReturnFromMRV", String::new);

		String returnedValue = dataAttribute.getNameInData();

		mcrForSpy.assertMethodWasCalled("addCallAndReturnFromMRV");
		mcrForSpy.assertReturn("addCallAndReturnFromMRV", 0, returnedValue);
	}

	@Test
	public void testDefaultGetValue() throws Exception {
		assertTrue(dataAttribute.getValue() instanceof String);
	}

	@Test
	public void testGetValue() throws Exception {
		dataAttribute.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier("addCallAndReturnFromMRV", String::new);

		String returnedValue = dataAttribute.getValue();

		mcrForSpy.assertMethodWasCalled("addCallAndReturnFromMRV");
		mcrForSpy.assertReturn("addCallAndReturnFromMRV", 0, returnedValue);
	}
}
