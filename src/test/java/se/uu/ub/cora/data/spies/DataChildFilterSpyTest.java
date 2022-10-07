/*
 * Copyright 2022 Uppsala University Library
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

import java.util.Set;
import java.util.function.Supplier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.data.spies.DataChildFilterSpy;
import se.uu.ub.cora.data.spies.DataChildSpy;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;
import se.uu.ub.cora.testutils.spies.MCRSpy;

public class DataChildFilterSpyTest {

	private static final String ADD_CALL = "addCall";
	private static final String ADD_CALL_AND_RETURN_FROM_MRV = "addCallAndReturnFromMRV";
	DataChildFilterSpy dataChildFilterSpy;
	private MCRSpy MCRSpy;
	private MethodCallRecorder mcrForSpy;

	@BeforeMethod
	public void beforeMethod() {
		MCRSpy = new MCRSpy();
		mcrForSpy = MCRSpy.MCR;
		dataChildFilterSpy = new DataChildFilterSpy();
	}

	@Test
	public void testMakeSureSpyHelpersAreSetUp() throws Exception {
		assertTrue(dataChildFilterSpy.MCR instanceof MethodCallRecorder);
		assertTrue(dataChildFilterSpy.MRV instanceof MethodReturnValues);
		assertSame(dataChildFilterSpy.MCR.onlyForTestGetMRV(), dataChildFilterSpy.MRV);
	}

	@Test
	public void testAddAttributeUsingNameInDataAndPossibleValues() throws Exception {
		dataChildFilterSpy.MCR = MCRSpy;

		Set<String> possibleValues = Set.of("value1", "value2");
		dataChildFilterSpy.addAttributeUsingNameInDataAndPossibleValues("someNameInData",
				possibleValues);

		mcrForSpy.assertParameter(ADD_CALL, 0, "nameInData", "someNameInData");
		mcrForSpy.assertParameter(ADD_CALL, 0, "possibleValues", possibleValues);
	}

	@Test
	public void testDefaultChildMatches() throws Exception {
		DataChildSpy child = new DataChildSpy();

		assertTrue(dataChildFilterSpy.childMatches(child));
	}

	@Test
	public void testChildMatches() throws Exception {
		DataChildSpy child = new DataChildSpy();
		dataChildFilterSpy.MCR = MCRSpy;
		MCRSpy.MRV.setDefaultReturnValuesSupplier(ADD_CALL_AND_RETURN_FROM_MRV,
				(Supplier<Boolean>) () -> false);

		boolean retunedValue = dataChildFilterSpy.childMatches(child);

		mcrForSpy.assertMethodWasCalled(ADD_CALL_AND_RETURN_FROM_MRV);
		mcrForSpy.assertParameter(ADD_CALL_AND_RETURN_FROM_MRV, 0, "child", child);
		mcrForSpy.assertReturn(ADD_CALL_AND_RETURN_FROM_MRV, 0, retunedValue);
	}

}
