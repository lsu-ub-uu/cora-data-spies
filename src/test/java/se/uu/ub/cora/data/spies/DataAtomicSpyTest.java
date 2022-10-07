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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.data.DataAttribute;
import se.uu.ub.cora.data.spies.DataAtomicSpy;
import se.uu.ub.cora.data.spies.DataAttributeSpy;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class DataAtomicSpyTest {
	private static final String ADD_CALL = "addCall";
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	DataAtomicSpy dataAtomic;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		dataAtomic = new DataAtomicSpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() throws Exception {
		assertTrue(dataAtomic.MCR instanceof MethodCallRecorder);
		assertTrue(dataAtomic.MRV instanceof MethodReturnValues);
		assertSame(dataAtomic.MCR.onlyForTestGetMRV(), dataAtomic.MRV);
	}

	@Test
	public void testsetRepeatId() throws Exception {
		dataAtomic.MCR = MCRSpy;

		dataAtomic.setRepeatId("repeat1");

		mcrForSpy.assertParameter(ADD_CALL, 0, "repeatId", "repeat1");
	}

	@Test
	public void testDefaultGetRepeatId() throws Exception {
		assertTrue(dataAtomic.getRepeatId() instanceof String);
	}

	@Test
	public void testGetRepeatId() throws Exception {
		dataAtomic.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataAtomic.getRepeatId();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testAddAttributeByIdWithValue() throws Exception {
		dataAtomic.MCR = MCRSpy;

		dataAtomic.addAttributeByIdWithValue("attribId", "attribValue");

		mcrForSpy.assertParameter(ADD_CALL, 0, "nameInData", "attribId");
		mcrForSpy.assertParameter(ADD_CALL, 0, "value", "attribValue");
	}

	@Test
	public void testDefaultHasAttributes() throws Exception {
		assertFalse(dataAtomic.hasAttributes());
	}

	@Test
	public void testHasAttributes() throws Exception {
		dataAtomic.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> true);

		boolean retunedValue = dataAtomic.hasAttributes();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

	@Test
	public void testDefaultGetAttribute() throws Exception {
		assertTrue(dataAtomic.getAttribute("nameInData") instanceof DataAttribute);
	}

	@Test
	public void testGetAttribute() throws Exception {
		dataAtomic.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, DataAttributeSpy::new);

		var returnedValue = dataAtomic.getAttribute("nameInData");

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetAttributes() throws Exception {
		assertTrue(dataAtomic.getAttributes() instanceof Collection<DataAttribute>);
	}

	@Test
	public void testGetAttributes() throws Exception {
		dataAtomic.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				ArrayList<DataAttribute>::new);

		var returnedValue = dataAtomic.getAttributes();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetNameInData() throws Exception {
		assertTrue(dataAtomic.getNameInData() instanceof String);
	}

	@Test
	public void testGetNameInData() throws Exception {
		dataAtomic.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataAtomic.getNameInData();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}

	@Test
	public void testDefaultGetValue() throws Exception {
		assertTrue(dataAtomic.getValue() instanceof String);
	}

	@Test
	public void testGetValue() throws Exception {
		dataAtomic.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV, String::new);

		String returnedValue = dataAtomic.getValue();

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, returnedValue);
	}
}
